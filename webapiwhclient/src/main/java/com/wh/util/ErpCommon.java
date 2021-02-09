package com.wh.util;

/*import com.qn.common.ErpClientViewConfig;*/
/*import com.qn.common.ErpClientViewConfig;
import com.qn.common.ErpCreateOrderMidTable;
import com.yyw.yhyc.ddx.dto.erp.*;*/
import erp.ErpCustData;
import org.apache.commons.io.FileUtils;
import erp.ErpSql;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Observable;

public class ErpCommon extends Observable {
    private static Logger log = Logger.getLogger(ErpCommon.class);

    public static String jpsCmd;

    private static final String oldFilePath = "d:/tmp";

    public static String filePath;

    public static String fileSqlName = "sqlSet.xml";

    public static String fileProductName = "ProductPane.xml";

    public static String logFilePath;

    public static String logFile = "popErpLog.log";

    public static String fileProductAndPriceName = "ProductPricePane.xml";

    public static String fileProductAndStockName = "ProductStockPane.xml";

    public static String fileCustomerName = "CustomerPane.xml";

    public static String fileOrderIssuedName = "OrderIssuedPanel.xml";

    public static String fileOrderCustomerName = "OrderCustomerPanel.xml";

    public static String fileOrderReturnName = "OrderReturnPanel.xml";

    public static String fileOrderProgressReturnName = "OrderProgressReturn.xml";

    public static String fileDelayExpressReturnName = "OrderDelayExpressPanel.xml";

    public static String fileElectronicInvoiceName = "OrderElectronicInvoicePanel.xml";

    public static String fileOrderCancelName = "OrderCancelPanel.xml";

    public static String fileOrderCancelToErpName = "OrderCancelToErpPanel.xml";

    public static String fileProductValidName = "ProductValidPane.xml";

    public static String fileExceptionOrderName = "ExceptionOrderPanel.xml";

    public static String fileCustDataName = "CustDataPane.xml";

    public static String fileProductPlaceNoName = "ProductPlaceNo.xml";

    public static String fileErpClientViewConfigName = "ErpClientViewConfig.xml";

    public static String fileErpCreateOrderMidTable = "ErpCreateOrderMidTable.xml";

    public static ErpSql es;

    /*public static ErpProduct ep;

    public static ErpProductValid epv;

    public static ErpProductPrice epp;

    public static ErpProductStock erpProductStock;

    public static ErpCustomer erpCustomer;

    public static ErpOrderIssued erpOrderIssued;

    public static ErpOrderCustomerUpdate erpOrderCustomerUpdate;

    public static ErpOrderReturn erpOrderReturn;

    public static ErpOrderProgressReturn erpOrderProgressReturn;

    public static ErpOrderDelayExpressReturn erpOrderDelayExpressReturn;

    public static ErpOrderElectronicInvoice erpOrderElectronicInvoice;

    public static ErpOrderCancel erpOrderCancel;

    public static ErpOrderCancelToErp erpOrderCancelToErp;*/

    public static ErpCustData erpCustData;

   /* public static ErpProductPlaceNo erpProductPlaceNo;

    public static ErpClientViewConfig erpClientViewConfig;

    public static ErpCreateOrderMidTable erpCreateOrderMidTable;

    public static ErpExceptionOrder erpExceptionOrder;*/

    public static String logStr;

    public static boolean openLock = false;

    private static ErpCommon instance;

    static {
        getInstance();
        System.out.println("erpcommon静态代码块执行");
    }

