package com.zyc.redisson.template;

public interface RedissonLockCallBack<T> {

    /**
     * 需要上锁的逻辑代码
     * @return T
     */
    T process();

    /**
     * 获取锁名称
     * @return String
     */
    String getLockName();

}
