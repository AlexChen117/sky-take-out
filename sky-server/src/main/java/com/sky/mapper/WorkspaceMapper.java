package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

/**
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/16 21:36:08
 */

@Mapper
public interface WorkspaceMapper {
    Map<String, Objects> businessData(LocalDateTime start, LocalDateTime end);
}
