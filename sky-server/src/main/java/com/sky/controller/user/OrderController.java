package com.sky.controller.user;

import com.sky.dto.OrdersDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrdersVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 提交订单
     *
     * @param submitDTO
     * @return
     */
    @PostMapping("/submit")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO submitDTO) {
        log.info("提交订单");
        OrderSubmitVO orderSubmitVO = orderService.submit(submitDTO);
        return Result.success(orderSubmitVO);
    }

    /**
     * 支付成功
     *
     * @param number
     * @return
     */
    @PutMapping("/paySuccess/{number}")
    public Result<?> paySuccess(@PathVariable String number) {
        log.info("支付成功");
        orderService.paySuccess(number);
        return Result.success();
    }

    /**
     * 历史订单查询
     *
     * @return
     */
    @GetMapping("/historyOrders")
    public Result<PageResult> historyOrders(OrdersPageQueryDTO ordersPageQueryDTO) {
        log.info("历史订单查询");
        PageResult pg = orderService.historyOrders(ordersPageQueryDTO);
        return Result.success(pg);
    }

    /**
     * 催单
     * @param id
     * @return
     */
    @GetMapping("/reminder/{id}")
    public Result<?> reminder(@PathVariable Integer id) {
        log.info("催单");

        return Result.success();
    }


}
