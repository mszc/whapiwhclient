package com;


import com.wh.service.JobService;
import com.wh.service.impl.CustDataServiceImpl;
import com.wh.util.ErpCommon;
import erp.ErpBaseModel;
import erp.ErpCustData;
import org.apache.log4j.Logger;
import org.junit.Test;
import java.util.Arrays;


public class MyTest {

    private static Logger logger = Logger.getLogger(MyTest.class);
    @Test
    public void test01() {
        JobService custDataService = new CustDataServiceImpl();
        ErpCustData ep = ErpCommon.erpCustData;
        custDataService.process((ErpBaseModel)ep);
    }

    @Test
    public void test02() {
        int scores[] = new int[]{1,2,3,89,4};
        Arrays.sort(scores);
        for (int i:scores
        ) {
            System.out.println(i);
        }
    }

    @Test
    public void test03() {
        logger.info("-------info-----");
        logger.debug("-----debug------");
        logger.error("-----error-----");


    }
}
