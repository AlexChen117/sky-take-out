package com.sky.controller.user;

import com.sky.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户端店铺控制器
 *
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/8 16:08:25
 */
@RestController("userShopController")
@RequestMapping("/user/shop")
public class ShopController {
    public static final String SHOP_STATUS = "SHOP_STATUS";
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取店铺状态
     *
     * @return
     */
    @GetMapping("/status")
    public Result<Integer> getStatus() {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        Object status = ops.get(SHOP_STATUS);
        return Result.success((Integer) status);
    }
}
