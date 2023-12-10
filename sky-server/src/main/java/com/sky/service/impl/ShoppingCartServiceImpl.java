package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


/**
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/10 14:17:29
 */
@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartMapper shoppingCartMapper;
    private final DishMapper dishMapper;
    private final SetMealMapper setMealMapper;

    /**
     * 购物车添加
     *
     * @param shoppingCartDTO
     */
    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
        //判断是菜品还是套餐
        if (Objects.nonNull(shoppingCartDTO.getDishId())) {
            ShoppingCart scQuery = new ShoppingCart();
            scQuery.setUserId(BaseContext.getCurrentId());
            scQuery.setDishId(shoppingCartDTO.getDishId());
            scQuery.setDishFlavor(shoppingCartDTO.getDishFlavor());
            ShoppingCart shoppingCart = shoppingCartMapper.selectOne(scQuery);
            if (Objects.isNull(shoppingCart)) {
                shoppingCart = new ShoppingCart();
                Dish dish = dishMapper.findById(shoppingCartDTO.getDishId());
                shoppingCart.setName(dish.getName());
                shoppingCart.setDishId(dish.getId());
                shoppingCart.setUserId(BaseContext.getCurrentId());
                shoppingCart.setDishFlavor(shoppingCartDTO.getDishFlavor());
                shoppingCart.setNumber(1);
                shoppingCart.setAmount(dish.getPrice());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setCreateTime(LocalDateTime.now());
                shoppingCartMapper.add(shoppingCart);
            } else {
                shoppingCart.setNumber(shoppingCart.getNumber() + 1);
                shoppingCartMapper.update(shoppingCart);
            }
        }
        if (Objects.nonNull(shoppingCartDTO.getSetmealId())) {
            ShoppingCart scQuery = new ShoppingCart();
            scQuery.setUserId(BaseContext.getCurrentId());
            scQuery.setSetmealId(shoppingCartDTO.getSetmealId());
            ShoppingCart shoppingCart = shoppingCartMapper.selectOne(scQuery);
            if (Objects.isNull(shoppingCart)) {
                shoppingCart = new ShoppingCart();
                Setmeal setmeal = setMealMapper.findById(shoppingCartDTO.getSetmealId());
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setSetmealId(setmeal.getId());
                shoppingCart.setUserId(BaseContext.getCurrentId());
                shoppingCart.setNumber(1);
                shoppingCart.setAmount(setmeal.getPrice());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setCreateTime(LocalDateTime.now());
                shoppingCartMapper.add(shoppingCart);
            } else {
                shoppingCart.setNumber(shoppingCart.getNumber() + 1);
                shoppingCartMapper.update(shoppingCart);
            }
        }


    }

    @Override
    public List<ShoppingCart> list() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(BaseContext.getCurrentId());
        return shoppingCartMapper.selectList(shoppingCart);
    }

    @Override
    public void delete() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(BaseContext.getCurrentId());
        shoppingCartMapper.delete(shoppingCart);
    }
}
