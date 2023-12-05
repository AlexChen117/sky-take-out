package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.DishException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.FlavorMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    private final SetmealDishMapper setmealDishMapper;

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

    /**
     * 分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult page(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Dish dishQuery = new Dish();
        BeanUtils.copyProperties(dishPageQueryDTO, dishQuery);
        Page<Dish> page = dishMapper.selectPage(dishQuery);
        //Dish 转换为 DishVO
        List<DishVO> collect = page.getResult().stream()
                .map(dish -> {
                    DishVO dishVO = new DishVO();
                    BeanUtils.copyProperties(dish, dishVO);
                    return dishVO;
                }).collect(Collectors.toList());
        return new PageResult(page.getTotal(), collect);
    }

    /**
     * 删除
     *
     * @param ids
     */
    @Override
    public void delete(List<String> ids) {
        Integer countByIdsAndStatus = dishMapper.selectCountByIdsAndStatus(ids, StatusConstant.ENABLE);
        if (countByIdsAndStatus > 0) {
            throw new DeletionNotAllowedException("启售商品无法删除!");
        }
        Integer countByDishIds = setmealDishMapper.selectCountByDishIds(ids, StatusConstant.ENABLE);
        if (countByDishIds > 0) {
            throw new DeletionNotAllowedException("套餐关联的商品无法删除!");
        }
        dishMapper.deleteByIds(ids);


    }
}
