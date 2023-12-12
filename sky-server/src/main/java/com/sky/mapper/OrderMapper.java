package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/12 09:23:18
 */
@Mapper
public interface OrderMapper {
    void add(Orders orders);
}
