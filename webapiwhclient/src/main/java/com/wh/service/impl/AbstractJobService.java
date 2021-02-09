package com.wh.service.impl;

import com.wh.service.JobService;
import com.wh.util.DataBaseConnection;
import com.wh.util.DateUtil;
import erp.ErpBaseModel;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.Date;

public abstract class AbstractJobService implements JobService {

    private static Logger logger = Logger.getLogger(AbstractJobService.class);

    @Override
    public boolean validate(ErpBaseModel model, String jobName) {
        if (model == null) {
            logger.error("沒有配置"+jobName +"任務");
            return false;
        }
        if (model.getFrequency()< 3.0D) {
            logger.error(model.getFrequency());
            logger.error(jobName+ "任务间隔时间过短");
            return false;
        }
        String startTime = model.getStartTime();
        String startMin = model.getStartMin();
        String endTime = model.getEndTime();
        String endMin = model.getEndMin();
        if (StringUtils.isBlank(startTime) ||           //StringUtils.isBlank(String)判断某字符串是否为空或长度为0或由空白符
                StringUtils.isBlank(startMin) ||
                StringUtils.isBlank(endTime) ||
                StringUtils.isBlank(endMin)) {
            logger.error(jobName + "任务开始时间与结束时间配置不正确，请配置正确的时间范围");
        }
        logger.error(startTime+":"+startMin);
        logger.error(endTime+":"+endMin);
        String hHmm = DateUtil.convertDate2String(new Date(), "HHmm");
        Integer start = Integer.valueOf(startTime +startMin);
        Integer end = Integer.valueOf(endTime +endMin);
        Integer now = Integer.valueOf(hHmm);
        if (start.intValue() > now.intValue() || now.intValue() > end.intValue()) {
            logger.error(jobName + "任务当前不在任务时间内");
            return false;
        }
        if (StringUtils.isBlank(model.getYywSecret())) {
            logger.error("请填写客户编码");
            return false;
        }
        if (StringUtils.isBlank(model.getOpenDock()) || model.getOpenDock().trim().equals("fasle")) {
            logger.error("没有开启" + jobName + "任务");
            return false;
        }
        try {
            //连接数据库
            DataBaseConnection.getInstance().openCon();
            logger.error("-------连接数据库成功------");
        }catch (Exception e){
            logger.error("数据库连接错误",e);
            return false;
        }
        return true;
    }
}
