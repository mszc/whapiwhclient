package com.wh.util;

import erp.ErpSql;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;
public class DataBaseConnection {
    private static Logger logger = Logger.getLogger(DataBaseConnection.class);

    public static String url;

    public static String driverName;

    public static String dbName;

    public static String passWord;

    public static String userName;

    public static String FLAG = "flag";

    public static String RESULT = "result";

    private DataBaseConnection() {}

    private static class LazyHolder {
        private static final DataBaseConnection INSTANCE = new DataBaseConnection();
    }

    public static final DataBaseConnection getInstance() {
        return LazyHolder.INSTANCE;
    }

    public Connection openCon() throws Exception {
        ErpCommon.getInstance();
        ErpSql es = ErpCommon.es;
        if (es == null)
            throw new ErpException("数据库配置有误，请填写正确的数据库配置");
        if (es.getSqlType() == null || es.getSqlType().trim().equals(""))
            throw new ErpException("数据库配置有误，请填写正确的数据库配置");
        if (es.getSqlType().trim().equals("Mysql"))
            return openMysqlCon(es);
        if (es.getSqlType().trim().equals("SQL Server"))
            return openSqlServerCon(es);
        if (es.getSqlType().trim().equals("SQL Server2000"))
            return openSqlServerConForVersion2000(es);
        if (es.getSqlType().trim().equals("Oracle")) {
            if (es.getOracleType() == null || es.getOracleType().trim().equals(""))
                throw new ErpException("数据库配置有误，oracle类型不能为空！");
            return openOracleCon(es);
        }
        if (es.getSqlType().trim().equals("DB2"))
            return openDB2Con(es);
        throw new ErpException("数据库类型误，请填写正确的数据类型");
    }

    public Connection openOracleCon(ErpSql es) throws Exception {
        String oracleType = es.getOracleType();
        driverName = "oracle.jdbc.driver.OracleDriver";
        dbName = es.getSqlName();
        userName = es.getSqlLoginName();
        passWord = es.getSqlLoginPW();
        url = "jdbc:oracle:thin:@" + es.getSqlIp() + ":" + es.getSqlPort() + ":" + es.getSqlName();
        if (oracleType.equals("serviceName"))
            url = "jdbc:oracle:thin:@//" + es.getSqlIp() + ":" + es.getSqlPort() + "/" + es.getSqlName();
        try {
            /*System.out.println("url: "+url);
            System.out.println("userName: "+userName);
            System.out.println("passWord: "+passWord);
*/
            Class.forName(driverName).newInstance();
            return DriverManager.getConnection(url, userName, passWord);
        } catch (Exception e) {
            throw new ErpException("连接数据库出错，请填写正确的数据库配置", e);
        }
    }

    public Connection openMysqlCon(ErpSql es) throws Exception {
        driverName = "com.mysql.jdbc.Driver";
        dbName = es.getSqlName();
        userName = es.getSqlLoginName();
        passWord = es.getSqlLoginPW();
        url = "jdbc:mysql://" + es.getSqlIp() + ":" + es.getSqlPort() + "/" + es.getSqlName();
        try {
            Class.forName(driverName).newInstance();
            return DriverManager.getConnection(url, userName, passWord);
        } catch (Exception e) {
            throw new ErpException("连接数据库出错，请填写正确的数据库配置", e);
        }
    }

