package com.wh.util;

import com.alibaba.fastjson.JSONObject;
import com.wh.vo.ErpLog;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class PopRequest {
    private static Logger log = Logger.getLogger(PopRequest.class);

    public String getPost(String url, String json, Map<String, String> headParams) throws ErpRunException {
        StringBuffer re = new StringBuffer();
        String jsonStr = "";
        HttpURLConnection httpConnection = null;
        OutputStream outputStream = null;
        BufferedReader responseBuffer = null;
        try {
            String sign = SignUtil.md5Signature(headParams, ErpCommon.es.getYywKey());
            //System.out.println("sign1:"+sign);
            if (StringUtils.isNotBlank(json)) {
                //String jsonString=json.replaceAll("%(?![0-9a-fA-F]{2})", "%25").replaceAll("\\+", "%2B");
                String signParams = SignUtil.getMd5Code(json);
                //System.out.println("sign2:"+signParams);
                //System.out.println("accessKey:"+ErpCommon.es.getYywKey());
                sign = SignUtil.getMd5Code(sign + signParams + ErpCommon.es.getYywKey());
            }
            String jsonParam = JSONObject.toJSONString(headParams);
            //System.out.println(jsonParam);
            log.debug("请求路径"+ url + ",请求参数" + json);
            json = URLEncoder.encode(json, "utf-8");
            URL targetUrl = new URL(url);
            httpConnection = (HttpURLConnection)targetUrl.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);
            httpConnection.setUseCaches(false);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.setRequestProperty("Connection", "Keep-Alive");
            for (Map.Entry<String, String> entry : headParams.entrySet())
                httpConnection.setRequestProperty(entry.getKey(), entry.getValue());
            httpConnection.setRequestProperty("signstr", sign);
            httpConnection.setRequestProperty("headParams",jsonParam);
            httpConnection.setConnectTimeout(30000);
            httpConnection.setReadTimeout(60000);
            outputStream = httpConnection.getOutputStream();
            outputStream.write(json.getBytes());
            outputStream.flush();
            if (httpConnection.getInputStream() != null) {
                if (httpConnection.getResponseCode() != 200)
                    throw new ErpRunException("请求失败，失败原因码："+ httpConnection.getResponseCode());
                responseBuffer = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                String output = "";
                while ((output = responseBuffer.readLine()) != null)
                    re.append(new String(output.getBytes(), "UTF-8"));
                httpConnection.disconnect();
                URLDecoder urlDecoder = new URLDecoder();
                jsonStr = URLDecoder.decode(re.toString(), "UTF-8");
                log.debug("返回结果"+ jsonStr);
            }
        } catch (Exception e) {
            throw new ErpRunException("请求接口出错", e);
        } finally {
            try {
                if (httpConnection != null)
                    httpConnection.disconnect();
                if (outputStream != null)
                    outputStream.close();
                if (responseBuffer != null)
                    responseBuffer.close();
            } catch (Exception e) {
                throw new ErpRunException("关闭请求接口出错", e);
            }
        }
        return jsonStr;
    }

    public void addErpLog(String supplyId, String clientLog, Integer logApiType, Integer logSourceType, Integer logType, String remark) {
        try {
            ErpLog erpLog = new ErpLog();
            erpLog.setClientLog(clientLog);
            erpLog.setLogApiType(logApiType);
            erpLog.setLogSourceType(logSourceType);
            erpLog.setLogType(logType);
            erpLog.setSupplierId(supplyId);
            erpLog.setRemark(remark);
            String json = JSONObject.toJSONString(erpLog);
            if (json == null || json.trim().equals(""))
                throw new ErpRunException("解析出错");
            Map<String, String> headParams = new HashMap<>();
            headParams.put("method", "addEroLog");
            headParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
            headParams.put("partnerid", erpLog.getSupplierId());
            getPost(ConfigHelpUtils.getConfigString("addEroLog"), json, headParams);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void addErpLog(ErpLog erpLog) {
        try {
            String json = JSONObject.toJSONString(erpLog);
            if (json == null || json.trim().equals(""))
                throw new ErpRunException("解析出错");
                        Map<String, String> headParams = new HashMap<>();
            headParams.put("method", "addEroLog");
            headParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
            headParams.put("partnerid", erpLog.getSupplierId());
            getPost(ConfigHelpUtils.getConfigString("addEroLog"), json, headParams);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static void main(String[] args) {
        ErpCommon.getInstance();
        PopRequest pr = new PopRequest();
        Map<String, String> headParams = new HashMap<>();
        headParams.put("method", "222");
        headParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
        headParams.put("partnerid", "111");
        String returnValueJson = pr.getPost("http://localhost:8888" +
                ConfigHelpUtils.getConfigString("updateProduceAndPriceNewUrl"), "", headParams);
    }
}
