package com.sky.service.impl;

import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DishException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.FlavorMapper;
import com.sky.service.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 菜品管理Service的实现类
 *
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/5 14:10:05
 */
@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishMapper dishMapper;
    private final FlavorMapper flavorMapper;

    /**
     * 新增菜品
     *
     * @param dishDTO
     */
    @Override
    @Transactional
    public void add(DishDTO dishDTO) {
        //校验
        Dish dishQuery = new Dish();
        dishQuery.setName(dishDTO.getName());
        Integer count = dishMapper.selectCount(dishQuery);
        if (count > 0) {
            throw new DishException("菜品已存在");
        }
        //核心代码
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        //默认停售
        dish.setStatus(StatusConstant.DISABLE);
        dishMapper.add(dish);
        //添加口味
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            //将flavors中的dishId替换成对应的dishId
            flavors.forEach(item -> item.setDishId(dish.getId()));
            flavorMapper.addBatch(flavors);
        }

    }
}