    public Connection openSqlServerCon(ErpSql es) throws Exception {
        driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        dbName = es.getSqlName();
        userName = es.getSqlLoginName();
        passWord = es.getSqlLoginPW();
        if (StringUtils.isBlank(userName)) {
            url = "jdbc:sqlserver://" + es.getSqlIp();
            if (!StringUtils.isBlank(es.getSqlPort())) {
                url += ":" + es.getSqlPort() + ";";
            } else {
                url += ";";
            }
            url += "integratedSecurity=true;DatabaseName=" + es.getSqlName();
            try {
                Class.forName(driverName);
                return DriverManager.getConnection(url);
            } catch (Exception e) {
                throw new ErpException("WINDOWS连接数据库出错，请填写正确的数据库配置", e);
            }
        }
        url = "jdbc:sqlserver://" + es.getSqlIp() + ":" + es.getSqlPort() + ";DatabaseName=" + es.getSqlName();
        try {
            Class.forName(driverName);
            return DriverManager.getConnection(url, userName, passWord);
        } catch (Exception e) {
            throw new ErpException("连接数据库出错，请填写正确的数据库配置", e);
        }
    }

    public Connection openSqlServerConForVersion2000(ErpSql es) throws Exception {
        driverName = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
        dbName = es.getSqlName();
        userName = es.getSqlLoginName();
        passWord = es.getSqlLoginPW();
        url = "jdbc:microsoft:sqlserver://" + es.getSqlIp() + ":" + es.getSqlPort() + ";DatabaseName=" + es.getSqlName();
        try {
            Class.forName(driverName);
            return DriverManager.getConnection(url, userName, passWord);
        } catch (Exception e) {
            throw new ErpException("连接数据库出错，请填写正确的数据库配置", e);
        }
    }

    public Connection openDB2Con(ErpSql es) throws Exception {
        driverName = "com.ibm.db2.jcc.DB2Driver";
        dbName = es.getSqlName();
        userName = es.getSqlLoginName();
        passWord = es.getSqlLoginPW();
        url = "jdbc:db2://" + es.getSqlIp() + ":" + es.getSqlPort() + "/" + es.getSqlName();
        try {
            Class.forName(driverName);
            return DriverManager.getConnection(url, userName, passWord);
        } catch (Exception e) {
            throw new ErpException("连接数据库出错，请填写正确的数据库配置", e);
        }
    }

    public void closeConAll(Connection conn, PreparedStatement prsts, ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
            if (prsts != null)
                prsts.close();
            if (conn != null)
                conn.close();
        } catch (Exception e) {
            new ErpException("关闭数据库连接失败", e);
        }
    }

    public List executeQuery(String sql) throws Exception {
        List<Map<Object, Object>> list = null;
        ResultSet rs = null;
        PreparedStatement prsts = null;
        Connection connection = null;
        Map<Object, Object> map = null;
        try {
            connection = openCon();
            prsts = connection.prepareStatement(sql);
            rs = prsts.executeQuery();
            list = new ArrayList();
            ResultSetMetaData rsm = rs.getMetaData();
            while (rs.next()) {
                map = new HashMap<>();
                for (int i = 1; i <= rsm.getColumnCount(); i++)
                    map.put(rsm.getColumnLabel(i), rs.getObject(rsm.getColumnLabel(i)));
                list.add(map);
            }
        } catch (Exception e) {
            throw new ErpException("查询数据库出错"+ sql, e);
        } finally {
            closeConAll(connection, prsts, rs);
        }
        return list;
    }

    public Map<String, Object> createOrderMidTable(List<String> sqls) {
        boolean flag = true;
        String result = "";
        Statement stmt = null;
        Connection connection = null;
        Map<String, Object> createBack = new HashMap<>();
        try {
            connection = openCon();
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            for (String sql : sqls) {
                logger.info("订单中间表执行sql"+ sql);
                        stmt.addBatch(sql);
            }
            stmt.executeBatch();
            connection.commit();
        } catch (Exception e) {
            flag = false;
            result = "建中间表"+ e.getMessage();
            logger.error("数据库创建订单中间表出错:["+ e.getMessage() + "]");
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException sqlException) {
                logger.error("数据库创建订单中间表回滚失败=" + sqlException.getMessage());
            }
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (connection != null)
                    connection.close();
            } catch (Exception e) {
                new ErpException("关闭数据库连接失败", e);
            }
        }
        createBack.put(FLAG, Boolean.valueOf(flag));
        createBack.put(RESULT, result);
        return createBack;
    }

    @Deprecated
    private String dealSqlLoseSpace(String sql) {
        sql = sql.replaceAll("from", " from ").replaceAll("FROM", " FROM ").replaceAll("where", " where ").replaceAll("WHERE", " WHERE ").replaceAll("left join", " left join ").replaceAll("left join", " left join ").replaceAll("right join", " right join ").replaceAll("RIGHT JOIN", " RIGHT JOIN ").replaceAll("group by", " group by ").replaceAll("GROUP BY", " GROUP BY ").replaceAll("order by", " order by ").replaceAll("ORDER BY", " ORDER BY ").replaceAll("case when", " case when ").replaceAll("CASE WHEN", " CASE WHEN ");
        return sql;
    }
