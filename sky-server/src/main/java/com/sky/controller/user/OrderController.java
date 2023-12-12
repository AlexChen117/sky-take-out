package com.sky.controller.user;

import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderSubmitVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单管理
 *
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/12 09:21:17
 */
@RestController
@Slf4j
@RequestMapping("/user/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/submit")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO submitDTO) {
        log.info("提交订单");
        OrderSubmitVO orderSubmitVO = orderService.submit(submitDTO);
        return Result.success(orderSubmitVO);
    }
}
