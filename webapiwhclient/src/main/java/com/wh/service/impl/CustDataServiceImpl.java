package com.wh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wh.util.*;
import erp.ErpBaseModel;
import erp.ErpCustData;
import erp.ErpCustDataTableInfo;
import erp.ErpSql;
import erp.common.CommonRequestVo;
import erp.common.ErpTaskLockDto;
import erp.common.RequestVo;
import erp.common.SystemParameter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service("custDataService")
public class CustDataServiceImpl extends AbstractJobService {

    private static Logger logger = Logger.getLogger(CustDataServiceImpl.class);

    private static final String JOB_NAME = "客户数据同步";

    @Override
    public void process(ErpBaseModel model) {
        ErpCustData ep = (ErpCustData)model;
        String maxSaleTime = "";
        if (!validate((ErpBaseModel)ep, "客户数据同步"))
            return;
        try {
            String supplyId = ep.getYywSecret();//145949，这个是配置的用户加密解密的secret
            logger.error("开始执行客户数据同步任务");
            Map<String,Object> statusMap = getStatus(supplyId);   //获取任务锁的状态
            int status = (Integer) statusMap.get("status");
            String lastTimestamp = statusMap.get("timestamp")==null?"":statusMap.get("timestamp").toString(); //这个时间戳是上一次提交的最后一条销售记录的发药时间
            if (status == 2 || status == 0) {
                logger.error("正在执行客户数据同步JOB");
                return;
            }
            dealTaskLock(ep.getYywSecret(), 0);  //给该任务加锁
            long startTime = System.currentTimeMillis();
            for (int k = 0; k < ep.getTableInfos().size(); k++) {
                ErpCustDataTableInfo tableInfo = ep.getTableInfos().get(k);
                String sql = tableInfo.getSqlContext();
                if (!StringUtils.isEmpty(lastTimestamp)) {
                    //String sd= DateUtil.convertDate2String(new Date(Long.valueOf(lastTimestamp)), "yyyy-MM-dd HH:mm:ss");
                    sql = sql + " and 发药时间> '" +lastTimestamp+"'";
                }
                //sql = sql +"order by 发药时间 desc";
                String tableName = tableInfo.getTableName().trim();
                if (sql != null && !"".equals(sql))
                    if (/*delData(tableName)*/ 1==1) {
                        List<Map<Object, Object>> temp = getList(sql);
                        if (CollectionUtils.isEmpty(temp)) {
                            logger.error("根据配置的sql查询不到结果集合,或本次任务无数据需要传输");
                        } else {
                            maxSaleTime = temp.get(0).get("发药时间")==null?"":temp.get(0).get("发药时间").toString();//获取第一条也是发药时间（订单时间）最大得一条。
                            int allCount = temp.size();
                            int pageSize = 200;
                            int allPage = (allCount % pageSize == 0) ? (allCount / pageSize) : (allCount / pageSize + 1);
                            for (int i = 0; i < allPage; i++) {
                                int fromIndex = i * pageSize;
                                int toIndex = (fromIndex + pageSize > allCount) ? allCount : (fromIndex + pageSize);
                                List<Map<Object, Object>> maps = temp.subList(fromIndex, toIndex);
                                try {
                                    String result = call(maps, tableName);
                                    logger.info("同步客户数据第"+ (i + 1) + "页返回结果=="+ result);
                                } catch (Exception e) {
                                    logger.error("同步客户数据第"+ (i + 1) + "页等待返回异常", e);
                                }
                            }
                        }
                    }
            }
            //String strDatetime = String.valueOf(DateUtil.dateToTimeStamp(DateUtil.convertString2Date(maxSaleTime,DateUtil.DATE_SPLIT_FORMAT)));
            //lastTimestamp = String.valueOf(DateUtil.dateToTimeStamp(DateUtil.convertString2Date(maxSaleTime,DateUtil.DATE_SPLIT_FORMAT)).getTime());
            dealTaskLock(ep.getYywSecret(), 1); //给任务释放锁
            long useTime = System.currentTimeMillis() - startTime;
            PopRequest popRequest = new PopRequest();
            popRequest.addErpLog(ep.getYywSecret(), "本次客户数据同步花费时间："+ useTime + "毫秒", Integer.valueOf(7), Integer.valueOf(1), Integer.valueOf(3), "客户数据同步花费时间");
        } catch (Exception e) {
            dealTaskLock(ep.getYywSecret(), 1);
            PopRequest popRequest = new PopRequest();
            popRequest.addErpLog(ep.getYywSecret(), e.getMessage() + "堆栈信息："+ ExceptionUtils.getFullStackTrace(e), Integer.valueOf(7), Integer.valueOf(1), Integer.valueOf(1), "客户数据错误信息");
            logger.error("客户数据同步请求错误了=="+ e.getMessage());
        }
    }

