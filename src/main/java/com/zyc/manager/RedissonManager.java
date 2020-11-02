package com.zyc.manager;

import com.zyc.entity.User;
import com.zyc.redisson.anno.RedissonLock;
import com.zyc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedissonManager {

    private final UserService userService;

    public RedissonManager(UserService userService) {
        this.userService = userService;
    }

    @RedissonLock(lockName = "lockWithExpireTime", expireTime = 20L)
    public void insertWithExpireTime(User user) {
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        userService.insert(user);
    }

    @RedissonLock(lockName = "lockWithExpireTime", noLockException = true, exceptionMessage = "锁被占用")
    public void insertWithException(User user) {
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        userService.insert(user);
    }

    @RedissonLock(lockName = "tryLockWithExpireTime", tryLock = true, waitTime = 10L)
    public void insertTryWithException(User user) {
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        userService.insert(user);
    }

}
