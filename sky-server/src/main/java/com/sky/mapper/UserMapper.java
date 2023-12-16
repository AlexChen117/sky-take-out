package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    /**
     * 用户统计接口
     *
     * @param list
     * @return
     */
    List<Map<String, Object>> userStatistics(ArrayList<LocalDate> list);
}
