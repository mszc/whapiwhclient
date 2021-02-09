package erp.common;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Map;

@XmlRootElement(
        name = "ErpTaskLockDto"
)
public class ErpTaskLockDto implements Serializable {
    private static final long serialVersionUID = 5768998020969781533L;
    private Integer supplyId;
    private String taskCode;
    private Integer status;
    private Map<String, String> sign_map;

    public ErpTaskLockDto() {
    }

    public Integer getSupplyId() {
        return this.supplyId;
    }

    public void setSupplyId(Integer supplyId) {
        this.supplyId = supplyId;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode == null ? null : taskCode.trim();
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Map<String, String> getSign_map() {
        return this.sign_map;
    }

    public void setSign_map(Map<String, String> sign_map) {
        this.sign_map = sign_map;
    }
}
