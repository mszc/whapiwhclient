package erp;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(
        name = "ErpCustDataTableInfo"
)
public class ErpCustDataTableInfo implements Serializable {
    private String funcDes;
    private String tableName;
    private String sqlContext;

    public ErpCustDataTableInfo() {
    }

    public String getFuncDes() {
        return this.funcDes;
    }

    public void setFuncDes(String funcDes) {
        this.funcDes = funcDes;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSqlContext() {
        return this.sqlContext;
    }

    public void setSqlContext(String sqlContext) {
        this.sqlContext = sqlContext;
    }
}
