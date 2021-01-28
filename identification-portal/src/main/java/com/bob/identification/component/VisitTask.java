package com.bob.identification.component;

import com.bob.identification.service.IdentificationVisitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 访问管理定时器
 * Created by LittleBob on 2021/1/4/004.
 */
@Configuration
@EnableScheduling
public class VisitTask {

    @Autowired
    private IdentificationVisitService visitService;

    private static final Logger LOGGER = LoggerFactory.getLogger(VisitTask.class);

    /**
     * 每天凌晨0点1分
     */
    //@Scheduled(cron = "0 1 0 * * ?")
    @Scheduled(cron = "0 10 15 * * ?")
    private void addDayVisitRecord() {
        LOGGER.info("添加每天访问记录");
        visitService.addDayVisitRecord();
        visitService.deleteYesterdayVisit();
    }
}
