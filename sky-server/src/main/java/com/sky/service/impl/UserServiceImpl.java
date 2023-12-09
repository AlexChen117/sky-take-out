package com.sky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sky.constant.JwtClaimsConstant;
import com.sky.entity.User;
import com.sky.mapper.UserMapper;
import com.sky.properties.JwtProperties;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/9 10:37:51
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    public static final String URL = "https://api.weixin.qq.com/sns/jscode2session";
    public static final String AUTHORIZATION_CODE = "authorization_code";
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private WeChatProperties weChatProperties;

    /**
     * 用户登录
     *
     * @param code
     * @return
     */
    @Override
    public UserLoginVO login(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", code);
        map.put("grant_type", AUTHORIZATION_CODE);
        String getJson = HttpClientUtil.doGet(URL, map);
        log.info("微信返回的数据:{}", getJson);
        JSONObject jsonObject = JSONObject.parseObject(getJson);
        String openid = jsonObject.getString("openid");
        User userQuery = new User();
        userQuery.setOpenid(openid);
        User user = userMapper.selectOne(userQuery);
        if (Objects.isNull(user)) {
            user = new User();
            user.setOpenid(openid);
            user.setCreateTime(LocalDateTime.now());
            userMapper.add(user);
        }
        UserLoginVO userLoginVO = new UserLoginVO();
        //获取jwt
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);
        userLoginVO.setId(user.getId());
        userLoginVO.setOpenid(openid);
        userLoginVO.setToken(token);
        return userLoginVO;
    }
}
