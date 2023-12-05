package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


}
