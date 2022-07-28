package com.ang.quartz.conf;

import com.ang.quartz.job.MyJob;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.quartz.*;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author: 于昂
 * @date: 2022/7/28
 **/
//@Configuration
public class QuartzConfig {

    @Bean("jobDetail")
    public JobDetailFactoryBean buildJobDetail() {
        JobDetailFactoryBean bean = new JobDetailFactoryBean();
        bean.setJobClass(MyJob.class);
        bean.setApplicationContextJobDataKey("applicationContext");

        HashMap<String, Object> map = new HashMap<>(1);
        map.put("key", 10);
        bean.setJobDataAsMap(map);
        return bean;
    }

    @Bean("jobDetail_1")
    public MethodInvokingJobDetailFactoryBean buildMethodInvokingJobDetailFactoryBean() {
        MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean();
        bean.setTargetBeanName("myService");
        bean.setTargetMethod("doJob");
        bean.setConcurrent(false);
        return bean;
    }

    @Bean("simpleTrigger")
    public SimpleTriggerFactoryBean buildSimpleTriggerFactoryBean() {
        SimpleTriggerFactoryBean bean = new SimpleTriggerFactoryBean();
        bean.setJobDetail(Objects.requireNonNull(buildJobDetail().getObject()));
        bean.setStartDelay(1000);
        bean.setRepeatInterval(2000);
        bean.setRepeatCount(100);
        HashMap<String, Object> map = new HashMap<>();
        map.put("count", 10);
        bean.setJobDataAsMap(map);
        return bean;
    }

    @Bean("cronTrigger")
    public CronTriggerFactoryBean buildCronTriggerFactoryBean() {
        CronTriggerFactoryBean bean = new CronTriggerFactoryBean();
        bean.setJobDetail(Objects.requireNonNull(buildJobDetail().getObject()));
        bean.setCronExpression("0/5 * * * * ?");
        return bean;
    }

    @Bean("schedulerFactory")
    public SchedulerFactoryBean buildSchedulerFactoryBean() {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        bean.setTriggers(buildSimpleTriggerFactoryBean().getObject());

        HashMap<String, Object> map = new HashMap<>(1);
        map.put("timeout", 30);
        bean.setSchedulerContextAsMap(map);
        return bean;
    }

//    @Bean("scheduler")
//    public SchedulerFactoryBean buildScheduler() {
//        buildSchedulerFactoryBean().getObject();
//    }
}
