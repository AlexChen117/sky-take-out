package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 口味管理Mapper
 *
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/5 14:45:52
 */
@Mapper
public interface FlavorMapper {
    void addBatch(List<DishFlavor> flavors);
}