    public static synchronized ErpCommon getInstance() {
        if (instance == null) {
            instance = new ErpCommon();
            logFilePath = System.getProperty("catalina.base") + File.separator + "logs";   // File.separator 在windows 表示 ‘\’ ,linux 表示 ‘/’
            File tmpFileSqlSet = new File("d:/tmp" + File.separator + fileSqlName);
            String filePathOld = "";
            if (tmpFileSqlSet.exists()) {
                filePathOld = "d:/tmp";
            } else {
                filePathOld = ConfigHelpUtils.getConfigByKey("filePath");
            }
            try {
                String basePath = ErpCommon.class.getResource("/").getPath();
                log.info("ErpCommon.class.getResource is basePath" + basePath);
                String batPath = (new File(basePath)).getParentFile().getParentFile().getAbsolutePath();
                log.info("ErpCommon.class.getResource is " + (new File(basePath)).getParentFile().getParentFile().getAbsolutePath());
                filePath = batPath + File.separator + "yycConfig";
                File oldFile = new File(filePathOld);
                File newFile = new File(filePath);
                if (oldFile.exists() && !newFile.exists()) {
                    FileUtils.copyDirectory(oldFile, newFile);
                    log.info("************************************配置文件复制成功:"+ filePathOld + "-->" + filePath);
                }
            } catch (IOException e) {
                log.error("配置文件复制error"+ filePathOld + "-->" + filePath + "*******" + e.getMessage());
            }
            jpsCmd = System.getProperty("catalina.base") + File.separator + "jdk7" + File.separator + "bin" + File.separator + "jps";
            log.info("ErpCommon filePath is " + filePath);
            es = instance.readSqlSet();
            /*
            ep = instance.readProductSet();
            epp = instance.readProPriSet();
            epv = instance.readProductValidSet();
            erpProductStock = instance.readProductStockSet();
            erpCustomer = instance.readCustomerSet();
            erpOrderIssued = instance.readOrderIssuedSet();
            erpOrderReturn = instance.readOrderReturnSet();
            erpOrderProgressReturn = instance.readOrderProgressReturnSet();
            erpOrderDelayExpressReturn = instance.readDelayExpressReturnSet();
            erpClientViewConfig = instance.readErpClientViewConfig();
            erpCreateOrderMidTable = instance.readErpMidTableConfig();
            erpOrderCancel = instance.readOrderCancelSet();
            erpOrderCancelToErp = instance.readOrderCancelToErpSet();
            */
            erpCustData = instance.readCustDataSet();
            /*
            erpProductPlaceNo = instance.readProductPlaceNoSet();
            erpExceptionOrder = instance.readErpExceptionOrderSet();
            erpOrderElectronicInvoice = instance.readOrderElectronicInvoiceSet();
            erpOrderCustomerUpdate = instance.readOrderCustomerUpdateSet();
            */
        }
        return instance;
    }
/*
    private ErpOrderCustomerUpdate readOrderCustomerUpdateSet() {
        ErpOrderCustomerUpdate oc = new ErpOrderCustomerUpdate();
        try {
            String xml = Utils.readFileByLines(filePath + File.separator + fileOrderCustomerName);
            if (xml != null && !xml.equals("")) {
                JAXBContext context = JAXBContext.newInstance(new Class[] { ErpOrderCustomerUpdate.class });
                Unmarshaller unmarshaller = context.createUnmarshaller();
                oc = (ErpOrderCustomerUpdate)unmarshaller.unmarshal(new StringReader(xml));
            }
        } catch (Exception e) {
            new ErpException("读取异常订单配置出错", e);
        }
        return oc;
    }


    private ErpProductPlaceNo readProductPlaceNoSet() {
        ErpProductPlaceNo ep = new ErpProductPlaceNo();
        try {
            String xml = Utils.readFileByLines(filePath + File.separator + fileProductPlaceNoName);
            if (xml != null && !xml.equals("")) {
                JAXBContext context = JAXBContext.newInstance(new Class[] { ErpProductPlaceNo.class });
                Unmarshaller unmarshaller = context.createUnmarshaller();
                ep = (ErpProductPlaceNo)unmarshaller.unmarshal(new StringReader(xml));
            }
        } catch (Exception e) {
            new ErpException("读取药品货位号同步配置出错", e);
        }
        return ep;
    }

    public ErpOrderReturn readOrderReturnSet() {
        ErpOrderReturn ep = new ErpOrderReturn();
        try {
            Utils u = new Utils();
            String xml = Utils.readFileByLines(filePath + File.separator + fileOrderReturnName);
            if (xml != null && !xml.equals("")) {
                JAXBContext context = JAXBContext.newInstance(new Class[] { ErpOrderReturn.class });
                Unmarshaller unmarshaller = context.createUnmarshaller();
                ep = (ErpOrderReturn)unmarshaller.unmarshal(new StringReader(xml));
            }
        } catch (Exception e) {
            new ErpException("读取订单回传同步配置出错", e);
        }
        return ep;
    }

    public ErpOrderProgressReturn readOrderProgressReturnSet() {
        ErpOrderProgressReturn ep = new ErpOrderProgressReturn();
        try {
            Utils u = new Utils();
            String xml = Utils.readFileByLines(filePath + File.separator + fileOrderProgressReturnName);
            if (xml != null && !xml.equals("")) {
                JAXBContext context = JAXBContext.newInstance(new Class[] { ErpOrderProgressReturn.class });
                Unmarshaller unmarshaller = context.createUnmarshaller();
                ep = (ErpOrderProgressReturn)unmarshaller.unmarshal(new StringReader(xml));
            }
        } catch (Exception e) {
            new ErpException("读取订单进度回传同步配置出错", e);
        }
        return ep;
    }

    public ErpOrderDelayExpressReturn readDelayExpressReturnSet() {
        ErpOrderDelayExpressReturn ep = new ErpOrderDelayExpressReturn();
        try {
            Utils u = new Utils();
            String xml = Utils.readFileByLines(filePath + File.separator + fileDelayExpressReturnName);
            if (xml != null && !xml.equals("")) {
                JAXBContext context = JAXBContext.newInstance(new Class[] { ErpOrderDelayExpressReturn.class });
                Unmarshaller unmarshaller = context.createUnmarshaller();
                ep = (ErpOrderDelayExpressReturn)unmarshaller.unmarshal(new StringReader(xml));
            }
        } catch (Exception e) {
            new ErpException("读取延迟快递单号配置文件出错", e);
        }
        return ep;
    }

    public ErpOrderElectronicInvoice readOrderElectronicInvoiceSet() {
        ErpOrderElectronicInvoice ei = new ErpOrderElectronicInvoice();
        try {
            String xml = Utils.readFileByLines(filePath + File.separator + fileElectronicInvoiceName);
            if (xml != null && !xml.equals("")) {
                JAXBContext context = JAXBContext.newInstance(new Class[] { ErpOrderElectronicInvoice.class });
                Unmarshaller unmarshaller = context.createUnmarshaller();
                ei = (ErpOrderElectronicInvoice)unmarshaller.unmarshal(new StringReader(xml));
            }
        } catch (Exception e) {
            new ErpException("读取延迟快递单号配置文件出错", e);
        }
        return ei;
    }

    public ErpClientViewConfig readErpClientViewConfig() {
        ErpClientViewConfig ep = new ErpClientViewConfig();
        try {
            Utils u = new Utils();
            String xml = Utils.readFileByLines(filePath + File.separator + fileErpClientViewConfigName);
            if (xml != null && !xml.equals("")) {
                JAXBContext context = JAXBContext.newInstance(new Class[] { ErpClientViewConfig.class });
                Unmarshaller unmarshaller = context.createUnmarshaller();
                ep = (ErpClientViewConfig)unmarshaller.unmarshal(new StringReader(xml));
            }
        } catch (Exception e) {
            new ErpException("客户端 tab显示配置 出错", e);
        }
        return ep;
    }

    public ErpCreateOrderMidTable readErpMidTableConfig() {
        ErpCreateOrderMidTable eomt = new ErpCreateOrderMidTable();
        try {
            Utils u = new Utils();
            String xml = Utils.readFileByLines(filePath + File.separator + fileErpCreateOrderMidTable);
            if (xml != null && !xml.equals("")) {
                JAXBContext context = JAXBContext.newInstance(new Class[] { ErpCreateOrderMidTable.class });
                Unmarshaller unmarshaller = context.createUnmarshaller();
                eomt = (ErpCreateOrderMidTable)unmarshaller.unmarshal(new StringReader(xml));
            }
        } catch (Exception e) {
            new ErpException("客户端中间表根据数据库类型自动建表配置出错", e);
        }
        return eomt;
    }

    public ErpOrderCancel readOrderCancelSet() {
        ErpOrderCancel ep = new ErpOrderCancel();
        try {
            Utils u = new Utils();
            String xml = Utils.readFileByLines(filePath + File.separator + fileOrderCancelName);
            if (xml != null && !xml.equals("")) {
                JAXBContext context = JAXBContext.newInstance(new Class[] { ErpOrderCancel.class });
                Unmarshaller unmarshaller = context.createUnmarshaller();
                ep = (ErpOrderCancel)unmarshaller.unmarshal(new StringReader(xml));
            }
        } catch (Exception e) {
            new ErpException("读取订单取消同步配置出错", e);
        }
        return ep;
    }

    public ErpOrderCancelToErp readOrderCancelToErpSet() {
        ErpOrderCancelToErp oce = new ErpOrderCancelToErp();
        try {
            Utils u = new Utils();
            String xml = Utils.readFileByLines(filePath + File.separator + fileOrderCancelToErpName);
            if (xml != null && !xml.equals("")) {
                JAXBContext context = JAXBContext.newInstance(new Class[] { ErpOrderCancelToErp.class });
                Unmarshaller unmarshaller = context.createUnmarshaller();
                oce = (ErpOrderCancelToErp)unmarshaller.unmarshal(new StringReader(xml));
            }
        } catch (Exception e) {
            new ErpException("读取订单取消下发同步配置出错", e);
        }
        return oce;
    }

    public ErpOrderIssued readOrderIssuedSet() {
        ErpOrderIssued ep = new ErpOrderIssued();
        try {
            Utils u = new Utils();
            String xml = Utils.readFileByLines(filePath + File.separator + fileOrderIssuedName);
            if (xml != null && !xml.equals("")) {
                JAXBContext context = JAXBContext.newInstance(new Class[] { ErpOrderIssued.class });
                Unmarshaller unmarshaller = context.createUnmarshaller();
                ep = (ErpOrderIssued)unmarshaller.unmarshal(new StringReader(xml));
            }
        } catch (Exception e) {
            new ErpException("读取商品同步配置出错", e);
        }
        return ep;
    }

    public ErpExceptionOrder readErpExceptionOrderSet() {
        ErpExceptionOrder eo = new ErpExceptionOrder();
        try {
            String xml = Utils.readFileByLines(filePath + File.separator + fileExceptionOrderName);
            if (xml != null && !xml.equals("")) {
                JAXBContext context = JAXBContext.newInstance(new Class[] { ErpExceptionOrder.class });
                Unmarshaller unmarshaller = context.createUnmarshaller();
                eo = (ErpExceptionOrder)unmarshaller.unmarshal(new StringReader(xml));
            }
        } catch (Exception e) {
            new ErpException("读取异常订单配置粗错", e);
        }
        return eo;
    }*/

