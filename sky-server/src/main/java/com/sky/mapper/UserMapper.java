package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/9 11:16:19
 */
@Mapper
public interface UserMapper {
    User selectOne(User userQuery);

    void add(User user);
}
