package com.sky.controller.admin;

import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * @param name
     * @param categoryId
     * @param page
     * @param pageSize
     * @param status
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> findByPage(String name, Integer categoryId,
                                         @RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue = "10") Integer pageSize,
                                         Integer status) {
        log.info("套餐分页查询");
        PageResult p = setMealService.findByPage(name,categoryId,page,pageSize,status);
        return Result.success(p);
    }
}
