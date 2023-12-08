package com.sky.controller.admin;

import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 店铺Controller
 *
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/8 15:34:23
 */
@RestController("adminShopController")
@Slf4j
@RequestMapping("/admin/shop")

public class ShopController {
    public static final String SHOP_STATUS = "SHOP_STATUS";
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 更新店铺状态
     *
     * @param status
     * @return
     */
    @PutMapping("/{status}")
    public Result<?> updateStatus(@PathVariable Integer status) {
        //数据类型为string,使用Value
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.set(SHOP_STATUS, status);
        return Result.success();
    }

    /**
     * 获取店铺状态
     *
     * @return
     */
    @GetMapping("/status")
    public Result<Integer> getStatus() {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        Object status = ops.get(SHOP_STATUS);
        if (Objects.isNull(status)) {
            return Result.success(0);
        }
        return Result.success((Integer) status);
    }
}