    public ErpSql readSqlSet() {
        ErpSql es = new ErpSql();
        try {
            Utils u = new Utils();
            String xml = Utils.readFileByLines(filePath + File.separator + fileSqlName);
            log.info("readSqlSet xml is " +filePath + File.separator + fileSqlName);
            log.info(xml + "查看xml文件");
            if (xml != null && !xml.equals("")) {
                JAXBContext context = JAXBContext.newInstance(new Class[] { ErpSql.class });
                Unmarshaller unmarshaller = context.createUnmarshaller();
                es = (ErpSql)unmarshaller.unmarshal(new StringReader(xml));
                System.out.println(es.getYywSecret()+"::::::");
            }
        } catch (Exception e) {
            new ErpException("读取数据库配置出错", e);
        }
        return es;
    }
/*

    public ErpProduct readProductSet() {
        ErpProduct ep = new ErpProduct();
        try {
            Utils u = new Utils();
            String xml = Utils.readFileByLines(filePath + File.separator + fileProductName);
            if (xml != null && !xml.equals("")) {
                JAXBContext context = JAXBContext.newInstance(new Class[] { ErpProduct.class });
                Unmarshaller unmarshaller = context.createUnmarshaller();
                ep = (ErpProduct)unmarshaller.unmarshal(new StringReader(xml));
            }
        } catch (Exception e) {
            new ErpException("读取商品同步配置出错", e);
        }
        return ep;
    }


    public ErpProductValid readProductValidSet() {
        ErpProductValid epv = new ErpProductValid();
        try {
            Utils u = new Utils();
            String xml = Utils.readFileByLines(filePath + File.separator + fileProductValidName);
            if (xml != null && !xml.equals("")) {
                JAXBContext context = JAXBContext.newInstance(new Class[] { ErpProductValid.class });
                Unmarshaller unmarshaller = context.createUnmarshaller();
                epv = (ErpProductValid)unmarshaller.unmarshal(new StringReader(xml));
            }
        } catch (Exception e) {
            new ErpException("读取商品同步配置出错", e);
        }
        return epv;
    }

    public ErpProductStock readProductStockSet() {
        ErpProductStock ep = new ErpProductStock();
        try {
            Utils u = new Utils();
            String xml = Utils.readFileByLines(filePath + File.separator + fileProductAndStockName);
            if (xml != null && !xml.equals("")) {
                JAXBContext context = JAXBContext.newInstance(new Class[] { ErpProductStock.class });
                Unmarshaller unmarshaller = context.createUnmarshaller();
                ep = (ErpProductStock)unmarshaller.unmarshal(new StringReader(xml));
            }
        } catch (Exception e) {
            new ErpException("读取库存同步配置出错", e);
        }
        return ep;
    }

    public ErpProductPrice readProPriSet() {
        ErpProductPrice epp = new ErpProductPrice();
        try {
            Utils u = new Utils();
            String xml = Utils.readFileByLines(filePath + File.separator + fileProductAndPriceName);
            if (xml != null && !xml.equals("")) {
                JAXBContext context = JAXBContext.newInstance(new Class[] { ErpProductPrice.class });
                Unmarshaller unmarshaller = context.createUnmarshaller();
                epp = (ErpProductPrice)unmarshaller.unmarshal(new StringReader(xml));
            }
        } catch (Exception e) {
            new ErpException("读取库存与价格同步配置出错", e);
        }
        return epp;
    }*/

