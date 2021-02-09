package com.wh.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {
    private static final long serialVersionUID = 1L;

    private static final String ENCODING_UTF_8 = "UTF-8";

    public String javaToXml(Object o) {
        if (o == null)
            return null;
        StringWriter sw = new StringWriter();
        try {
            JAXBContext jAXBContext = JAXBContext.newInstance(new Class[] { o.getClass() });
            Marshaller marshaller = jAXBContext.createMarshaller();
            marshaller.marshal(o, sw);
        } catch (JAXBException e) {
            new ErpException("转换成xml出错", e);
        }
        return sw.toString();
    }

    public Object xmlToJava(String str, Object obj) {
        try {
            JAXBContext context = JAXBContext.newInstance(new Class[] { Object.class });
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return unmarshaller.unmarshal(new StringReader(str));
        } catch (JAXBException e) {
            new ErpException("转换成java对象出错", e);
            return obj;
        }
    }

    public void fileWrite(String filePath, String fileName, String context) {
        BufferedWriter writer = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists())
                dir.mkdirs();
            String path = filePath + File.separator + fileName;
            File outFile = new File(path);
            if (outFile.exists())
                outFile.delete();
            outFile.createNewFile();
            OutputStreamWriter writerStream = new OutputStreamWriter(new FileOutputStream(path), "UTF-8");
            writer = new BufferedWriter(writerStream);
            writer.write(context);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteFile(String path) {
        try {
            File file = new File(path);
            if (file.exists())
                file.delete();
        } catch (Exception exception) {}
    }

    public void writeNullFile(String path) {
        try {
            File f = new File(path);
            FileWriter fw = new FileWriter(f, false);
            fw.write("");
            fw.flush();
            fw.close();
        } catch (IOException iOException) {}
    }

    public static String readFileByLines(String fileName) throws Exception {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        if (!file.exists())
            return null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String tempString = null;
            while ((tempString = reader.readLine()) != null)
                sb.append(tempString);
            reader.close();
        } catch (IOException e) {
            throw e;
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException iOException) {}
        }
        return sb.toString();
    }

    public static String readFileByNewLines(String fileName) throws Exception {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        if (!file.exists())
            return null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String tempString = null;
            while ((tempString = reader.readLine()) != null)
                sb.append(tempString).append("\r\n");
            reader.close();
        } catch (IOException e) {
            throw e;
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException iOException) {}
        }
        return sb.toString();
    }

    public boolean checkTime(String start, String end) throws Exception {
        if (start == null || start.trim().equals(""))
            return false;
        if (end == null || end.trim().equals(""))
            return false;
        try {
            int stime = Integer.valueOf(start).intValue();
            int etime = Integer.valueOf(end).intValue();
            if (stime < etime)
                return true;
        } catch (NumberFormatException e) {
            new ErpException("时段设置有误，请检查并正确的配置", e);
        }
        return false;
    }

    public long reDelay(String time) {
        long delay = 0L;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date now1 = sdf.parse(sdf.format(new Date()));
            String s = sdf2.format(new Date()) + " " + time;
            Date now2 = sdf.parse(s);
            delay = now2.getTime() - now1.getTime();
            if (delay < 0L)
                delay = 0L;
        } catch (ParseException e) {
            new ErpException("时段设置有误，请检查并正确的配置", e);
        }
        return delay;
    }

    public boolean compareTime(String time, String min) {
        boolean flag = false;
        try {
            Calendar now = Calendar.getInstance();
            int hour = now.get(11);
            int minute = now.get(12);
            if (hour > Integer.valueOf(time).intValue())
                return flag;
            if (hour == Integer.valueOf(time).intValue() && minute > Integer.valueOf(min).intValue())
                return flag;
            if (hour <= Integer.valueOf(time).intValue()) {
                flag = true;
                return flag;
            }
        } catch (NumberFormatException e) {
            new ErpException("时段设置有误，请检查并正确的配置", e);
        }
        return flag;
    }

    public static String dateFormateToString(Date date, String mode) {
        SimpleDateFormat formate = new SimpleDateFormat(mode);
        String returnValue = formate.format(date);
        return returnValue;
    }

    public static String getStringValue(Object obj) {
        return (obj == null) ? " " : (obj.toString() + " ");
    }

    /*public static void main(String[] args) {
        try {
            PopRequest popRequest = new PopRequest();
            ErpLog erpLog = new ErpLog();
            erpLog.setClientLog(");
                    erpLog.setLogApiType(Integer.valueOf(4));
            erpLog.setLogSourceType(Integer.valueOf(1));
            erpLog.setLogType(Integer.valueOf(3));
            erpLog.setSupplierId("11111");
            erpLog.setRemark(");
                    popRequest.addErpLog(erpLog);
        } catch (Exception exception) {}
    }*/
}
