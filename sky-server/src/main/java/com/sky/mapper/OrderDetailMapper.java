package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/12 10:24:36
 */
@Mapper
public interface OrderDetailMapper {
    void add(List<OrderDetail> orderDetails);

    @Select("select * from order_detail where order_id = #{id};")
    List<OrderDetail> findByOrdersId(Long id);

    List<Map<String, Object>> top10(LocalDate begin, LocalDate end);
}
