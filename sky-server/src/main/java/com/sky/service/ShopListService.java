package com.sky.service;

import com.sky.entity.Category;
import com.sky.entity.Setmeal;
import com.sky.vo.DishItemVO;
import com.sky.vo.DishVO;

import java.util.List;

/**
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/9 14:33:23
 */
public interface ShopListService {
    List<Category> categoryList(Integer type);

    List<DishVO> dishList(Long categoryId);

    List<Setmeal> setmealList(Long categoryId);

    List<DishItemVO> findDishBySetmealId(Long id);
}
