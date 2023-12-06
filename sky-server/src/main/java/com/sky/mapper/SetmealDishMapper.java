package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/5 16:42:22
 */
@Mapper
public interface SetmealDishMapper {
    Integer selectCountByDishIds(List<String> ids, Integer enable);

    void add(List<SetmealDish> setmealDishes);
}
