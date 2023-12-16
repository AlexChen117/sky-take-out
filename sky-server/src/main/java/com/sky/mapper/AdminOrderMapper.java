package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/12 20:19:55
 */
@Mapper
public interface AdminOrderMapper {
    Page<Orders> findOrders(OrdersPageQueryDTO ordersPageQueryDTO);

    @Select("select count(*) from orders where status = #{status};")
    Integer findOrdersByStatus(Integer status);

    void update(Orders orders);

    @Select("select * from orders where id = #{id}")
    Orders findOrdersById(Long id);

    @Select("select * from order_detail where order_id = #{id}")
    List<OrderDetail> findOrderDetailsByOrderId(Long id);

    @Select("select * from orders where order_time < #{time} and status =1 and pay_status = 0")
    List<Orders> findTimeOutOrder(LocalDateTime time);

    void updateOutTimeOrder();

    void updateCompleteOrders();

    @MapKey("day")
    List<Map<String, Object>> turnoverStatistics(LocalDate begin, LocalDate end);
}
