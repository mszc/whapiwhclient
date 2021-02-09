package erp.common;

import java.io.Serializable;

public class SystemParameter implements Serializable {

    private String validateCode;
    private String dataType;
    private String version;
    private String interfaceName;
    private String requestTime;
    private String supplyName;
    private String parmeter;
    private Boolean syncAll;

    public SystemParameter() {
    }

    public String getParmeter() {
        return this.parmeter;
    }

    public void setParmeter(String parmeter) {
        this.parmeter = parmeter;
    }

    public String getSupplyName() {
        return this.supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }

    public String getValidateCode() {
        return this.validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getInterfaceName() {
        return this.interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getRequestTime() {
        return this.requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public Boolean getSyncAll() {
        return this.syncAll;
    }

    public void setSyncAll(Boolean syncAll) {
        this.syncAll = syncAll;
    }
}
