package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

/**
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/10 14:17:16
 */
public interface ShoppingCartService {
    void add(ShoppingCartDTO shoppingCartDTO);

    List<ShoppingCart> list();

    void delete();

    void sub(ShoppingCartDTO shoppingCartDTO);
}
