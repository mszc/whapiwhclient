package erp.common;

import java.io.Serializable;
import java.util.List;

public class CommonRequestVo<T> implements Serializable {

    private List<T> objectData;
    private int dataCount;

    public CommonRequestVo() {
    }

    public List<T> getObjectData() {
        return this.objectData;
    }

    public void setObjectData(List<T> objectData) {
        this.objectData = objectData;
    }

    public int getDataCount() {
        return this.dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }
}
