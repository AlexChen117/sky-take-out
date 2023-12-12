package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.context.BaseContext;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.entity.Orders;
import com.sky.mapper.AdminOrderMapper;
import com.sky.mapper.OrderMapper;
import com.sky.result.PageResult;
import com.sky.service.AdminOrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public void cancel(OrdersRejectionDTO ordersRejectionDTO) {
        Orders orders = new Orders();
        orders.setId(ordersRejectionDTO.getId());
        orders.setStatus(Orders.CANCELLED);
        orders.setCancelReason(ordersRejectionDTO.getRejectionReason());
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
}
