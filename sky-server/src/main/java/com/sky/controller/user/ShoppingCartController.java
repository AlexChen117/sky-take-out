package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车
 *
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/10 14:16:31
 */
@Slf4j
@RestController
@RequestMapping("/user/shoppingCart")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    /**
     * 购物车添加
     *
     * @param shoppingCartDTO
     * @return
     */
    @PostMapping("/add")
    public Result<?> add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("购物车添加菜品");
        System.out.println(shoppingCartDTO);
        shoppingCartService.add(shoppingCartDTO);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<List<ShoppingCart>> list() {
        log.info("购物车查看");
        List<ShoppingCart> list = shoppingCartService.list();
        return Result.success(list);
    }
}
