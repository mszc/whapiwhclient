package com.wh.schedule;

import com.wh.service.JobService;
import com.wh.util.ErpCommon;
import erp.ErpBaseModel;
import erp.ErpCustData;
import org.apache.log4j.Logger;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustDataTask extends AbstractTask {
    private static Logger logger = Logger.getLogger(CustDataTask.class);

    private AtomicBoolean isBegin = new AtomicBoolean(false);

    @Resource(name = "custDataService")
    private JobService custDataService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ErpCommon.getInstance();
        final ErpCustData ep = ErpCommon.erpCustData;
        taskRegistrar.addTriggerTask(new Runnable() {
            public void run() {
                if (!CustDataTask.this.isBegin.getAndSet(true))
                    try {
                        CustDataTask.this.custDataService.process((ErpBaseModel)ep);
                    } catch (Exception e) {
                        CustDataTask.logger.error("客户数据同步的定时任务出错", e);
                    } finally {
                        CustDataTask.this.isBegin.getAndSet(false);
                    }
            }
        },new Trigger() {
            public Date nextExecutionTime(TriggerContext triggerContext) {
                return CustDataTask.this.getDate(triggerContext, (ErpBaseModel)ep);
            }
        });
    }
}