/*
    public boolean insertExceptionOrderInfo(ExceptionOrderSql exceptionOrderSql, String supplierId) {
        String repeatProcessSql = exceptionOrderSql.getExceptionRepeatSql();
        List<String> exceptionOrderDetailSql = exceptionOrderSql.getExceptionDetailSql();
        String exceptionOrderMainSql = exceptionOrderSql.getExceptionMainSql();
        boolean flag = true;
        Statement stmt = null;
        Connection connection = null;
        ResultSet rs = null;
        String executeSql = "";
        try {
            connection = openCon();
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            if (StringUtils.isNotBlank(repeatProcessSql)) {
                executeSql = repeatProcessSql;
                rs = stmt.executeQuery(executeSql);
                if (rs.next()) {
                    int count = rs.getInt(1);
                    if (count > 0) {
                        logger.warn("异常订单信息已存在，订单重复下发"+ repeatProcessSql);
                        return true;
                    }
                }
            }
            if (StringUtils.isNotBlank(exceptionOrderMainSql)) {
                executeSql = exceptionOrderMainSql;
                stmt.executeUpdate(executeSql);
            }
            if (exceptionOrderDetailSql != null && exceptionOrderDetailSql.size() > 0)
                for (String childSql : exceptionOrderDetailSql) {
                    for (String sql : childSql.split(";")) {
                        executeSql = sql;
                        stmt.executeUpdate(executeSql);
                    }
                }
            connection.commit();
        } catch (Exception e) {
            flag = false;
            PopRequest popRequest = new PopRequest();
            ErpLog erpLog = new ErpLog();
            erpLog.setClientLog(e
                    .getMessage() + ",执行SQL脚本失败:"+ executeSql + ",堆栈信息："+ ExceptionUtils.getFullStackTrace(e));
            erpLog.setLogApiType(Integer.valueOf(13));
            erpLog.setLogSourceType(Integer.valueOf(1));
            erpLog.setLogType(Integer.valueOf(1));
            erpLog.setSupplierId(supplierId);
            erpLog.setRemark("异常订单下发插入数据失败");
                    popRequest.addErpLog(erpLog);
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException sqlException) {
                logger.error("插入订单回滚失败="+ sqlException.getMessage());
            }
            logger.error("插入订单下发数据失败：["+ e.getMessage() + "]");
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (connection != null)
                    connection.close();
            } catch (Exception e) {
                new ErpException("关闭数据连接失败", e);
            }
        }
        return flag;
    }*/
