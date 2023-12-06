package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFillAdd;
import com.sky.annotation.AutoFillUpdate;
import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 菜品管理Mapper
 *
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/5 14:10:41
 */
@Mapper
public interface DishMapper {
    @AutoFillAdd
    void add(Dish dish);

    Integer selectCount(Dish dishQuery);

    Page<Dish> selectPage(Dish dishQuery);

    Integer selectCountByIdsAndStatus(List<String> ids, Integer status);

    void deleteByIds(List<String> ids);

    @Select("select * from dish where id = #{id}")
    Dish findById(Long id);

    @AutoFillUpdate
    void update(Dish dish);

    @Select("select * from dish where category_id = #{categoryId} and status = #{status}")
    List<Dish> list(Long categoryId,Integer status);
}
