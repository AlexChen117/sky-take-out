package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.entity.Setmeal;
import com.sky.mapper.SetMealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
