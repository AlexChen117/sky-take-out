package com.sky.service;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import com.sky.vo.TurnoverReportVO;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/12 20:19:20
 */
public interface AdminOrderService {
    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    OrderStatisticsVO statistics();

    void cancel(OrdersCancelDTO ordersCancelDTO);

    void complete(Long id);

    void confirm(OrdersConfirmDTO ordersConfirmDTO);

    void delivery(Long id);

    void rejection(OrdersRejectionDTO ordersRejectionDTO);

    OrderVO details(Long id);

    TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end);
}
