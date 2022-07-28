package com.ang.quartz.job;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.AnnualCalendar;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author: 于昂
 * @date: 2022/7/28
 **/
public class CalenderExample {
    public static void main(String[] args) throws SchedulerException {
        StdSchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = sf.getScheduler();

        AnnualCalendar holidays = new AnnualCalendar();

        GregorianCalendar laborDay = new GregorianCalendar();
        laborDay.add(Calendar.MONTH, 5);
        laborDay.add(Calendar.DAY_OF_MONTH, 1);

        GregorianCalendar nationalDay = new GregorianCalendar();
        nationalDay.add(Calendar.MONTH, 10);
        nationalDay.add(Calendar.DAY_OF_MONTH, 1);

        ArrayList<Calendar> list = new ArrayList<>();
        list.add(laborDay);
        list.add(nationalDay);
        holidays.setDaysExcluded(list);


        scheduler.addCalendar("holidays", holidays, false, false);

//        TriggerUtils

    }
}
