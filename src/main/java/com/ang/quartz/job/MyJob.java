package com.ang.quartz.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

/**
 * @author: 于昂
 * @date: 2022/7/28
 **/
public class MyJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail()
                                    .getJobDataMap();

        int size = (int) dataMap.get("size");


        ApplicationContext ctx = (ApplicationContext) dataMap.get("applicationContext");

        System.out.println("size:" + size);

        dataMap.put("size", ++size);

    }
}
