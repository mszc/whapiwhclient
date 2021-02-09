package com.wh.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;

public class SignUtil {
    private static final Log logger = LogFactory.getLog(SignUtil.class);

    public static String md5Signature(Map<String, String> params, String secret) {
        String result = null;
        StringBuffer orgin = getBeforeSign(params, new StringBuffer(secret));
        if (orgin == null)
            return result;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = byte2hex(md.digest(orgin.toString().getBytes("utf-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getMd5Code(String origin) {
        if (StringUtils.isBlank(origin))
            return null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String result = byte2hex(md.digest(origin.toString().getBytes("utf-8")));
            return result;
        } catch (Exception e) {
            logger.error("加密数据异常", e);
            return null;
        }
    }

    private static String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
        }
        return hs.toString();
    }

    public static StringBuffer getBeforeSign(Map<String, String> params, StringBuffer orgin) {
        if (params == null)
            return null;
        Object[] key = params.keySet().toArray();
        Arrays.sort(key);
        for (int i = 0; i < key.length; i++)
            orgin.append(key[i]).append(params.get(key[i]));
        return orgin;
    }

    public static void main(String[] args) {
        System.out.println(getMd5Code("Hello world !"));
    }
}
