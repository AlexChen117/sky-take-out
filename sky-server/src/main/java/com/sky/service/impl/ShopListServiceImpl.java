package com.sky.service.impl;

import com.sky.constant.StatusConstant;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.*;
import com.sky.service.ShopListService;
import com.sky.vo.DishItemVO;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);
        List<Dish> dishList = dishMapper.findByList(dish);
        DishVO dishVO = new DishVO();
        List<DishVO> dishVOS = new ArrayList<>();
        for (Dish d : dishList) {
            BeanUtils.copyProperties(d, dishVO);
            dishVO.setFlavors(flavorMapper.selectByDishId(d.getId()));
            dishVOS.add(dishVO);
        }
        return dishVOS;
    }

    /**
     * 根据分类id查询套餐
     *
     * @return
     */
    @Override
    public List<Setmeal> setmealList(Long categoryId) {
        Setmeal setmeal = new Setmeal();
        setmeal.setCategoryId(categoryId);
        setmeal.setStatus(StatusConstant.ENABLE);
        return setMealMapper.list(setmeal);
    }

    @Override
    public List<DishItemVO> findDishBySetmealId(Long id) {
        List<DishItemVO> list = new ArrayList<>();
        DishItemVO vo = new DishItemVO();
        List<SetmealDish> setmealDishList = setmealDishMapper.findBySetmealId(id);
        for (SetmealDish setmealDish : setmealDishList) {
            vo.setName(setmealDish.getName());
            vo.setCopies(setmealDish.getCopies());
            vo.setImage(dishMapper.findImageByDishName(setmealDish.getName()));
            vo.setDescription(dishMapper.findDescDishName(setmealDish.getName()));
            list.add(vo);
        }
        return list;
    }


}
