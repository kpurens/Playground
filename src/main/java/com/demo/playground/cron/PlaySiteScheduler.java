package com.demo.playground.cron;

import com.demo.playground.service.PlaySiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class PlaySiteScheduler {

    @Autowired
    private PlaySiteService playSiteService;

    @Scheduled(cron = "0 0 0 * * ?") // Every day at midnight
    public void resetVisitorCountTask() {
        playSiteService.resetVisitorCount();
    }
}