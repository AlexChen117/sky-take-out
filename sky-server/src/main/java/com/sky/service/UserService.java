package com.sky.service;

import com.sky.vo.UserLoginVO;

/**
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/9 10:37:15
 */
public interface UserService {
    UserLoginVO login(String code);

}
