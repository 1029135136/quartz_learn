package com.ang.quartz.job;

import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;

import java.text.ParseException;

/**
 * @author: 于昂
 * @date: 2022/7/28
 **/
public class CronTriggerRunning {
    public static void main(String[] args) throws SchedulerException, ParseException {
        JobDetailImpl jd = new JobDetailImpl("job1", "jgroup1", SimpleJob.class);
        CronTrigger trigger = new CronTriggerImpl("trigger1", "tgroup1", "0/2 * * * * ?");

        StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.scheduleJob(jd, trigger);

        scheduler.start();
    }
}
