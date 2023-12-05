package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 分类管理
 *
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/3 17:27:24
 */
@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "分类管理")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 分页查询
     *
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分类分页查询");
        PageResult pageResult = categoryService.page(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 修改分类
     *
     * @param categoryDTO
     * @return
     */
    @PutMapping
    @ApiOperation(value = "修改分类")
    public Result<?> update(@RequestBody CategoryDTO categoryDTO) {
        log.info("修改分类");
        categoryService.update(categoryDTO);
        return Result.success();
    }

    /**
     * 启用、禁用分类
     *
     * @param status
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation(value = "启用、禁用分类")
    public Result<?> changeStatus(@PathVariable Integer status, Integer id) {
        log.info("启用、禁用分类");
        categoryService.changeStatus(status, id);
        return Result.success();
    }

    /**
     * 新增分类
     *
     * @return
     */
    @PostMapping
    @ApiOperation(value = "新增分类")
    public Result<?> add(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类");
        categoryService.add(categoryDTO);
        return Result.success();
    }

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "删除分类")
    public Result<?> delete(Integer id) {
        log.info("删除分类");
        categoryService.delete(id);
        return Result.success();
    }


}
