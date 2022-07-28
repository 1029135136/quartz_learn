package com.ang.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * @author: 于昂
 * @date: 2022/7/28
 **/
@Slf4j
@Component
public class CronProcessJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();

        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        String  data = (String) jobDataMap.get("data");
        log.info("CRON 任务执行{}", data);
    }
}
