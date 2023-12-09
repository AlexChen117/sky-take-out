package com.sky.controller.user;

import com.sky.dto.UserLoginDTO;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户登录
 *
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/9 10:17:01
 */
@RestController
@RequestMapping("/user/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录:{}", userLoginDTO.getCode());
        UserLoginVO login = userService.login(userLoginDTO.getCode());
        return Result.success(login);

    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<?> logout() {
        return Result.success();
    }
}
