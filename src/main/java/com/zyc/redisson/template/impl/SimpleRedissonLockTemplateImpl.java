package com.zyc.redisson.template.impl;

import com.zyc.redisson.template.RedissonLockCallBack;
import com.zyc.redisson.template.RedissonLockTemplate;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SimpleRedissonLockTemplateImpl implements RedissonLockTemplate {

    private final RedissonClient redissonClient;

    public SimpleRedissonLockTemplateImpl(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public <T> T lock(RedissonLockCallBack<T> callBack, long expireTime, TimeUnit timeUnit, boolean noLockException, String exceptionMessage) {
        RLock lock = getLock(callBack.getLockName());
        boolean flag = false;
        try {
            if (lock.isLocked() && noLockException)
                throwException(exceptionMessage);
            flag = true;
            if (DEFAULT_EXPIRE_TIME.equals(expireTime))
                lock.lock();
            else
                lock.lock(expireTime, timeUnit);
            return callBack.process();
        } finally {
            if (lock != null && lock.isLocked() && flag)
                lock.unlock();
        }
    }

    @Override
    public <T> T tryLock(RedissonLockCallBack<T> callBack, long expireTime, long waitTime, TimeUnit timeUnit, boolean noLockException, String exceptionMessage) {
        RLock lock = getLock(callBack.getLockName());
        try {
            if (lock.isLocked() && noLockException)
                throwException(exceptionMessage);
            boolean tryLock;
            if (DEFAULT_EXPIRE_TIME.equals(expireTime) && DEFAULT_WAIT_TIME.equals(waitTime))
                tryLock = lock.tryLock();
            else if (DEFAULT_EXPIRE_TIME.equals(expireTime))
                tryLock = lock.tryLock(waitTime, timeUnit);
            else
                tryLock = lock.tryLock(waitTime, expireTime, timeUnit);
            if (tryLock)
                try {
                    return callBack.process();
                } finally {
                    if (lock.isLocked())
                        lock.unlock();
                }
            System.out.println("try");
        } catch (InterruptedException e) {
            log.error("线程被中断 --- " + e);
        }
        return tryLock(callBack, expireTime, waitTime, timeUnit, noLockException, exceptionMessage);
    }

    private RLock getLock(String lockName) {
        return redissonClient.getLock(lockName);
    }

    private void throwException(String message) {
        throw new RuntimeException(message);
    }

}
