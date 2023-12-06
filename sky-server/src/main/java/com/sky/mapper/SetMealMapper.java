package com.sky.mapper;

import com.sky.annotation.AutoFillAdd;
import com.sky.annotation.AutoFillUpdate;
import com.sky.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 套餐Mapper
 *
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/2 15:10:45
 */
@Mapper
public interface SetMealMapper {
    List<Setmeal> findByPage(String name, Integer categoryId, Integer status);

    @AutoFillAdd
    void add(Setmeal setmeal);

    @Select("select * from setmeal where id = #{id}")
    Setmeal findById(Long id);

    @AutoFillUpdate
    void update(Setmeal setmeal);

    @Select("select count(*) from setmeal where name= #{setmealDTOName} and id != #{setmealDTOId}")
    int findByNameAndNotId(Long setmealDTOId, String setmealDTOName);

    @Select("select count(*) from setmeal where name= #{setmealDTOName}")
    int findByName(String setmealDTOName);

    void delete(List<String> ids);
}
