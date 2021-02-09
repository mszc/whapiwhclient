package com.wh.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class ReadProperties {
    private static Logger log = Logger.getLogger(ReadProperties.class);

    private static Properties dbProps = new Properties();

    static {
        loads();
    }

    public static synchronized void loads() {
        InputStream is = ReadProperties.class.getResourceAsStream("/project_config.properties");
        try {
            dbProps.load(is);
        } catch (Exception e) {
            System.err.println("不能读取属性文件，请确保project_config.properties在CLASSPATH指定的路径中");
        }
    }

    public static String getProperties(String keyName) {
        String res = "";
        try {
            loads();
            res = dbProps.getProperty(keyName);
        } catch (Exception e) {
            System.err.println("属性文件更新错误");
        }
        return res;
    }

    public static String getProperties(String keyName, String defaultValue) {
        String res = getProperties(keyName);
        if (StringUtils.isBlank(res))
            return defaultValue;
        return res;
    }

    public static int getIntProperties(String keyName, int defaultValue) {
        String stringValue = getProperties(keyName);
        if (StringUtils.isNotBlank(stringValue))
            try {
                int value = Integer.parseInt(stringValue);
                return value;
            } catch (NumberFormatException e) {
                log.error("解析配置数据异常", e);
                return defaultValue;
            }
        return defaultValue;
    }

    public static void writeProperties(String keyname, String keyvalue) {
        try {
            loads();
            String filePath = ReadProperties.class.getResource("/project_config.properties").toString();
            OutputStream fos = new FileOutputStream(filePath.substring(filePath.indexOf(":") + 2, filePath.length()));
            dbProps.setProperty(keyname, keyvalue);
            dbProps.store(fos, "Update '" + keyname + "' value");
        } catch (Exception e) {
            System.err.println("属性文件跟新错误");
        }
    }

    public static void main(String[] args) {
        int writeSyncRecordCount = getIntProperties("writeSyncRecordCount", 2000);
        System.out.println(writeSyncRecordCount);
    }
}