/*
    public boolean insertOrderIssuedInfo(String repeatProcessSql, String masterOrderSql, List<String> detailOrderSqlList, String updateMasterOrderSql, List<String> updateDetailOrderSqlList, String insertCustomerSqlContext, String supplierId) {
        boolean flag = true;
        Statement stmt = null;
        Connection connection = null;
        ResultSet rs = null;
        String executeSql = "";
        try {
            connection = openCon();
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            if (StringUtils.isNotBlank(repeatProcessSql)) {
                executeSql = repeatProcessSql;
                rs = stmt.executeQuery(executeSql);
                if (rs.next()) {
                    int count = rs.getInt(1);
                    if (count > 0) {
                        boolean updateFlag = false;
                        if (StringUtils.isNotBlank(updateMasterOrderSql)) {
                            logger.info("订单更新主sql==="+ updateMasterOrderSql);
                                    updateFlag = true;
                            stmt.executeUpdate(updateMasterOrderSql);
                        }
                        if (updateDetailOrderSqlList != null && updateDetailOrderSqlList.size() > 0)
                            for (String childSql : updateDetailOrderSqlList) {
                                logger.info("订单子sql==="+ childSql);
                                for (String sql : childSql.split(";")) {
                                    logger.info("订单子sql==="+ sql);
                                            updateFlag = true;
                                    stmt.executeUpdate(sql);
                                }
                            }
                        connection.commit();
                        if (updateFlag) {
                            logger.warn("订单信息已存在，订单更新："+ repeatProcessSql);
                        } else {
                            logger.warn("订单信息已存在，订单重复下发"+ repeatProcessSql);
                        }
                        return true;
                    }
                }
            }
            String firstExec = ReadProperties.getProperties("firstExec");
            if ("MX".equals(firstExec)) {
                if (detailOrderSqlList != null && detailOrderSqlList.size() > 0)
                    for (String childSql : detailOrderSqlList) {
                        logger.info("订单子sql==="+ childSql);
                        for (String sql : childSql.split(";")) {
                            executeSql = sql;
                            stmt.executeUpdate(executeSql);
                        }
                    }
                if (masterOrderSql != null && !"".equals(masterOrderSql.trim())) {
                    logger.info("订单主sql==="+ masterOrderSql);
                            executeSql = masterOrderSql;
                    stmt.executeUpdate(executeSql);
                }
                if (insertCustomerSqlContext != null && !"".equals(insertCustomerSqlContext.trim())) {
                    logger.info("订单买家sql==="+ insertCustomerSqlContext);
                            executeSql = insertCustomerSqlContext;
                    stmt.executeUpdate(executeSql);
                }
            } else {
                if (masterOrderSql != null && !"".equals(masterOrderSql.trim())) {
                    logger.info("订单主sql==="+ masterOrderSql);
                            executeSql = masterOrderSql;
                    stmt.executeUpdate(executeSql);
                }
                if (insertCustomerSqlContext != null && !"".equals(insertCustomerSqlContext.trim())) {
                    logger.info("订单买家sql==="+ insertCustomerSqlContext);
                            executeSql = insertCustomerSqlContext;
                    stmt.executeUpdate(executeSql);
                }
                if (detailOrderSqlList != null && detailOrderSqlList.size() > 0)
                    for (String childSql : detailOrderSqlList) {
                        logger.info("订单子sql==="+ childSql);
                        for (String sql : childSql.split(";")) {
                            executeSql = sql;
                            stmt.executeUpdate(executeSql);
                        }
                    }
            }
            connection.commit();
        } catch (Exception e) {
            flag = false;
            PopRequest popRequest = new PopRequest();
            ErpLog erpLog = new ErpLog();
            erpLog.setClientLog(e
                    .getMessage() + ",执行SQL脚本失败："+ executeSql + ",堆栈信息："+ ExceptionUtils.getFullStackTrace(e));
            erpLog.setLogApiType(Integer.valueOf(4));
            erpLog.setLogSourceType(Integer.valueOf(1));
            erpLog.setLogType(Integer.valueOf(1));
            erpLog.setSupplierId(supplierId);
            erpLog.setRemark("订单下发插入数据失败");
                    popRequest.addErpLog(erpLog);
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException sqlException) {
                logger.error("插入订单回滚失败="+ sqlException.getMessage());
            }
            logger.error("插入订单下发数据失败"+ e.getMessage() + "]");
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (connection != null)
                    connection.close();
            } catch (Exception e) {
                new ErpException("关闭数据库连接失败", e);
            }
        }
        return flag;
    }*/

    public int getTotalNum(String sql) throws Exception {
        int total = 0;
        if (sql != null && !sql.trim().equals("")) {
            String countSql = "select count(*) NUM from (" + sql + ") lol";
            logger.info("根据sql获取数据："+ countSql);
                    List<HashMap> countResult = executeQuery(countSql);
            try {
                if (countResult != null && countResult.size() > 0)
                    for (HashMap map : countResult)
                        total = Integer.valueOf(map.get("NUM").toString()).intValue();
            } catch (Exception e) {
                throw new ErpException("获取集合大小失败", e);
            }
        }
        return total;
    }

    /*public boolean updateOrderCustomerInfo(String updateSql, String supplierId) {
        boolean flag = true;
        Statement stmt = null;
        Connection connection = null;
        ResultSet rs = null;
        String executeSql = "";
        try {
            connection = openCon();
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            if (updateSql != null && !"".equals(updateSql.trim())) {
                logger.info("订单客户更新sql==="+ updateSql);
                        executeSql = updateSql;
                stmt.executeUpdate(executeSql);
            }
            connection.commit();
        } catch (Exception e) {
            flag = false;
            PopRequest popRequest = new PopRequest();
            ErpLog erpLog = new ErpLog();
            erpLog.setClientLog(e
                    .getMessage() + ",执行SQL脚本失败："+ executeSql + ",堆栈信息："+ ExceptionUtils.getFullStackTrace(e));
            erpLog.setLogApiType(Integer.valueOf(16));
            erpLog.setLogSourceType(Integer.valueOf(1));
            erpLog.setLogType(Integer.valueOf(1));
            erpLog.setSupplierId(supplierId);
            erpLog.setRemark("订单更新客户下发插入数据失败");
                    popRequest.addErpLog(erpLog);
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException sqlException) {
                logger.error("更新订单客户回滚失败="+ sqlException.getMessage());
            }
            logger.error("更新订单客户下发数据失败"+ e.getMessage() + "]");
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (connection != null)
                    connection.close();
            } catch (Exception e) {
                new ErpException("关闭数据库连接失败", e);
            }
        }
        return flag;
    }*/

    /*public boolean insertOrderCancelToErpInfo(String resultSql, String yywSecret) {
        boolean flag = true;
        Statement stmt = null;
        Connection connection = null;
        ResultSet rs = null;
        String executeSql = "";
        try {
            connection = openCon();
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            if (resultSql != null && !"".equals(resultSql.trim())) {
                logger.info("订单取消的sql==="+ resultSql);
                        executeSql = resultSql;
                stmt.executeUpdate(executeSql);
            }
            connection.commit();
        } catch (Exception e) {
            flag = false;
            PopRequest popRequest = new PopRequest();
            ErpLog erpLog = new ErpLog();
            erpLog.setClientLog(e
                    .getMessage() + ",执行SQL脚本失败："+ executeSql + ",堆栈信息："+ ExceptionUtils.getFullStackTrace(e));
            erpLog.setLogApiType(Integer.valueOf(17));
            erpLog.setLogSourceType(Integer.valueOf(1));
            erpLog.setLogType(Integer.valueOf(1));
            erpLog.setSupplierId(yywSecret);
            erpLog.setRemark("订单取消下发插入数据失败");
                    popRequest.addErpLog(erpLog);
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException sqlException) {
                logger.error("更新订单客户回滚失败="+ sqlException.getMessage());
            }
            logger.error("更新订单客户下发数据失败：["+ e.getMessage() + "]");
        } finally {
            closeConn(rs, stmt, connection);
        }
        return flag;
    }*/

    private void closeConn(ResultSet rs, Statement stmt, Connection connection) {
        try {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
            if (connection != null)
                connection.close();
        } catch (Exception e) {
            new ErpException("关闭数据库连接失败", e);
        }
    }
}
