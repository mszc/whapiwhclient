package com.wh.schedule;

import erp.ErpBaseModel;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.support.PeriodicTrigger;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public abstract class AbstractTask implements SchedulingConfigurer {
    protected static long cron = 10L;

    protected Date getDate(TriggerContext triggerContext, ErpBaseModel model) {
        long frequency = cron;
        if (model.getFrequency() >=5.0D)
            frequency = (long)model.getFrequency();  //if不加 {} 时，作用域是第一条语句，这个第一条指的是if后面第一个 ; 结尾的语句。 并且这第一条语句不能用来做变量申明。
        PeriodicTrigger periodicTrigger = new PeriodicTrigger(frequency, TimeUnit.HOURS); //MINUTES
        Date nextExec = periodicTrigger.nextExecutionTime(triggerContext);
        System.out.println(nextExec);

        return nextExec;
    }
}
