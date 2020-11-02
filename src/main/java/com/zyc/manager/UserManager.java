package com.zyc.manager;

import com.zyc.entity.User;
import com.zyc.service.UserService;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class UserManager {

    @Resource
    private UserService userService;

    @Resource
    private RedissonClient redissonClient;

    private static final String KEY = "Redis:Test";

    private static final String LOCK = "Redis:Lock";

    private void put(User user) {
        RMap<Object, Object> map = redissonClient.getMap(KEY);
        map.put(user.getId(), user);
        map.expire(30L, TimeUnit.SECONDS);
    }

    private User get(Integer userId) {
        return (User) redissonClient.getMap(KEY).get(userId);
    }

    public User getUserByTryLock(Integer userId) throws Exception {
        User user = get(userId);
        if (user == null) {
            RLock lock = redissonClient.getLock(LOCK);
            if (lock.tryLock(10L, TimeUnit.SECONDS)) {
                user = userService.selectById(userId);
                Thread.sleep(30000);
                put(user);
                lock.unlock();
            } else {
                System.out.println("come");
                user = get(userId);
                if (user != null) {
                    return user;
                }
                return getUserByTryLock(userId);
            }
        }
        return user;
    }

}
