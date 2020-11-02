package com.zyc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zyc.dao")
public class RedissonApplication {

    public static void main(String[] args) {

        SpringApplication.run(RedissonApplication.class, args);

    }

}
