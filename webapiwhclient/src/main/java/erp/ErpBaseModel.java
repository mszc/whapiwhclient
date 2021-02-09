package erp;

import java.io.Serializable;

public class ErpBaseModel implements Serializable {
    private String startTime;
    private String startMin;
    private String endTime;
    private String endMin;
    private String openDock;
    private String pharmacyName;
    private String yywSecret;
    private String sqlContext;
    private String sqlContextQL;
    private String isDoneQL;
    private double frequency;

    public ErpBaseModel() {
    }

    public double getFrequency() {
        return this.frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartMin() {
        return this.startMin;
    }

    public void setStartMin(String startMin) {
        this.startMin = startMin;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndMin() {
        return this.endMin;
    }

    public void setEndMin(String endMin) {
        this.endMin = endMin;
    }

    public String getOpenDock() {
        return this.openDock;
    }

    public void setOpenDock(String openDock) {
        this.openDock = openDock;
    }

    public String getPharmacyName() {
        return this.pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getYywSecret() {
        return this.yywSecret;
    }

    public void setYywSecret(String yywSecret) {
        this.yywSecret = yywSecret;
    }

    public String getSqlContext() {
        return this.sqlContext;
    }

    public void setSqlContext(String sqlContext) {
        this.sqlContext = sqlContext;
    }

    public String getSqlContextQL() {
        return this.sqlContextQL;
    }

    public void setSqlContextQL(String sqlContextQL) {
        this.sqlContextQL = sqlContextQL;
    }

    public String getIsDoneQL() {
        return this.isDoneQL;
    }

    public void setIsDoneQL(String isDoneQL) {
        this.isDoneQL = isDoneQL;
    }
}