    public ErpCustData readCustDataSet() {
        ErpCustData ecd = new ErpCustData();
        try {
            Utils u = new Utils();
            String xml = Utils.readFileByLines(filePath + File.separator + fileCustDataName);
            log.info("readCustDataSet xml is " +filePath + File.separator + fileCustDataName);
            if (xml != null && !xml.equals("")) {
                JAXBContext context = JAXBContext.newInstance(new Class[] { ErpCustData.class });
                Unmarshaller unmarshaller = context.createUnmarshaller();
                ecd = (ErpCustData)unmarshaller.unmarshal(new StringReader(xml));
                System.out.println(ecd.getYywSecret()+":"+ecd.getFrequency());
            }
        } catch (Exception e) {
            new ErpException("读取客户数据同步配置出错", e);
        }
        return ecd;
    }
/*
    public ErpCustomer readCustomerSet() {
        ErpCustomer ec = new ErpCustomer();
        try {
            Utils u = new Utils();
            String xml = Utils.readFileByLines(filePath + File.separator + fileCustomerName);
            if (xml != null && !xml.equals("")) {
                JAXBContext context = JAXBContext.newInstance(new Class[] { ErpCustomer.class });
                Unmarshaller unmarshaller = context.createUnmarshaller();
                ec = (ErpCustomer)unmarshaller.unmarshal(new StringReader(xml));
            }
        } catch (Exception e) {
            new ErpException("读取erp客户信息同步配置出错", e);
        }
        return ec;
    }*/

