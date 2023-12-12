package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/12 20:19:55
 */
@Mapper
public interface AdminOrderMapper {
    Page<Orders> findOrders(OrdersPageQueryDTO ordersPageQueryDTO);
}
