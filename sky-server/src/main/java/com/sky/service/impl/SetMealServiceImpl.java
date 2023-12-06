package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetmealDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetMealMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 套餐管理Service的实现类
 *
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/2 15:11:59
 */
@Service
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    private SetMealMapper setMealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;


    /**
     * 套餐分页查询
     *
     * @param name
     * @param categoryId
     * @param page
     * @param pageSize
     * @param status
     * @return
     */
    @Override
    public PageResult findByPage(String name, Integer categoryId, Integer page, Integer pageSize, Integer status) {
        PageHelper.startPage(page, pageSize);
        List<Setmeal> list = setMealMapper.findByPage(name, categoryId, status);
        Page<Setmeal> p = (Page<Setmeal>) list;
        return new PageResult(p.getTotal(), p.getResult());
    }

    /**
     * 添加套餐
     *
     * @param setmealDTO
     */
    @Override
    @Transactional
    public void add(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setMealMapper.add(setmeal);
        Long id = setmeal.getId();
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(id);
        });
        System.out.println(setmealDishes);
        setmealDishMapper.add(setmealDishes);
    }

    /**
     * @return
     */
    @Override
    public SetmealVO findById(Long id) {
        Setmeal setmealQuery = setMealMapper.findById(id);
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmealQuery, setmealVO);
        setmealVO.setSetmealDishes(setmealDishMapper.findBySetmealId(id));
        return setmealVO;
    }
}
