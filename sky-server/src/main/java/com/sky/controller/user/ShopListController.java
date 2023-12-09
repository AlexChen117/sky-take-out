package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.ShopListService;
import com.sky.vo.DishItemVO;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 套餐和菜品浏览
 *
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/9 14:29:00
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class ShopListController {

    @Autowired
    private ShopListService shopListService;

    /**
     * 条件查询
     *
     * @param type
     * @return
     */
    @GetMapping("/category/list")
    public Result<List<Category>> categoryList(Integer type) {
        log.info("条件查询:{}", type);
        List<Category> category = shopListService.categoryList(type);
        System.out.println("条件查询:" + category);
        return Result.success(category);
    }

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/dish/list")
    public Result<List<DishVO>> dishList(Long categoryId) {
        log.info("根据分类id查询菜品");
        List<DishVO> list = shopListService.dishList(categoryId);
        System.out.println("根据分类id查询菜品: " + list);
        return Result.success(list);
    }

    /**
     * 根据分类id查询套餐
     *
     * @return
     */
    @GetMapping("/setmeal/list")
    public Result<List<Setmeal>> setmealList(Long categoryId) {
        log.info("根据分类id查询套餐");
        List<Setmeal> list = shopListService.setmealList(categoryId);
        System.out.println("根据分类id查询套餐:" + list);
        return Result.success(list);
    }

    /**
     * 根据套餐id查询包含的菜品
     *
     * @param id
     * @return
     */
    @GetMapping("setmeal/dish/{id}")
    public Result<?> findDishBySetmealId(@PathVariable Long id) {
        log.info("根据套餐id查询包含的菜品");
        List<DishItemVO> dishItemVO = shopListService.findDishBySetmealId(id);
        System.out.println("根据套餐id查询包含的菜品: " + dishItemVO);
        return Result.success(dishItemVO);
    }

}
