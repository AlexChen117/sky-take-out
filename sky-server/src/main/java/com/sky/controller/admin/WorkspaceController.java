package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 工作台
 *
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/16 21:14:17
 */
@RestController
@RequestMapping("/admin/workspace")
@Slf4j
@RequiredArgsConstructor
public class WorkspaceController {
    private final WorkspaceService workspaceService;

    /**
     * 查询今日运营数据
     *
     * @return
     */
    @GetMapping("/businessData")
    public Result<BusinessDataVO> businessData() {
        log.info("查询今日运营数据");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
        LocalDate today = LocalDate.now();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
        BusinessDataVO businessDataVO = workspaceService.businessData(startOfDay, endOfDay);
        return Result.success(businessDataVO);
    }

    /**
     * 查询套餐总览
     *
     * @return
     */
    @GetMapping("/overviewSetmeals")
    public Result<SetmealOverViewVO> overviewSetmeals() {
        log.info("查询套餐总览");
        SetmealOverViewVO setmealOverViewVO = workspaceService.overviewSetmeals();
        return Result.success(setmealOverViewVO);
    }

    /**
     * 查询菜品总览
     *
     * @return
     */
    @GetMapping("/overviewDishes")
    public Result<DishOverViewVO> overviewDishes() {
        log.info("查询菜品总览");
        DishOverViewVO dishOverViewVO = workspaceService.overviewDishes();
        return Result.success(dishOverViewVO);
    }

    /**
     * 查询订单管理数据
     *
     * @return
     */
    @GetMapping("/overviewOrders")
    public Result<OrderOverViewVO> overviewOrders() {
        log.info("查询订单管理数据");
        OrderOverViewVO orderOverViewVO = workspaceService.overviewOrders();
        return Result.success(orderOverViewVO);
    }
}
