package com.wh.service;


import erp.ErpBaseModel;

public interface JobService {
    boolean validate(ErpBaseModel paramErpBaseModel, String paramString);

    void process(ErpBaseModel paramErpBaseModel);
}
