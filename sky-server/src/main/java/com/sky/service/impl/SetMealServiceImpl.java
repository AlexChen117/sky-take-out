package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetmealDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.exception.SetmealException;
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
    @Transactional //事务
    public void add(SetmealDTO setmealDTO) {
        //校验
        String setmealDTOName = setmealDTO.getName();
        int count = setMealMapper.findByName(setmealDTOName);
        if (count > 0) {
            throw new SetmealException("套餐名重复,请检查");
        }
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
     * 根据id查询套餐(回显)
     *
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

    /**
     * 更新套餐
     *
     * @param setmealDTO
     */
    @Override
    @Transactional
    public void update(SetmealDTO setmealDTO) {
        //校验
        Long setmealDTOId = setmealDTO.getId();
        String setmealDTOName = setmealDTO.getName();
        int count = setMealMapper.findByNameAndNotId(setmealDTOId, setmealDTOName);
        if (count > 0) {
            throw new SetmealException("套餐名重复,请检查");
        }
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setMealMapper.update(setmeal);
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishMapper.deleteBySetmealId(setmeal.getId());
        if (setmealDishes != null) {
            setmealDishes.forEach(setmealDish -> {
                setmealDish.setSetmealId(setmeal.getId());
            });
            setmealDishMapper.add(setmealDishes);
        }


    }

    /**
     * 套餐起售、停售
     *
     * @param status
     * @param id
     */

    @Override
    public void statusChange(Integer status, Long id) {
        Setmeal setmeal = setMealMapper.findById(id);
        setmeal.setStatus(status);
        setMealMapper.update(setmeal);
    }

    @Override
    public void delete(List<String> ids) {
        setMealMapper.delete(ids);
        for (String id : ids) {
            setmealDishMapper.deleteBySetmealId(Long.valueOf(id));
        }
    }

}
