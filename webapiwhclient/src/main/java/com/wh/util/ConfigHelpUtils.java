package com.wh.util;

import erp.ErpSql;
import org.apache.commons.lang.StringUtils;

import java.util.ResourceBundle;

public class ConfigHelpUtils {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("project_config");

    private  static ResourceBundle getProjectConfig() {
        return bundle;
    }
    public static String getConfigString(String key) {
        ErpCommon.getInstance();
        ErpSql sql = ErpCommon.es;
        String servicePath = sql.getServicePath();
        System.out.println(servicePath);
        if (StringUtils.isEmpty(servicePath) || "null".equals(servicePath))
            servicePath = "http://ddx.yaoex.com";
        if ("addEroLog".equals(key))
            return servicePath + "/rest/company/addEroLog";
        if ("updateProduceAndPriceNewUrl".equals(key))
            return servicePath + "/product/price/updatePrice";
        if ("updateProduceAndPriceVerticalUrl".equals(key))
            return servicePath + "/product/priceVertical/updatePrice";
        if ("updateProductUrl".equals(key))
            return servicePath + "/product/productInfo/updateProduct";
        if ("updateProductValidUrl".equals(key))
            return servicePath + "/product/productInfo/updateProductInfo";
        if ("updateProductAndStockUrl".equals(key))
            return servicePath + "/product/inventory/updateInventory";
        if ("updateCustomer".equals(key))
            return servicePath + "/customer/createCustomerRelation";
        if ("queryOrderIssuedUrl".equals(key))
            return servicePath + "/order/orderIssued";
        if ("queryOrderIssuedExceptionUrl".equals(key))
            return servicePath + "/order/orderException/getOrderIssuedExceptionList";
        if ("notificationOrderIssuedSuccess".equals(key))
            return servicePath + "/order/orderIssuedBack";
        if ("queryOrderCodeReturnUrl".equals(key))
            return servicePath + "/order/getOrderDeliveryList";
        if ("noficationOrdeReturnUrl".equals(key))
            return servicePath + "/order/orderDelivery";
        if ("orderProgressReturnUrl".equals(key))
            return servicePath + "/order/orderProgress";
        if ("orderDelayExpressReturnUrl".equals(key))
            return servicePath + "/carrier/updateCarrierInfo";
        if ("queryOrderExpressReturnUrl".equals(key))
            return servicePath + "/carrier/queryWithoutCarrierOrderCodes";
        if ("noficationOrdeCancelUrl".equals(key))
            return servicePath + "/order/orderCancel";
        if ("testDDXUrl".equals(key))
            return servicePath + "/clientConfig/test";
        if ("saveCustDataUrl".equals(key))
            return servicePath + "/rest/company/saveSaleData";
        if ("delCustDataUrl".equals(key))
            return servicePath + "/data/delData";
        if ("getVersion".equals(key))
            return servicePath + "/clientConfig/getVersion";
        if ("productPlaceNoUrl".equals(key))
            return servicePath + "/product/syncPlaceNo";
        if ("panelConfigUrl".equals(key))
            return servicePath + "/clientConfig/getAllPanelConfig";
        if ("versionHeartBeatUrl".equals(key))
            return servicePath + "/versionManage/syncVersion";
        if ("isTempDBClearWantedNowUrl".equals(key))
            return servicePath + "/erpTempDBClear/clearNow";
        if ("getSqlTaskUrl".equals(key))
            return servicePath + "/erp/sqltask/getSqlTask";
        if (CommonConstants.saveSqlTaskUrl.equals(key))
            return servicePath + "/erp/sqltask/saveSqlTask";
        if ("clientRestartUrl".equals(key))
            return servicePath + "/clientRestart/restartTomcat";
        if ("isClientRestartUrl".equals(key))
            return servicePath + "/clientRestart/isRestartSuccess";
        if ("updateHeartBeatTime".equals(key))
            return servicePath + "/clientConfig/updateHeartBeatTime";
        if ("productInventoryGetCacheNewFlag".equals(key))
            return servicePath + "/product/inventory/getCacheNewFlag";
        if ("productInventoryDeleteCacheNewFlag".equals(key))
            return servicePath + "/product/inventory/deleteCacheNewFlag";
        if ("getProductInventoryBySupplyId".equals(key))
            return servicePath + "/product/inventory/getProductInventoryBySupplyId";
        if (CommonConstants.exceptionOrderIssuedUrl.equals(key))
            return servicePath + "/exceptionOrder/exceptionOrderIssued";
        if (CommonConstants.exceptionOrderIssuedBackUrl.equals(key))
            return servicePath + "/exceptionOrder/exceptionOrderIssuedBack";
        if ("getProductPriceBySupplyId".equals(key))
            return servicePath + "/product/price/getProductPriceBySupplyId";
        if ("productPriceGetCacheNewFlag".equals(key))
            return servicePath + "/product/price/getCacheNewFlag";
        if ("productPriceDeleteCacheNewFlag".equals(key))
            return servicePath + "/product/price/deleteCacheNewFlag";
        if ("orderElectronicInvoiceReturnUrl".equals(key))
            return servicePath + "/electronicInvoice/saveElectronicInvoice";
        if ("getProductSpuIncrease".equals(key))
            return servicePath + "/product/inventory/getProductSpuIncrease";
        if ("getCustomerInfoAllSyncFlag".equals(key))
            return servicePath + "/customer/ifSyncAll";
        if ("queryOrderCustomerUrl".equals(key))
            return servicePath + "/orderCustomerUpdate/getErpOrderCustomerList";
        if ("notificationOrderCustomerSuccess".equals(key))
            return servicePath + "/orderCustomerUpdate/updateErpOrderCustomer";
        if ("productInfoGetCacheNewFlag".equals(key))
            return servicePath + "/product/productInfo/getCacheNewFlag";
        if ("queryOrderCancelToErpUrl".equals(key))
            return servicePath + "/order/getCancelOrderList";
        if (CommonConstants.notificationOrderCancelSuccess.equals(key))
            return servicePath + "/order/updateCancelOrderIssueStatus";
        return "";
    }

    public static String getConfigByKey(String key) {
        ResourceBundle bundle = getProjectConfig();
        return bundle.getString(key);
    }


}
