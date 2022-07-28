package com.ang.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.TriggerKey;

import java.util.Date;

/**
 * @author: 于昂
 * @date: 2022/7/28
 **/
public class SimpleJob implements Job {
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        TriggerKey name = jec.getTrigger()
                            .getKey();
        System.out.printf("Trigger name: %s time is %s %n", name, new Date());
    }
}
