package com.zyc.redisson.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedissonLock {

    /**
     * 锁名称
     */
    String lockName();

    /**
     * 是否使用尝试锁
     */
    boolean tryLock() default false;

    /**
     * 最长等待时间：默认 -1，一直等待
     */
    long waitTime() default -1L;

    /**
     * 锁释放时间：默认 -1，永不失效
     */
    long expireTime() default -1L;

    /**
     * 时间单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 没拿到锁是否抛出异常
     */
    boolean noLockException() default false;

    /**
     * 抛出异常的内容
     */
    String exceptionMessage() default "";

}
