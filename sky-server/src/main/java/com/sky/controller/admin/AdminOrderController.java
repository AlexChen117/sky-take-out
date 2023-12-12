package com.sky.controller.admin;

import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.AdminOrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/12 20:18:11
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/order")
@Slf4j
public class AdminOrderController {
    private final AdminOrderService adminOrderService;

    /**
     * 订单搜索
     *
     * @param ordersPageQueryDTO
     * @return
     */
    @GetMapping("/conditionSearch")
    public Result<PageResult> conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        log.info("订单搜索");
        //System.out.println(ordersPageQueryDTO.getStatus());
        PageResult orderVOList = adminOrderService.conditionSearch(ordersPageQueryDTO);
        //System.out.println(orderVOList);
        return Result.success(orderVOList);
    }

    /**
     * 各个状态的订单数量统计
     *
     * @return
     */
    @GetMapping("/statistics")
    public Result<OrderStatisticsVO> statistics() {
        log.info("各个状态的订单数量统计");
        OrderStatisticsVO orderStatisticsVO = adminOrderService.statistics();
        return Result.success(orderStatisticsVO);
    }

    /**
     * 取消订单
     *
     * @param ordersRejectionDTO
     * @return
     */
    @PutMapping("/cancel")
    public Result<?> cancel(@RequestBody OrdersRejectionDTO ordersRejectionDTO) {
        log.info("取消订单");
        adminOrderService.cancel(ordersRejectionDTO);
        return Result.success();
    }

    /**
     * 完成订单
     *
     * @param id
     * @return
     */
    @PutMapping("complete/{id}")
    public Result<?> complete(@PathVariable Long id) {
        log.info("完成订单");
        adminOrderService.complete(id);
        return Result.success();
    }

    /**
     * 接单
     *
     * @param ordersConfirmDTO 接单
     * @return Result
     */
    @PutMapping("/confirm")
    public Result<?> confirm(@RequestBody OrdersConfirmDTO ordersConfirmDTO) {
        log.info("接单");
        adminOrderService.confirm(ordersConfirmDTO);
        return Result.success();
    }

    /**
     * 派送订单
     *
     * @param id
     * @return
     */
    @PutMapping("delivery/{id}")
    public Result<?> delivery(@PathVariable Long id) {
        log.info("派送订单");
        adminOrderService.delivery(id);
        return Result.success();
    }

    /**
     * 拒单
     *
     * @return
     */
    @PutMapping("/rejection")
    public Result<?> rejection(@RequestBody OrdersRejectionDTO ordersRejectionDTO) {
        log.info("拒单");
        adminOrderService.rejection(ordersRejectionDTO);
        return Result.success();
    }

    /**
     * 查询订单详情
     *
     * @param id
     * @return
     */
    @GetMapping("/details/{id}")
    public Result<OrderVO> details(@PathVariable Long id) {
        log.info("查询订单详情");
        OrderVO vo = adminOrderService.details(id);
        return Result.success(vo);
    }
}
