package com.zyc.redisson.template;

import java.util.concurrent.TimeUnit;

public interface RedissonLockTemplate {

    Long DEFAULT_EXPIRE_TIME = -1L;

    Long DEFAULT_WAIT_TIME = -1L;

    <T> T lock(RedissonLockCallBack<T> callBack, long expireTime, TimeUnit timeUnit, boolean noLockException, String exceptionMessage);

    <T> T tryLock(RedissonLockCallBack<T> callBack, long expireTime, long waitTime, TimeUnit timeUnit, boolean noLockException, String exceptionMessage);

}
