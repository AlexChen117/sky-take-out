package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.sky.constant.StatusConstant;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.*;
import com.sky.service.ShopListService;
import com.sky.vo.DishItemVO;
import com.sky.vo.DishVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/9 14:33:45
 */
@Service
public class ShopListServiceImpl implements ShopListService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetMealMapper setMealMapper;
    @Autowired
    private FlavorMapper flavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 条件查询
     *
     * @param type
     * @return
     */
    @Override
    public List<Category> categoryList(Integer type) {
        return categoryMapper.findByType(type);
    }

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    @Override
    public List<DishVO> dishList(Long categoryId) {
        //走redis取数据
        String key = "DISH:" + categoryId;
        String dishJson = (String) redisTemplate.opsForValue().get(key);
        if (StringUtils.isNoneEmpty(dishJson)) {
            return JSON.parseArray(dishJson, DishVO.class);
        }
        //Integer status = StatusConstant.ENABLE;
        //List<Dish> list = dishMapper.list(categoryId, status);
        //if (Objects.nonNull(list)&&!list.isEmpty()) {
        //    redisTemplate.opsForValue().set(key,JSON.toJSONString(list));
        //}
        //return list;
        //走数据库
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);
        List<Dish> dishList = dishMapper.findByList(dish);
        List<DishVO> dishVOS = new ArrayList<>();
        for (Dish d : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d, dishVO);
            dishVO.setFlavors(flavorMapper.selectByDishId(d.getId()));
            dishVOS.add(dishVO);
        }
        if (Objects.nonNull(dishVOS) && !dishVOS.isEmpty()) {
            redisTemplate.opsForValue().set(key, JSON.toJSONString(dishVOS));
        }
        return dishVOS;
    }

    /**
     * 根据分类id查询套餐
     *
     * @return
     */
    @Override
    @Cacheable(cacheNames = "SETMEAL",key = "#categoryId")
    public List<Setmeal> setmealList(Long categoryId) {
        Setmeal setmeal = new Setmeal();
        setmeal.setCategoryId(categoryId);
        setmeal.setStatus(StatusConstant.ENABLE);
        return setMealMapper.list(setmeal);
    }

    /**
     * 根据套餐id查询包含的菜品
     *
     * @param id
     * @return
     */
    @Override
    public List<DishItemVO> findDishBySetmealId(Long id) {
        List<DishItemVO> list = new ArrayList<>();
        List<SetmealDish> setmealDishList = setmealDishMapper.findBySetmealId(id);
        for (SetmealDish setmealDish : setmealDishList) {
            DishItemVO vo = new DishItemVO();
            vo.setName(setmealDish.getName());
            vo.setCopies(setmealDish.getCopies());
            vo.setImage(dishMapper.findImageByDishName(setmealDish.getName()));
            vo.setDescription(dishMapper.findDescDishName(setmealDish.getName()));
            list.add(vo);
        }
        return list;
    }


}
