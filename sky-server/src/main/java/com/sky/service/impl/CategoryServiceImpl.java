package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 分类管理service实现类
 *
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/3 17:28:43
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 分页查询
     *
     * @param categoryPageQueryDTO
     * @return
     */
    @Override
    public PageResult page(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        List<Category> categoryList = categoryMapper.page(categoryPageQueryDTO.getName(), categoryPageQueryDTO.getType());
        Page page = (Page) categoryList;
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 修改
     *
     * @param categoryDTO
     */
    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setUpdateUser(BaseContext.getCurrentId());
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

    /**
     * 修改状态
     *
     * @param status
     * @param id
     */
    @Override
    public void changeStatus(Integer status, Integer id) {
        Category category = categoryMapper.findCategoryById(id);
        category.setStatus(status);
        category.setUpdateUser(BaseContext.getCurrentId());
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

    /**
     * 添加分类
     *
     * @param categoryDTO
     */
    @Override
    public void add(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setCreateTime(LocalDateTime.now());
        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.add(category);
    }

    /**
     * 删除分类
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        categoryMapper.delete(id);
    }
}
