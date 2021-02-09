package com.wh.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class CommonConstants {
    public static final String code_success = "1";

    public static final String testDDXUrl = "testDDXUrl";

    public static final String updateHeartBeatTime = "updateHeartBeatTime";

    public static final String getVersion = "getVersion";

    public static final String saveCustDataUrl = "saveCustDataUrl";

    public static final String delCustDataUrl = "delCustDataUrl";

    public static final String updateProduceAndPriceUrl = "updateProduceAndPriceUrl";

    public static final String updateProduceAndPriceNewUrl = "updateProduceAndPriceNewUrl";

    public static final String updateProduceAndPriceVerticalUrl = "updateProduceAndPriceVerticalUrl";

    public static final String updateProductUrl = "updateProductUrl";

    public static final String updateProductValidUrl = "updateProductValidUrl";

    public static final String updateProductAndStockUrl = "updateProductAndStockUrl";

    public static final String productInventoryGetCacheNewFlag = "productInventoryGetCacheNewFlag";

    public static final String productInfoGetCacheNewFlag = "productInfoGetCacheNewFlag";

    public static final String getCustomerInfoAllSyncFlag = "getCustomerInfoAllSyncFlag";

    public static final String productPriceGetCacheNewFlag = "productPriceGetCacheNewFlag";

    public static final String getProductInventoryBySupplyId = "getProductInventoryBySupplyId";

    public static final String getProductSpuIncrease = "getProductSpuIncrease";

    public static final String getProductPriceBySupplyId = "getProductPriceBySupplyId";

    public static final String productInventoryDeleteCacheNewFlag = "productInventoryDeleteCacheNewFlag";

    public static final String productPriceDeleteCacheNewFlag = "productPriceDeleteCacheNewFlag";

    public static final String updateCustomer = "updateCustomer";

    public static final String queryOrderIssuedUrl = "queryOrderIssuedUrl";

    public static final String queryOrderCustomerUrl = "queryOrderCustomerUrl";

    public static final String queryOrderCancelToErpUrl = "queryOrderCancelToErpUrl";

    public static final String notificationOrderIssuedSuccess = "notificationOrderIssuedSuccess";

    public static final String notificationOrderCustomerSuccess = "notificationOrderCustomerSuccess";

    public static final String queryOrderCodeReturnUrl = "queryOrderCodeReturnUrl";

    public static final String noficationOrdeReturnUrl = "noficationOrdeReturnUrl";

    public static final String orderProgressReturnUrl = "orderProgressReturnUrl";

    public static final String queryOrderExpressReturnUrl = "queryOrderExpressReturnUrl";

    public static final String orderElectronicInvoiceReturnUrl = "orderElectronicInvoiceReturnUrl";

    public static final String orderDelayExpressReturnUrl = "orderDelayExpressReturnUrl";

    public static final String noficationOrdeCancelUrl = "noficationOrdeCancelUrl";

    public static final String queryOrderIssuedExceptionUrl = "queryOrderIssuedExceptionUrl";

    public static final String addEroLog = "addEroLog";

    public static final String heartBeatUrl = "heartBeatUrl";

    public static final String versionHeartBeatUrl = "versionHeartBeatUrl";

    public static final String productPlaceNoUrl = "productPlaceNoUrl";

    public static final String panelConfigUrl = "panelConfigUrl";

    public static final String isTempDBClearWantedNowUrl = "isTempDBClearWantedNowUrl";

    public static final String getSqlTaskUrl = "getSqlTaskUrl";

    public static final String clientRestartUrl = "clientRestartUrl";

    public static final String isClientRestartUrl = "isClientRestartUrl";

    public static String saveSqlTaskUrl = "saveSqlTaskUrl";

    public static String exceptionOrderIssuedUrl = "exceptionOrderIssuedUrl";

    public static String exceptionOrderIssuedBackUrl = "exceptionOrderIssuedBackUrl";

    public static final String filePath = "filePath";

    public static final String supplyId = "yywSecret";

    public static final String secretKey = "yywKey";

    public static final String taskPriceTime = "taskPriceTime";

    public static final String taskProductTime = "taskProductTime";

    public static final String taskProduceStockTime = "taskProduceStockTime";

    public static final String taskOrderIssuedTime = "taskOrderIssuedTime";

    public static final String taskOrderReturnTime = "taskOrderReturnTime";

    public static final String SQL_DATA_TYPE_HORIZONTAL = "1";

    public static final String SQL_DATA_TYPE_VERTICAL = "2";

    public static final String CUSTOMERIDLINK_PREX = "http://oms.yaoex.com/order/downloadImg/";

    public static final int DATA_LIMIT = 200;

    public static String notificationOrderCancelSuccess = "notificationOrderCancelSuccess";

    public static final String TASK_CODE_LOCK = "0";

    public static final int TASK_LOCK_STATUS_NOT_COMPLETE_TASK = 0;

    public static final int TASK_LOCK_STATUS_COMPLETE_TASK = 1;

    public static final int TASK_LOCK_STATUS_NOT_COMPLETE_JOB = 2;

    public static final int TASK_LOCK_STATUS_COMPLETE_JOB = 3;

    public static String getString(Object object) throws ErpException {
        try {
            if (object == null)
                return null;
            return object.toString().trim();
        } catch (Exception e) {
            throw new ErpException("该字段类型["+ object.getClass() + "] , 数值["+ object.toString() + "]转换失败! ", e);
        }
    }

    public static Integer getInteger(Object object) throws ErpException {
        try {
            if (object == null)
                return null;
            return Integer.valueOf(Integer.parseInt(object.toString()));
        } catch (Exception e) {
            throw new ErpException("该字段类型["+ object.getClass() + "] , 数值["+ object.toString() + "]转换失败! ", e);
        }
    }

    public static Integer getInteger2(Object object) throws ErpException {
        try {
            if (object == null)
                return null;
            BigDecimal tmp = new BigDecimal(object.toString());
            return Integer.valueOf(tmp.intValue());
        } catch (Exception e) {
            throw new ErpException("该字段类型["+ object.getClass() + "] , 数值["+ object.toString() + "]转换失败! ", e);
        }
    }

    public static String getDate(Object object, String format) throws ErpException {
        try {
            if (object == null)
                return null;
            if (object instanceof Date)
                return Utils.dateFormateToString((Date)object, format);
            if (object instanceof String)
                return object.toString();
            if (object instanceof Integer)
                return object.toString();
            throw new ErpException("该字段类型["+ object.getClass() + "]不存在");
        } catch (Exception e) {
            throw new ErpException("该字段类型["+ object.getClass() + "] , 数值["+ object.toString() + "]转换失败! ", e);
        }
    }

    public static synchronized BigDecimal getBigDecimal(Object object) throws ErpException {
        try {
            if (object == null)
                return null;
            if (object instanceof Integer)
                return new BigDecimal(((Integer)object).intValue());
            if (object instanceof Long)
                return new BigDecimal(((Long)object).longValue());
            if (object instanceof Double)
                return new BigDecimal(((Double)object).doubleValue());
            if (object instanceof BigDecimal)
                return (BigDecimal)object;
            if (object instanceof String)
                return new BigDecimal((String)object);
            if (object instanceof Number)
                return new BigDecimal(((Number)object).doubleValue());
            if (object instanceof BigInteger)
                return new BigDecimal((BigInteger)object);
            if (object instanceof Float)
                return new BigDecimal(object.toString());
            if (object instanceof Byte)
                return new BigDecimal(object.toString());
            if (object instanceof Short)
                return new BigDecimal(object.toString());
            if (object instanceof Boolean) {
                if (((Boolean)object).booleanValue())
                    return new BigDecimal(1);
                return new BigDecimal(0);
            }
            throw new ErpException("该字段类型["+ object.getClass() + "]不存在");
        } catch (ErpException e) {
            throw e;
        } catch (Exception e) {
            throw new ErpException("该字段类型["+ object.getClass() + "] , 数值["+ object.toString() + "]转换失败! ", e);
        }
    }
}
