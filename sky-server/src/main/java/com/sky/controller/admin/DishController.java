package com.sky.controller.admin;

import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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
        //清除缓存
        String key = "DISH:" + dishDTO.getCategoryId();
        cleanCache(key);
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
        //将所有的菜品缓存数据清理掉，所有以dish_开头的key
        cleanCache("DISH:*");
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
        //将所有的菜品缓存数据清理掉，所有以dish_开头的key
        cleanCache("DISH:*");
        return Result.success();
    }

    /**
     * 菜品起售、停售
     *
     * @param status
     * @return
     */
    @PostMapping("/status/{status}")
    public Result<?> statusChange(@PathVariable Integer status, Long id) {
        log.info("菜品起售、停售");
        dishService.statusChange(status, id);
        //将所有的菜品缓存数据清理掉，所有以dish_开头的key
        cleanCache("DISH:*");
        return Result.success();
    }

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    public Result<List<Dish>> list(Long categoryId) {
        List<Dish> dish = dishService.list(categoryId);
        return Result.success(dish);
    }

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 清理缓存数据
     * @param pattern
     */
    private void cleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }

}
