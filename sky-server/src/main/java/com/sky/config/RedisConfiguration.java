package com.sky.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/8 11:12:07
 */
@Configuration
public class RedisConfiguration {
    @Bean
    public RedisTemplate<String , Object> redisTemplate (RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String , Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        //序列化相关配置
        template.setKeySerializer(new StringRedisSerializer());
        template.setDefaultSerializer(new GenericFastJsonRedisSerializer());
        return template;
    }

}
