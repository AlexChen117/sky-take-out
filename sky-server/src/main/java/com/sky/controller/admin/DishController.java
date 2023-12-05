package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜品管理
 *
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/5 14:08:23
 */
@RestController
@RequestMapping("/admin/dish")
@RequiredArgsConstructor
@Slf4j
public class DishController {
    private final DishService dishService;

    /**
     * 新增菜品
     *
     * @param dishDTO
     * @return
     */
    @PostMapping
    public Result<?> add(@RequestBody DishDTO dishDTO) {
        log.info("添加菜品");
        dishService.add(dishDTO);
        return Result.success();
    }

    /**
     * 分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("分页查询");
        PageResult pageResult = dishService.page(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result<?> delete(@RequestParam List<String> ids) {
        log.info("批量删除");
        dishService.delete(ids);
        return Result.success();
    }

    /**
     * 信息回显
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Result<DishVO> findById(@PathVariable Long id) {
        log.info("信息回显");
        DishVO dishVO = dishService.findById(id);
        return Result.success(dishVO);
    }

    /**
     * 更新菜品
     *
     * @param dishDTO
     * @return
     */
    @PutMapping
    public Result<?> update(@RequestBody DishDTO dishDTO) {
        log.info("更新菜品");
        dishService.update(dishDTO);
        return Result.success();
    }

    /**
     * 菜品起售、停售
     *
     * @param status
     * @return
     */
    @PostMapping("/status/{status}")
    public Result<?> statusChange(@PathVariable Integer status,Long id) {
        log.info("菜品起售、停售");
        dishService.statusChange(status,id);
        return Result.success();
    }

}
