package com.wh.common;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "ErpCreateOrderMidTable")
public class ErpCreateOrderMidTable implements Serializable {
    private static final long serialVersionUID = 1L;

    private String createResult;

    private String sqlType;

    private String orderSql;

    private String orderDetailSql;

    private String orderCustomerSql;

    public static long getSerialVersionUID() {
        return 1L;
    }

    public String getOrderCustomerSql() {
        return this.orderCustomerSql;
    }

    public void setOrderCustomerSql(String orderCustomerSql) {
        this.orderCustomerSql = orderCustomerSql;
    }

    public String getCreateResult() {
        return this.createResult;
    }

    public void setCreateResult(String createResult) {
        this.createResult = createResult;
    }

    public String getSqlType() {
        return this.sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    public String getOrderSql() {
        return this.orderSql;
    }

    public void setOrderSql(String orderSql) {
        this.orderSql = orderSql;
    }

    public String getOrderDetailSql() {
        return this.orderDetailSql;
    }

    public void setOrderDetailSql(String orderDetailSql) {
        this.orderDetailSql = orderDetailSql;
    }
}
