package com.ang.quartz.job;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.SimpleTriggerImpl;

import java.util.Date;

/**
 * 启动一个最简答的定时任务
 *
 * @author: 于昂
 * @date: 2022/7/28
 **/
public class SimpleTriggerRunning {
    public static void main(String[] args) throws SchedulerException {
        //jobDetail
        JobDetailImpl jobDetail = new JobDetailImpl("job1", "jgroup1", SimpleJob.class);
        //trigger
        SimpleTriggerImpl trigger = new SimpleTriggerImpl("trigger1", "tgroupp1");
        //设置执行策略
        trigger.setStartTime(new Date());
        trigger.setRepeatInterval(2000);
        trigger.setRepeatCount(100);

        //创建调度器
        StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        //将任务和触发器注册到执行器
        scheduler.scheduleJob(jobDetail, trigger);
        //开始调度
        scheduler.start();
    }
}
