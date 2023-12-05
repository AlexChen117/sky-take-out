package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

/**
 * 菜品管理Service
 *
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/5 14:09:38
 */
public interface DishService {
    void add(DishDTO dishDTO);

    PageResult page(DishPageQueryDTO dishPageQueryDTO);
}
