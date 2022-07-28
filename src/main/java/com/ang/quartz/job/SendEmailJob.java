package com.ang.quartz.job;

import com.ang.quartz.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author: 于昂
 * @date: 2022/7/28
 **/
@Slf4j
@Component
public class SendEmailJob extends QuartzJobBean {
    private ScheduleService scheduleService;

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        Trigger trigger = context.getTrigger();
        JobDetail jobDetail = context.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        String data = (String) jobDataMap.get("data");
        log.info("定时发送邮件成功! {}", data);
        boolean dontCancel = (boolean) jobDataMap.getOrDefault("dontCancel",false);
        if (!dontCancel) {
            scheduleService.cancelScheduleJob(trigger.getKey()
                                                     .getName());
        }
    }
}