    public List<Map<Object, Object>> getList(String sql) {
        logger.error("根据sql获取数据："+ sql);
        ErpCommon.getInstance();
        ErpCustData ep = ErpCommon.erpCustData;
        List<Map<Object, Object>> result = new ArrayList<>();
        try {
            if (sql != null && !sql.trim().equals("")) {
                result = DataBaseConnection.getInstance().executeQuery(sql);
                if (result != null && result.size() > 0)
                    for (Map<Object, Object> map : result) {
                        map.remove("RN");
                        map.remove("RC");
                        for (Map.Entry<Object, Object> entry : map.entrySet()) {
                            if (entry.getValue() instanceof Date)
                                map.put(entry.getKey(), DateUtil.convertDate2String((Date)entry.getValue()));
                        }
                    }
            }
        } catch (Exception e) {
            PopRequest popRequest = new PopRequest();
            popRequest.addErpLog(ep.getYywSecret(), sql + "," + e.getMessage() + "堆栈信息"+ ExceptionUtils.getFullStackTrace(e), Integer.valueOf(7), Integer.valueOf(1), Integer.valueOf(1), "获取客户数据错误信息");
        }
        return result;
    }

    public boolean delData(String tableName) {
        boolean result = false;
        ErpCommon.getInstance();
        ErpCustData ep = ErpCommon.erpCustData;
        try {
            String json = "";
            SystemParameter sp = new SystemParameter();
            sp.setDataType("json");
            sp.setInterfaceName("yyw.data.delData");
            sp.setValidateCode(ep.getYywSecret());
            sp.setVersion("1.0");
            sp.setRequestTime(String.valueOf(System.currentTimeMillis()));
            sp.setSupplyName(ep.getPharmacyName());
            sp.setParmeter(tableName);
            CommonRequestVo<Map<Object, Object>> commonVo = new CommonRequestVo();
            RequestVo requestVo = new RequestVo();
            requestVo.setCommonData(commonVo);
            requestVo.setSystemParameter(sp);
            json = JSONObject.toJSONString(requestVo);
            PopRequest pr = new PopRequest();
            Map<String, String> headParams = new HashMap<>();
            headParams.put("method", "delCustDataUrl");
            headParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
            headParams.put("partnerid", ep.getYywSecret());
            String returnValueJson = pr.getPost(ConfigHelpUtils.getConfigString("delCustDataUrl"), json, headParams);
            JSONObject map = JSONObject.parseObject(returnValueJson);
            String returnCode = map.getString("code");
            if (returnCode.equals("1")) {
                result = true;
                logger.info("删除客户数据调用服务端成功了");
            }
        } catch (Exception e) {
            PopRequest popRequest = new PopRequest();
            popRequest.addErpLog(ep.getYywSecret(), e.getMessage() + "堆栈信息："+ ExceptionUtils.getFullStackTrace(e), Integer.valueOf(7), Integer.valueOf(1), Integer.valueOf(1), "客户数据错误信息");
            logger.error("客户数据删除请求错误了=="+ e.getMessage());
        }
        return result;
    }

