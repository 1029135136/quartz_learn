package com.ang.quartz.service;

import org.quartz.Job;

import java.util.Date;

/**
 * @author: 于昂
 * @date: 2022/7/28
 **/
public interface ScheduleService {
    /**
     * 通过CRON 表达式调度任务
     */
    String scheduleJob(Class<? extends Job> jobBeanClass, String cron, String data);

    /**
     * 调度指定时间的任务
     */
    String scheduleFixTimeJob(Class<? extends Job> jobBeanClass, Date startTime, String data);

    /**
     * 调度任务指定执行次数
     */
    String scheduleJob(Class<? extends Job> jobBeanClass, int count, String data);

    /**
     * 调度指定时间的任务
     */
    String scheduleJob(Class<? extends Job> jobBeanClass, int count, String data, Date date);

    /**
     * 取消任务
     *
     * @return
     */
    boolean cancelScheduleJob(String jobName);
}
