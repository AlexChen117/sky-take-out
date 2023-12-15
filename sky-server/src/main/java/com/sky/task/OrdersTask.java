package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.AdminOrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单自动取消任务
 *
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/15 09:54:37
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class OrdersTask {
    private final AdminOrderMapper adminOrderMapper;

    @Scheduled(cron = "0 * * * * ?")
    public void autoCancelOutTimeOrder() {
        log.info("----------自动取消订单:开始----------");
        //查询超时订单
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime time = now.plusMinutes(-15L);
        List<Orders> list = adminOrderMapper.findTimeOutOrder(time);
        log.info("----------超时的订单有:{}----------", list.size());
        if (!list.isEmpty()) {
            for (Orders orders : list) {
                orders.setCancelTime(LocalDateTime.now());
                orders.setCancelReason("订单超时");
                orders.setStatus(Orders.CANCELLED);
                adminOrderMapper.update(orders);
            }
        }
        //adminOrderMapper.updateOutTimeOrder();
        log.info("----------自动取消订单:结束----------");
    }
}
