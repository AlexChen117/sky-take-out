package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFillAdd;
import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜品管理Mapper
 *
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/5 14:10:41
 */
@Mapper
public interface DishMapper {
    @AutoFillAdd
    void add(Dish dish);

    Integer selectCount(Dish dishQuery);

    Page<Dish> selectPage(Dish dishQuery);
}
