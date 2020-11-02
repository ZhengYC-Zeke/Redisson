package com.zyc.redisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;

//    @Value("${spring.redis.password}")
//    private String password;

    @Value("${spring.redis.port}")
    private Integer port;

    @Value("${spring.redis.database}")
    private Integer database;

    @Bean
    public RedissonClient redissonClient() {

        Config config = new Config();
        config.setCodec(new JsonJacksonCodec()).useSingleServer().setAddress("redis://" + host + ":" + port)
//                .setPassword(password)
                .setDatabase(database);
        return Redisson.create(config );

    }

}
