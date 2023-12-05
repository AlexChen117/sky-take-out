package com.sky.mapper;

import com.sky.annotation.AutoFillAdd;
import com.sky.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 套餐Mapper
 *
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/2 15:10:45
 */
@Mapper
public interface SetMealMapper {
    List<Setmeal> findByPage(String name, Integer categoryId, Integer status);

    @AutoFillAdd
    void add(Setmeal setmeal);
}
