package com.sky.mapper;

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

    void update(Category category);

    @Select("select * from  category where id = #{id}")
    Category findCategoryById(Integer id);

    void add(Category category);

    @Delete("delete from category where id = #{id}")
    void delete(Integer id);
}
