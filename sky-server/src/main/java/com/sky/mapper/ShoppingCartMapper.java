package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/10 14:16:59
 */
@Mapper
public interface ShoppingCartMapper {
    ShoppingCart selectOne(ShoppingCart scQuery);

    void add(ShoppingCart shoppingCart);

    void update(ShoppingCart shoppingCart);

    List<ShoppingCart> selectList(ShoppingCart shoppingCart);
}