    public void dealTaskLock(String supplyId, int status) {
        try {
            ErpCommon.getInstance();
            ErpSql erpSql = ErpCommon.es;
            ErpTaskLockDto erpTaskLockDto = new ErpTaskLockDto();
            erpTaskLockDto.setSupplyId(Integer.valueOf(supplyId));
            erpTaskLockDto.setTaskCode("0");
            erpTaskLockDto.setStatus(Integer.valueOf(status));
            PopRequest pr = new PopRequest();
            Map<String, String> headParams = new HashMap<>();
            headParams.put("method", "savaOrUpdateTaskLock");
            headParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
            headParams.put("partnerid", supplyId);
            //String url = "/taskLock/saveOrUpdate";
            String url =erpSql.getServicePath() +"/rest/company/savaOrUpdateTaskLock"; //自定义路径
            String json = JSONObject.toJSONString(erpTaskLockDto);
            String returnValueJson = pr.getPost(url, json, headParams);
            if (StringUtils.isNotBlank(returnValueJson)) {
                JSONObject map = JSONObject.parseObject(returnValueJson);
                String returnCode = map.getString("code");
                PopRequest popRequest = new PopRequest();
                popRequest.addErpLog(supplyId, returnCode, Integer.valueOf(7), Integer.valueOf(1), Integer.valueOf(3), "任务锁操作成功");
            }
        } catch (Exception e) {
            PopRequest popRequest = new PopRequest();
            popRequest.addErpLog(supplyId, e.getMessage() + "堆栈信息"+ ExceptionUtils.getFullStackTrace(e), Integer.valueOf(7), Integer.valueOf(1), Integer.valueOf(1), "任务锁错误记录");
        }
    }

    public void dealTaskLock(String supplyId, int status, String timesstamp) {
        try {
            ErpCommon.getInstance();
            ErpSql erpSql = ErpCommon.es;
            ErpTaskLockDto erpTaskLockDto = new ErpTaskLockDto();
            erpTaskLockDto.setSupplyId(Integer.valueOf(supplyId));
            erpTaskLockDto.setTaskCode(timesstamp);
            erpTaskLockDto.setStatus(Integer.valueOf(status));
            PopRequest pr = new PopRequest();
            Map<String, String> headParams = new HashMap<>();
            headParams.put("method", "savaOrUpdateTaskLock");
            headParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
            headParams.put("partnerid", supplyId);
            //String url = "/taskLock/saveOrUpdate";
            String url =erpSql.getServicePath() +"/rest/company/savaOrUpdateTaskLock"; //自定义路径
            String json = JSONObject.toJSONString(erpTaskLockDto);
            String returnValueJson = pr.getPost(url, json, headParams);
            if (StringUtils.isNotBlank(returnValueJson)) {
                JSONObject map = JSONObject.parseObject(returnValueJson);
                String returnCode = map.getString("code");
                PopRequest popRequest = new PopRequest();
                popRequest.addErpLog(supplyId, returnCode, Integer.valueOf(7), Integer.valueOf(1), Integer.valueOf(3), "任务锁操作成功");
            }
        } catch (Exception e) {
            PopRequest popRequest = new PopRequest();
            popRequest.addErpLog(supplyId, e.getMessage() + "堆栈信息"+ ExceptionUtils.getFullStackTrace(e), Integer.valueOf(7), Integer.valueOf(1), Integer.valueOf(1), "任务锁错误记录");
        }
    }

