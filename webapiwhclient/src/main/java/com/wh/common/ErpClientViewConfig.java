package com.wh.common;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name="ErpClientViewConfig")
public class ErpClientViewConfig implements Serializable {
    private static final long serialVersionUID = 4425569654029278082L;

    private String productPane;

    private String productValidPane;

    private String productPricePane;

    private String productStockPane;

    private String customerPane;

    private String orderIssuedPanel;

    private String orderReturnPanel;

    private String orderCancelPanel;

    private String productPlaceNo;

    private String custDataPane;

    private String exceptionOrderPanel;

    private String orderCustomerPanel;

    private String orderCancelToErpPanel;

    public String getProductPane() {
        return this.productPane;
    }

    public void setProductPane(String productPane) {
        this.productPane = productPane;
    }

    public String getProductPricePane() {
        return this.productPricePane;
    }

    public void setProductPricePane(String productPricePane) {
        this.productPricePane = productPricePane;
    }

    public String getProductStockPane() {
        return this.productStockPane;
    }

    public void setProductStockPane(String productStockPane) {
        this.productStockPane = productStockPane;
    }

    public String getCustomerPane() {
        return this.customerPane;
    }

    public void setCustomerPane(String customerPane) {
        this.customerPane = customerPane;
    }

    public String getOrderIssuedPanel() {
        return this.orderIssuedPanel;
    }

    public void setOrderIssuedPanel(String orderIssuedPanel) {
        this.orderIssuedPanel = orderIssuedPanel;
    }

    public String getOrderReturnPanel() {
        return this.orderReturnPanel;
    }

    public void setOrderReturnPanel(String orderReturnPanel) {
        this.orderReturnPanel = orderReturnPanel;
    }

    public String getOrderCancelPanel() {
        return this.orderCancelPanel;
    }

    public void setOrderCancelPanel(String orderCancelPanel) {
        this.orderCancelPanel = orderCancelPanel;
    }

    public String getProductPlaceNo() {
        return this.productPlaceNo;
    }

    public void setProductPlaceNo(String productPlaceNo) {
        this.productPlaceNo = productPlaceNo;
    }

    public String getCustDataPane() {
        return this.custDataPane;
    }

    public void setCustDataPane(String custDataPane) {
        this.custDataPane = custDataPane;
    }

    public String getExceptionOrderPanel() {
        return this.exceptionOrderPanel;
    }

    public void setExceptionOrderPanel(String orderExceptionPanel) {
        this.exceptionOrderPanel = orderExceptionPanel;
    }

    public String getProductValidPane() {
        return this.productValidPane;
    }

    public void setProductValidPane(String productValidPane) {
        this.productValidPane = productValidPane;
    }

    public String getOrderCustomerPanel() {
        return this.orderCustomerPanel;
    }

    public void setOrderCustomerPanel(String orderCustomerPanel) {
        this.orderCustomerPanel = orderCustomerPanel;
    }

    public String getOrderCancelToErpPanel() {
        return this.orderCancelToErpPanel;
    }

    public void setOrderCancelToErpPanel(String orderCancelToErpPanel) {
        this.orderCancelToErpPanel = orderCancelToErpPanel;
    }

    public String toString() {
        return "ErpClientViewConfig [productPane=" + this.productPane + ", productPricePane=" + this.productPricePane + ", productStockPane=" + this.productStockPane + ", customerPane=" + this.customerPane + ", orderIssuedPanel=" + this.orderIssuedPanel + ", orderReturnPanel=" + this.orderReturnPanel + ", orderCancelPanel=" + this.orderCancelPanel + ", productPlaceNo=" + this.productPlaceNo + ", custDataPane=" + this.custDataPane + "]";
    }

}
