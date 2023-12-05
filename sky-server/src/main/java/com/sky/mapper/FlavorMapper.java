package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

    void update(DishFlavor flavor);

    @Select("select * from dish_flavor where dish_id = #{id}")
    List<DishFlavor> selectByDishId(Long id);

    @Delete("delete  from dish_flavor where dish_id = #{id}")
    void deleteByDishId(Long id);
}
