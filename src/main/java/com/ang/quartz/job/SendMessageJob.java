package com.ang.quartz.job;

import com.ang.quartz.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: 于昂
 * @date: 2022/7/28
 **/
@Slf4j
@Component
public class SendMessageJob extends QuartzJobBean {
    private ScheduleService scheduleService;

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        Trigger trigger = context.getTrigger();

        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        String data = (String) jobDataMap.get("data");
        log.info("发送站内信成功!{}", data);

        scheduleService.cancelScheduleJob(trigger.getKey()
                                                 .getName());
    }
}
