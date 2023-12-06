package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public Result<?> add(@RequestBody SetmealDTO setmealDTO) {
        log.info("添加套餐");
        //System.out.println(setmealDTO);
        setMealService.add(setmealDTO);
        return Result.success();
        //SetmealDTO(id=null, categoryId=13, name=test, price=68, status=0, description=ceshi, image=1.png,
        //        setmealDishes=
        //        [SetmealDish(id=null, setmealId=null, dishId=65, name=草鱼2斤, price=68, copies=1),
        //        SetmealDish(id=null, setmealId=null, dishId=49, name=米饭, price=2, copies=1),
        //        SetmealDish(id=null, setmealId=null, dishId=46, name=王老吉, price=6, copies=1)])
    }
}