    public String readProductLog() {
        String str = "";
        try {
            Utils u = new Utils();
            str = Utils.readFileByNewLines(logFilePath + File.separator + logFile);
        } catch (Exception e) {
            new ErpException("读取日志出错", e);
        }
        return str;
    }

    public void delErpSql() {
        try {
            Utils u = new Utils();
            u.deleteFile(filePath + File.separator + fileSqlName);
        } catch (Exception e) {
            new ErpException("删除数据库配置出错", e);
        }
    }

    public void delErpProduct() {
        try {
            Utils u = new Utils();
            u.deleteFile(filePath + File.separator + fileProductName);
        } catch (Exception e) {
            new ErpException("删除商品同步配置出错", e);
        }
    }

    public void delErpProductPrice() {
        try {
            Utils u = new Utils();
            u.deleteFile(filePath + File.separator + fileProductAndPriceName);
        } catch (Exception e) {
            new ErpException("删除库存与价格同步配置出错", e);
        }
    }

    public void delErpCustData() {
        try {
            Utils u = new Utils();
            u.deleteFile(filePath + File.separator + fileCustDataName);
        } catch (Exception e) {
            new ErpException("删除客户数据同步配置出错", e);
        }
    }

    public void delErpCustomer() {
        try {
            Utils u = new Utils();
            u.deleteFile(filePath + File.separator + fileCustomerName);
        } catch (Exception e) {
            new ErpException("删除erp客户信息同步配置出错", e);
        }
    }

