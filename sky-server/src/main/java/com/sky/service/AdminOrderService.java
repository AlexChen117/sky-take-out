package com.sky.service;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.OrderVO;

import java.util.List;

/**
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/12 20:19:20
 */
public interface AdminOrderService {
    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);
}
