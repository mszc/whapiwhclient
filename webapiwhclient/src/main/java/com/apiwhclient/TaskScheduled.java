package com.apiwhclient;

import java.util.Date;

import com.wh.service.JobService;
import com.wh.util.DateUtil;
import com.wh.util.ErpCommon;
import erp.ErpBaseModel;
import erp.ErpCustData;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TaskScheduled {
    private static Logger logger = Logger.getLogger(TaskScheduled.class);

    private String name = "aaa";

    public String getName() {
        return name;
    }

    @Resource(name = "custDataService")
    private JobService custDataService;

    @Scheduled(cron="0 */1 * * * ? ")
    public void insertSale() {
        logger.error("-------1、开始insertSale执行触发,"+ DateUtil.currentDate2String(DateUtil.DATE_SPLIT_FORMAT));
        //ErpCommon.getInstance();      //这里使用ErpCommon的时候 类被加载，类加载时自动调用静态代码块,并且只调用一次。
        ErpCustData ep = ErpCommon.erpCustData;
        long startTime = System.currentTimeMillis();
        custDataService.process((ErpBaseModel)ep);
        long useTime = System.currentTimeMillis() - startTime;
        logger.error("-------1、结束insertSale执行触发,执行时长"+useTime+"ms-------"+ DateUtil.currentDate2String(DateUtil.DATE_SPLIT_FORMAT));
    }

 /*   @Scheduled(cron="0/5 * * * * ? ")
    public void updateStore() {
        logger.error("-------2、updateStore执行触发-------"+new Date());
    }*/
}
