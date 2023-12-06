package com.sky.mapper;

import com.sky.annotation.AutoFillAdd;
import com.sky.annotation.AutoFillUpdate;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 分类管理Mapper类
 *
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/3 17:29:21
 */
@Mapper
public interface CategoryMapper {

    List<Category> page(String name, Integer type);

    @AutoFillUpdate
    void update(Category category);

    @Select("select * from  category where id = #{id}")
    Category findCategoryById(Integer id);

    @AutoFillAdd
    void add(Category category);

    @Delete("delete from category where id = #{id}")
    void delete(Integer id);


    @Select("select count(*) from dish where category_id=#{id}")
    int countDishByCategoryId(Integer id);

    @Select("select * from category where type=#{type}")
    List<Category> list(String type);

    @Select("select count(*) from category where name = #{name} and id != #{id}")
    int findCategoryByName(String name, Long id);
}
