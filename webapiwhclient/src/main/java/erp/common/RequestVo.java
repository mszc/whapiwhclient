package erp.common;

import java.io.Serializable;
import java.util.List;

public class RequestVo<T> implements Serializable {

    private SystemParameter systemParameter;
    private CommonRequestVo<T> commonData;
    private List<String> flowIds;
    private List<String> failureOrderList;
    private List<String> successOrderList;

    public RequestVo() {
    }

    public List<String> getFailureOrderList() {
        return this.failureOrderList;
    }

    public void setFailureOrderList(List<String> failureOrderList) {
        this.failureOrderList = failureOrderList;
    }

    public List<String> getSuccessOrderList() {
        return this.successOrderList;
    }

    public void setSuccessOrderList(List<String> successOrderList) {
        this.successOrderList = successOrderList;
    }

    public List<String> getFlowIds() {
        return this.flowIds;
    }

    public void setFlowIds(List<String> flowIds) {
        this.flowIds = flowIds;
    }

    public SystemParameter getSystemParameter() {
        return this.systemParameter;
    }

    public void setSystemParameter(SystemParameter systemParameter) {
        this.systemParameter = systemParameter;
    }

    public CommonRequestVo<T> getCommonData() {
        return this.commonData;
    }

    public void setCommonData(CommonRequestVo<T> commonData) {
        this.commonData = commonData;
    }
}
