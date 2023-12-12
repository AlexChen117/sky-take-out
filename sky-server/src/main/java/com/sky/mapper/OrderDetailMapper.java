package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/12 10:24:36
 */
@Mapper
public interface OrderDetailMapper {
    void add(List<OrderDetail> orderDetails);
}
