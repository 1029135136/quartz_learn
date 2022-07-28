package com.ang.quartz.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.ang.quartz.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author: 于昂
 * @date: 2022/7/28
 **/
@Service
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {
    private Scheduler scheduler;

    @Autowired
    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public static final String DEFAULT_GROUP = "default_group";

    @Override
    public String scheduleJob(Class<? extends Job> jobBeanClass, String cron, String data) {
        String jobName = UUID.fastUUID()
                             .toString();

        JobDetail jobDetail = JobBuilder.newJob(jobBeanClass)
                                        .withIdentity(jobName, DEFAULT_GROUP)
                                        .usingJobData("data", data)
                                        .build();

        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                                                .withIdentity(jobName, DEFAULT_GROUP)
                                                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                                                .build();

        try {
            scheduler.scheduleJob(jobDetail, cronTrigger);
            log.info("创建定时任务成功!");
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.error("创建定时任务失败!");
        }
        return jobName;
    }

    @Override
    public String scheduleFixTimeJob(Class<? extends Job> jobBeanClass, Date startTime, String data) {
        String cron = String.format("%d %d %d %d %d ? %d", DateUtil.second(startTime), DateUtil.minute(startTime), DateUtil.hour(startTime, true), DateUtil.dayOfMonth(startTime), DateUtil.month(startTime) + 1, DateUtil.year(startTime));
        return scheduleJob(jobBeanClass, cron, data);
    }

    @Override
    public String scheduleJob(Class<? extends Job> jobBeanClass, int count, String data) {
        return this.scheduleJob(jobBeanClass, count, data, new Date());
    }

    @Override
    public String scheduleJob(Class<? extends Job> jobBeanClass, int count, String data, Date date) {
        String jobName = UUID.fastUUID()
                             .toString();

        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                                              .withIdentity(jobName, DEFAULT_GROUP)
                                              .withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(10, 1))
                                              .startAt(date)
                                              .build();

        JobDetail jobDetail = JobBuilder.newJob(jobBeanClass)
                                        .usingJobData("data", data)
                                        .usingJobData("dontCancel", true)
                                        .withIdentity(jobName, DEFAULT_GROUP)
                                        .build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
            log.info("创建简单重复任务成功!");
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.error("创建简单重复任务失败!");
        }
        return jobName;
    }

    @Override
    public boolean cancelScheduleJob(String jobName) {
        boolean success = false;
        try {
            scheduler.pauseTrigger(new TriggerKey(jobName, DEFAULT_GROUP));
            scheduler.unscheduleJob(new TriggerKey(jobName, DEFAULT_GROUP));

            scheduler.deleteJob(new JobKey(jobName, DEFAULT_GROUP));
            success = true;

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return success;
    }
}
