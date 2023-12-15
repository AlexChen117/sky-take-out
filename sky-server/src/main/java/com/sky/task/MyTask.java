package com.sky.task;

import com.sky.config.WebSocketServer;
import lombok.RequiredArgsConstructor;
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
//@RequiredArgsConstructor
public class MyTask {
    private  WebSocketServer socketServer;
    @Scheduled(cron = "* * * * * ?")
    public void taskTest() {
        socketServer.sendToAllClient("陈俊杰是什么");
        socketServer.sendToAllClient("陈俊杰是sb");
    }
}