    public Map<String, Object> getStatus(String supplyId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        int result = -1;
        String dateStr = "";
        String timestamp = "0";
        try {
            ErpCommon.getInstance();
            ErpSql erpSql = ErpCommon.es;
            ErpTaskLockDto erpTaskLockDto = new ErpTaskLockDto();
            erpTaskLockDto.setSupplyId(Integer.valueOf(supplyId));
            erpTaskLockDto.setTaskCode("0");
            PopRequest pr = new PopRequest();
            Map<String, String> headParams = new HashMap<>();
            headParams.put("method", "heartBeatUrl");
            headParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
            headParams.put("partnerid", supplyId);
            //String url = erpSql.getServicePath() + "/taskLock/getStatus";
            String url = erpSql.getServicePath() +"/rest/company/getTaskLockStatus";
            //String url ="http://ddx.yaoex.com/taskLock/getStatus"; //定义临时访问状态路径
            logger.error(url);
            String json = JSONObject.toJSONString(erpTaskLockDto);
            String returnValueJson = pr.getPost(url, json, headParams);
            JSONObject map = JSONObject.parseObject(returnValueJson);
            String returnCode = map.getString("code");
            if (returnCode.equals("0000") && map.get("data") != null)
                //result = map.getInteger("data").intValue();
                 dateStr = map.getString("data");
            if (dateStr !=null) {
                JSONObject datejson = JSONObject.parseObject(dateStr);
                result = datejson.getInteger("STATUS");
                timestamp = datejson.getString("TIMESTAMP");
                resultMap.put("status",result);
                resultMap.put("timestamp",timestamp);
            }
            PopRequest popRequest = new PopRequest();
            popRequest.addErpLog(supplyId, returnValueJson, Integer.valueOf(7), Integer.valueOf(1), Integer.valueOf(3), "任务锁查询成功");
        } catch (Exception e) {
            PopRequest popRequest = new PopRequest();
            popRequest.addErpLog(supplyId, e.getMessage() + "堆栈信息"+ ExceptionUtils.getFullStackTrace(e), Integer.valueOf(7), Integer.valueOf(1), Integer.valueOf(1), "任务锁错误记录");
        }
        return resultMap;
    }

    public String packParam(List<Map<Object, Object>> list, String tableName) {
        ErpCustData ep = ErpCommon.erpCustData;
        String json = "";
        try {
            String version = ReadProperties.getProperties("version");
            if (version == null || "".equals(version))
                version = "2.0";
            SystemParameter sp = new SystemParameter();
            sp.setDataType("json");
            sp.setInterfaceName("yyw.data.saveData");
            sp.setValidateCode(ep.getYywSecret());
            sp.setVersion(version);
            sp.setRequestTime(String.valueOf(System.currentTimeMillis()));
            sp.setSupplyName(ep.getPharmacyName());
            sp.setParmeter(tableName);
            CommonRequestVo<Map<Object, Object>> commonVo = new CommonRequestVo();
            logger.error("DataCountSize===" + list.size());
            commonVo.setDataCount(list.size());
            commonVo.setObjectData(list);
            RequestVo requestVo = new RequestVo();
            requestVo.setCommonData(commonVo);
            requestVo.setSystemParameter(sp);
            json = JSONObject.toJSONString(requestVo);
        } catch (Exception e) {
            logger.error("拼装参数出错", e);
        }
        return json;
    }

    public String call(List<Map<Object, Object>> list, String tableName) {
        ErpCustData ep = ErpCommon.erpCustData;
        String json = packParam(list, tableName);
        if (StringUtils.isBlank(json)) {
            logger.error("拼装参数出错");
            return "拼装参数出错";
        }
        logger.error("线程Name=="+ Thread.currentThread().getName() + "调用erp接口，客户数据");
        PopRequest pr = new PopRequest();
        Map<String, String> headParams = new HashMap<>();
        headParams.put("method", "saveCustDataUrl");
        headParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
        headParams.put("partnerid", ep.getYywSecret());
        String returnValueJson = pr.getPost(ConfigHelpUtils.getConfigString("saveCustDataUrl"), json, headParams);
        JSONObject map = JSONObject.parseObject(returnValueJson);
        String returnCode = map.getString("code");
        String message = map.getString("data");
        if (returnCode.equals("1"))
            logger.error("线程Name=="+ Thread.currentThread().getName() + " : 客户数据同步任务调用服务端成功了");
        return message;
    }

    public static void main(String[] args) {
        CustDataServiceImpl custDataService = new CustDataServiceImpl();
        ErpCommon.getInstance();
        custDataService.process(ErpCommon.erpCustData);

    }
}
