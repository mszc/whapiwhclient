package erp;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(
        name = "ErpCustData"
)
public class ErpCustData extends ErpBaseModel {
    private List<ErpCustDataTableInfo> tableInfos;

    public ErpCustData() {
    }

    @XmlElementWrapper(
            name = "tableInfos"
    )
    @XmlElement(
            name = "tableInfo"
    )
    public List<ErpCustDataTableInfo> getTableInfos() {
        return this.tableInfos;
    }

    public void setTableInfos(List<ErpCustDataTableInfo> tableInfos) {
        this.tableInfos = tableInfos;
    }
}
