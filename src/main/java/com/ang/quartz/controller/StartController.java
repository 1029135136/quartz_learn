package com.ang.quartz.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.ang.quartz.job.CronProcessJob;
import com.ang.quartz.job.SendEmailJob;
import com.ang.quartz.job.SendMessageJob;
import com.ang.quartz.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author: 于昂
 * @date: 2022/7/28
 **/
@RestController
@RequestMapping("schedule")
public class StartController {
    private ScheduleService scheduleService;

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @RequestMapping(value = "sendMail", method = RequestMethod.GET)
    public String sendMail(@RequestParam("data") String data, @RequestParam("startDate") String startDate) {
        DateTime dateTime = DateUtil.parse(startDate, "yyyy-MM-dd HH:mm:ss");

        return scheduleService.scheduleFixTimeJob(SendEmailJob.class, dateTime, data);
    }

    @RequestMapping(value = "sendMessage", method = RequestMethod.GET)
    public String sendMessage(@RequestParam("data") String data, @RequestParam("startDate") String startDate) {
        DateTime dateTime = DateUtil.parse(startDate, "yyyy-MM-dd HH:mm:ss");
        return scheduleService.scheduleFixTimeJob(SendMessageJob.class, dateTime, data);
    }

    @RequestMapping(value = "cronProcess", method = RequestMethod.GET)
    public String cronProcess(@RequestParam("data") String data, @RequestParam("cron") String cron) {
        return scheduleService.scheduleJob(CronProcessJob.class, cron, data);
    }

    @RequestMapping(value = "sendMailCount", method = RequestMethod.GET)
    public String sendMailCount(@RequestParam("data") String data, @RequestParam("count") int count) {
        return scheduleService.scheduleJob(SendEmailJob.class, count, data);
    }

    @RequestMapping(value = "sendMailCountDate", method = RequestMethod.GET)
    public String sendMailCountDate(@RequestParam("data") String data, @RequestParam("count") int count, @RequestParam("startDate") String startDate) {
        DateTime dateTime = DateUtil.parse(startDate, "yyyy-MM-dd HH:mm:ss");
        return scheduleService.scheduleJob(SendEmailJob.class, count, data, dateTime);
    }

    @RequestMapping(value = "cancelJob", method = RequestMethod.GET)
    public boolean cancelJob(@RequestParam("jobName") String jobName) {
        return scheduleService.cancelScheduleJob(jobName);
    }
}
