package com.sky.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 我的任务
 *
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/15 09:24:46
 */

//@Component
@Slf4j
public class MyTask {
    @Scheduled(cron = "* * * * * ?")
    public void taskTest() {
        log.info("陈俊杰是沙雕");
    }
}
