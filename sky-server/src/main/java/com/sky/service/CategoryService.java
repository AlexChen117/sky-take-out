package com.sky.service;

import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;

/**
 * 分类管理 service
 *
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/3 17:28:17
 */
public interface CategoryService {
    PageResult page(CategoryPageQueryDTO categoryPageQueryDTO);
}
