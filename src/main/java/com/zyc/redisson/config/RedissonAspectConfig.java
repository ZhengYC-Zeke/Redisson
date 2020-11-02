package com.zyc.redisson.config;

import com.zyc.redisson.template.RedissonLockTemplate;
import com.zyc.redisson.template.impl.SimpleRedissonLockTemplateImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonAspectConfig {

    @Bean
    public RedissonLockTemplate redissonLockTemplate() {
        return new SimpleRedissonLockTemplateImpl();
    }

}
