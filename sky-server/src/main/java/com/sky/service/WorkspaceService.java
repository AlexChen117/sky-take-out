package com.sky.service;

import com.sky.vo.BusinessDataVO;

import java.time.LocalDateTime;

/**
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/16 21:35:36
 */
public interface WorkspaceService {


    BusinessDataVO businessData(LocalDateTime start, LocalDateTime end);

}
