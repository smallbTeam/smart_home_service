/**
 * Company
 * Copyright (C) 2004-2017 All Rights Reserved.
 */
package com.atat.soft.common.scheduled;

import com.atat.core.config.SpringBeanFactoryUtils;
import com.atat.soft.freshair.service.DataFreshairDayService;
import com.atat.soft.freshair.service.DataFreshairHourService;
import com.atat.soft.freshair.service.DataFreshairNowService;
import com.atat.soft.message.action.WeixinAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author ligw
 * @version $Id SpecScheduled.java, v 0.1 2017-08-10 13:31 ligw Exp $$
 */
@Component
public class SpecScheduled {

    private static final Logger logger = LoggerFactory.getLogger(SpecScheduled.class);

    // 间隔3小时执行任务
    @Scheduled(cron = "0 5 0/3 * * ?")
//    @Scheduled(cron = "0 0/1 * * * ?")
    public static void threeHoursEachScheduled() {
         Thread current = Thread.currentThread();
         System.out.println("间隔3小时定时任务:" + current.getId());
         logger.info("SpecScheduled.threeHoursEachScheduled 定时任务:" +
         current.getId() + ",name:" + current.getName());
        DataFreshairNowService dataFreshairNowService = (DataFreshairNowService) SpringBeanFactoryUtils.getBean("dataFreshairNowService");
        dataFreshairNowService.timingFormateForThreeHour();
    }

    // 每天1点执行任务
    @Scheduled(cron = "0 0 1 * * ?")
//    @Scheduled(cron = "0 0/2 * * * ?")
    public static void oneDayEachScheduled() {
         Thread current = Thread.currentThread();
         System.out.println("每天1点定时任务:" + current.getId());
         logger.info("SpecScheduled.oneDayEachScheduled 定时任务:" + current.getId()
         + ",name:" + current.getName());
        DataFreshairHourService dataFreshairHourService = (DataFreshairHourService) SpringBeanFactoryUtils.getBean("dataFreshairHourService");
        dataFreshairHourService.timingFormateForOneDay();
    }

    // 每周凌晨半点执行任务
    @Scheduled(cron = "0 30 0 ? * SAT")
//    @Scheduled(cron = "0 0/3 * * * ?")
    public static void oneWeekEachScheduled() {
         Thread current = Thread.currentThread();
         System.out.println("每周凌晨半点定时任务:" + current.getId());
         logger.info("SpecScheduled.oneWeekEachScheduled 定时任务:" +
         current.getId() + ",name:" + current.getName());
        DataFreshairDayService dataFreshairDayService = (DataFreshairDayService) SpringBeanFactoryUtils.getBean("dataFreshairDayService");
        dataFreshairDayService.timingFormateForOneWeek();
    }
    // 每天早八点到晚八点，间隔2分钟执行任务
    // @Scheduled(cron="0 0/1 8-20 * * ?")

    // 间隔1小时执行任务
    @Scheduled(cron = "0 0 0/1 * * ?")
    public static void oneHourEachScheduled() {
        Thread current = Thread.currentThread();
        System.out.println("每1小时定时任务:" + current.getId());
        logger.info("SpecScheduled.oneHourEachScheduled 定时任务:" +
                current.getId() + ",name:" + current.getName());
        //更新accessToken
        WeixinAction.refreshWxaccessToken();
    }

}
