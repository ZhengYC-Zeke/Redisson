package com.zyc;

import com.zyc.entity.User;
import com.zyc.manager.RedissonManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class LockTest {

    @Autowired
    private RedissonManager redissonManager;

    @Test
    public void lockWithExpireTime1() {
        redissonManager.insertWithExpireTime(new User(2, "Mary", 1));
    }

    @Test
    public void lockWithExpireTime2() {
        redissonManager.insertWithExpireTime(new User(2, "Mary", 2));
    }

    @Test
    public void lockWithException1() {
        redissonManager.insertWithException(new User(2, "Mary", 19));
    }

    @Test
    public void lockWithException2() {
        redissonManager.insertWithException(new User(2, "Mary", 19));
    }

    @Test
    public void tryLockWithException1() {
        redissonManager.insertTryWithException(new User(2, "Mary", 19));
    }

    @Test
    public void tryLockWithException2() {
        redissonManager.insertTryWithException(new User(2, "Mary", 19));
    }

}
