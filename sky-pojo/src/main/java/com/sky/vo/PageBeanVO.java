package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * PageBean分页
 *
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/1 20:48:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBeanVO {
    private Long total; // 总条数
    private List rows; // 当前页展示数据
}
