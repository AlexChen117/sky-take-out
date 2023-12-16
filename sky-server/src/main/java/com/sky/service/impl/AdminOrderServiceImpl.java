package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.mapper.AdminOrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.result.PageResult;
import com.sky.service.AdminOrderService;
import com.sky.vo.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


/**
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/12 20:19:35
 */

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService {
    private final AdminOrderMapper adminOrderMapper;
    private final UserMapper userMapper;

    /**
     * 订单搜索
     *
     * @param ordersPageQueryDTO
     * @return
     */
    @Override
    public PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        PageHelper.startPage(ordersPageQueryDTO.getPage(), ordersPageQueryDTO.getPageSize());
        Page<Orders> orders = adminOrderMapper.findOrders(ordersPageQueryDTO);
        List<OrderVO> collect = orders.getResult().stream().map(order -> {
            OrderVO vo = new OrderVO();
            BeanUtils.copyProperties(order, vo);
            StringBuilder cjj = new StringBuilder();
            List<OrderDetail> list = adminOrderMapper.findOrderDetailsByOrderId(vo.getId());
            for (OrderDetail orderDetail : list) {
                cjj.append(orderDetail.getName()).append("*").append(orderDetail.getNumber()).append(System.lineSeparator());
            }
            vo.setOrderDishes(cjj.toString());
            return vo;
        }).collect(Collectors.toList());
        return new PageResult(orders.getTotal(), collect);
    }

    /**
     * 各个状态的订单数量统计
     *
     * @return
     */
    @Override
    public OrderStatisticsVO statistics() {
        OrderStatisticsVO orderStatisticsVO = new OrderStatisticsVO();
        orderStatisticsVO.setConfirmed(adminOrderMapper.findOrdersByStatus(Orders.CONFIRMED));
        orderStatisticsVO.setToBeConfirmed(adminOrderMapper.findOrdersByStatus(Orders.TO_BE_CONFIRMED));
        orderStatisticsVO.setDeliveryInProgress(adminOrderMapper.findOrdersByStatus(Orders.DELIVERY_IN_PROGRESS));
        return orderStatisticsVO;
    }

    /**
     * 取消订单
     *
     * @param ordersCancelDTO
     */
    @Override
    public void cancel(OrdersCancelDTO ordersCancelDTO) {
        Orders orders = new Orders();
        orders.setId(ordersCancelDTO.getId());
        orders.setStatus(Orders.CANCELLED);
        orders.setCancelReason(ordersCancelDTO.getCancelReason());
        orders.setCancelTime(LocalDateTime.now());
        adminOrderMapper.update(orders);
    }

    /**
     * 完成订单
     *
     * @param id
     */
    @Override
    public void complete(Long id) {
        Orders orders = new Orders();
        orders.setId(id);
        orders.setStatus(Orders.COMPLETED);
        orders.setDeliveryTime(LocalDateTime.now());
        adminOrderMapper.update(orders);
    }

    /**
     * 接单
     *
     * @param
     */
    @Override
    public void confirm(OrdersConfirmDTO ordersConfirmDTO) {
        Orders orders = new Orders();
        orders.setId(ordersConfirmDTO.getId());
        orders.setStatus(Orders.CONFIRMED);
        adminOrderMapper.update(orders);
    }

    /**
     * 派送
     *
     * @param id
     */
    @Override
    public void delivery(Long id) {
        Orders orders = new Orders();
        orders.setId(id);
        orders.setStatus(Orders.DELIVERY_IN_PROGRESS);
        long timeMillis = System.currentTimeMillis() + 720000;
        Instant instant = Instant.ofEpochMilli(timeMillis);
        orders.setEstimatedDeliveryTime(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()));
        orders.setDeliveryStatus(1);
        adminOrderMapper.update(orders);
    }

    /**
     * 拒单
     *
     * @param ordersRejectionDTO
     */
    @Override
    public void rejection(OrdersRejectionDTO ordersRejectionDTO) {
        Orders orders = new Orders();
        orders.setId(ordersRejectionDTO.getId());
        orders.setStatus(Orders.CANCELLED);
        orders.setCancelTime(LocalDateTime.now());
        orders.setCancelReason(ordersRejectionDTO.getRejectionReason());
        adminOrderMapper.update(orders);
    }

    /**
     * 查询订单详情
     *
     * @param id
     * @return
     */
    @Override
    public OrderVO details(Long id) {
        Orders orders = adminOrderMapper.findOrdersById(id);
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(orders, orderVO);
        List<OrderDetail> list = adminOrderMapper.findOrderDetailsByOrderId(id);
        orderVO.setOrderDetailList(list);
        return orderVO;
    }

    /**
     * 营业额统计接口
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end) {
        List<Map<String, Object>> list = adminOrderMapper.turnoverStatistics(begin, end);
        String day = list.stream().map(item -> (String) item.get("day")).collect(Collectors.joining(","));
        String turnover = list.stream().map(item -> (BigDecimal) item.get("turnover")).map(BigDecimal::toString).collect(Collectors.joining(","));
        return new TurnoverReportVO(day, turnover);
    }

    /**
     * 用户统计接口
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public UserReportVO userStatistics(LocalDate begin, LocalDate end) {
        ArrayList<LocalDate> list = getLocalDates(begin, end);
        List<Map<String, Object>> map = userMapper.userStatistics(list);
        String day = map.stream().map(item -> (String) item.get("day")).collect(Collectors.joining(","));
        String allCount = map.stream().map(item -> item.get("all_count")).map(Object::toString).collect(Collectors.joining(","));
        String userCount = map.stream().map(item -> item.get("user_count")).map(Object::toString).collect(Collectors.joining(","));
        return new UserReportVO(day, allCount, userCount);
    }

    /**
     * 订单统计接口
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public OrderReportVO ordersStatistics(LocalDate begin, LocalDate end) {
        ArrayList<LocalDate> localDates = getLocalDates(begin, end);
        List<Map<String, Object>> mapList = adminOrderMapper.ordersStatisticsNumber(localDates);
        String dateList = mapList.stream().map(item -> (String) item.get("day")).collect(Collectors.joining(","));
        String orderCountList = mapList.stream().map(item -> item.get("total")).map(Object::toString).collect(Collectors.joining(","));
        String validOrderCountList = mapList.stream().map(item -> item.get("valid_number")).map(Object::toString).collect(Collectors.joining(","));

        Map<String, Object> map = adminOrderMapper.ordersStatistics(begin, end);
        Long totalNumber = (Long) map.get("total_number");
        Long completeNumber = (Long) map.get("complete_number");
        BigDecimal decimal = (BigDecimal) map.get("round");

        OrderReportVO orderReportVO = new OrderReportVO();
        orderReportVO.setDateList(dateList);
        orderReportVO.setOrderCountList(orderCountList);
        orderReportVO.setValidOrderCountList(validOrderCountList);
        orderReportVO.setTotalOrderCount(totalNumber.intValue());
        orderReportVO.setValidOrderCount(completeNumber.intValue());
        orderReportVO.setOrderCompletionRate(decimal.doubleValue());
        return orderReportVO;
    }

    /**
     * 产生时间列表
     *
     * @param begin
     * @param end
     * @return
     */
    private static ArrayList<LocalDate> getLocalDates(LocalDate begin, LocalDate end) {
        ArrayList<LocalDate> list = new ArrayList<>();
        list.add(begin);
        while (true) {
            begin = begin.plusDays(1L);
            if (begin.isAfter(end)) {
                break;
            }
            list.add(begin);
        }
        return list;
    }
}
