package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 套餐管理
 *
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/2 14:45:03
 */
@RestController
@Slf4j
@Api(tags = "套餐管理")
@RequestMapping("/admin/setmeal")
public class SetMealController {
    @Autowired
    private SetMealService setMealService;


    /**
     * 套餐分页查询
     *
     * @param setmealPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> findByPage(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("套餐分页查询");
        String name = setmealPageQueryDTO.getName();
        Integer categoryId = setmealPageQueryDTO.getCategoryId();
        int page = setmealPageQueryDTO.getPage();
        int pageSize = setmealPageQueryDTO.getPageSize();
        Integer status = setmealPageQueryDTO.getStatus();
        PageResult p = setMealService.findByPage(name, categoryId, page, pageSize, status);
        return Result.success(p);
    }

    /**
     * 添加套餐
     *
     * @param setmealDTO
     * @return
     */
    @PostMapping
    public Result<?> add(@RequestBody SetmealDTO setmealDTO) {
        log.info("添加套餐");
        //System.out.println(setmealDTO);
        setMealService.add(setmealDTO);
        return Result.success();
    }

    /**
     * 根据id查询套餐
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<SetmealVO> findById(@PathVariable Long id) {
        log.info("根据id查询套餐:{}", id);
        SetmealVO setmeal = setMealService.findById(id);
        return Result.success(setmeal);
    }

    /**
     * 修改套餐
     *
     * @param setmealDTO
     * @return
     */
    @PutMapping
    public Result<?> update(@RequestBody SetmealDTO setmealDTO) {
        log.info("修改套餐");
        setMealService.update(setmealDTO);
        return Result.success();
    }

    /**
     * 套餐起售、停售
     *
     * @param status
     * @return
     */
    @PostMapping("/status/{status}")
    public Result<?> statusChange(@PathVariable Integer status, Long id) {
        log.info("套餐起售、停售");
        setMealService.statusChange(status, id);
        return Result.success();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result<?> delete(@RequestParam List<String> ids) {
        log.info("批量删除");
        System.out.println(ids);
        setMealService.delete(ids);
        return Result.success();
    }
}