    public void updateErpSql(ErpSql esl) {
        this.es = esl;
        setChanged();
        notifyObservers("erpSql");
        log.info("数据库配置已更新");
    }
/*
    public void updateErpProductPrice(ErpProductPrice eppe) {
        this.epp = eppe;
        setChanged();
        notifyObservers("epp");
        log.info("价格库存同步配置已被修改");
    }

    public void updateErpProductStock(ErpProductStock erpProductStock) {
        this.erpProductStock = erpProductStock;
        setChanged();
        notifyObservers("erpProductStock");
        log.info("商品库存同步配置已被修改");
    }

    public void updateErpProduct(ErpProduct ept) {
        this.ep = ept;
        setChanged();
        notifyObservers("ep");
        log.info("商品同步配置已被修改");
    }

    public void updateErpProductValid(ErpProductValid ept) {
        this.epv = ept;
        setChanged();
        notifyObservers("epv");
        log.info("商品效期同步配置已被修改");
    }

    public void updateOpenLock(boolean flag) {

        if (this.openLock != flag) {
            this.openLock = flag;
            setChanged();
            notifyObservers("openLock");
            log.info("启动数据同步任务");
        } else {
            log.info("数据同步已经开启，如果想重新启动 ，请关闭窗口重新启动");
        }
    }

    public void updateErpOrderIssued(ErpOrderIssued orderIssued) {
        this.erpOrderIssued = orderIssued;
        setChanged();
        notifyObservers("orderIssued");
        log.info("订单下发配置修改了");
    }

    public void updateErpOrderCustomer(ErpOrderCustomerUpdate erpOrderCustomerUpdate) {
        this.erpOrderCustomerUpdate = erpOrderCustomerUpdate;
        setChanged();
        notifyObservers("erpOrderCustomerUpdate");
        log.info("订单下发配置修改了");
    }

    public void updateErpExceptionOrder(ErpExceptionOrder erpExceptionOrder) {
        ErpCommon.erpExceptionOrder = erpExceptionOrder;
        setChanged();
        notifyObservers("exceptionOrder");
        log.info("订单回传配置修改了");
    }

    public void updateErpOrderReturn(ErpOrderReturn orderReturn) {
        this.erpOrderReturn = orderReturn;
        setChanged();
        notifyObservers("orderReturn");
        log.info("订单回传配置修改了");
    }

    public void updateErpOrderProgressReturn(ErpOrderProgressReturn orderProgressReturn) {
        this.erpOrderProgressReturn = orderProgressReturn;
        setChanged();
        notifyObservers("orderProgressReturn");
        log.info("延迟快递单号回传配置修改了");
    }

    public void updateErpDelayExpressReturn(ErpOrderDelayExpressReturn orderReturn) {
        this.erpOrderDelayExpressReturn = orderReturn;
        setChanged();
        notifyObservers("delayExpressReturn");
        log.info("delayExpressReturn修改了");
    }

    public void updateErpOrderElectronicInvoice(ErpOrderElectronicInvoice erpOerderElectronicInvoice) {
        erpOrderElectronicInvoice = erpOerderElectronicInvoice;
        setChanged();
        notifyObservers("erpOrderElectronicInvoice");
        log.info("电子发票回传配置修改了");
    }

    public void updateErpOrderCancel(ErpOrderCancel orderCancel) {
        this.erpOrderCancel = orderCancel;
        setChanged();
        notifyObservers("orderCancel");
        log.info("订单取消配置修改了");
    }

    public void updateErpOrderCancelToErp(ErpOrderCancelToErp erpOrderCancelToErp) {
        this.erpOrderCancelToErp = erpOrderCancelToErp;
        setChanged();
        notifyObservers("orderCancelToErp");
        log.info("订单取消配置修改了");
    }

    public void updateErpCustData(ErpCustData ecd) {
        this.erpCustData = ecd;
        setChanged();
        notifyObservers("ecd");
        log.info("客户数据同步配置已被修改");
    }

    public void updateErpCustomerData(ErpCustomer ec) {
        this.erpCustomer = ec;
        setChanged();
        notifyObservers("ec");
        log.info("ERP客户信息同步配置已被修改");
    }

    public void updateErpProductPlaceNo(ErpProductPlaceNo ep) {
        erpProductPlaceNo = ep;
        setChanged();
        notifyObservers("placeNo");
        log.info("ERP药品货位号同步配置已被修改");
    }*/

    public static void main(String[] args) {
        ErpCommon.getInstance();
    }
}
