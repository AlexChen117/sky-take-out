package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

/**
 * 套餐管理Service
 *
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/2 15:11:32
 */
public interface SetMealService {
    PageResult findByPage(String name, Integer categoryId, Integer page, Integer pageSize, Integer status);

    void add(SetmealDTO setmealDTO);

    SetmealVO findById(Long id);
}
