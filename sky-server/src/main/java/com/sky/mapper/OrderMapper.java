package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/12 09:23:18
 */
@Mapper
public interface OrderMapper {
    void add(Orders orders);

    @Select("select * from orders where number = #{number}")
    Orders findOrderByNumber(String number);

    void updateById(Orders orders);
}
