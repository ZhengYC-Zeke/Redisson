package com.zyc.redisson.aspect;

import com.zyc.redisson.anno.RedissonLock;
import com.zyc.redisson.template.RedissonLockCallBack;
import com.zyc.redisson.template.RedissonLockTemplate;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

@Aspect
@Component
public class RedissonAspect {

    private final RedissonLockTemplate redissonLockTemplate;

    @Value("${spring.redis.prefix:match:}")
    private String prefix;

    public RedissonAspect(RedissonLockTemplate redissonLockTemplate) {
        this.redissonLockTemplate = redissonLockTemplate;
    }

    private String formatKey(String key) {
        return Optional.ofNullable(prefix).orElse("") + key;
    }

    @Pointcut("@annotation(com.zyc.redisson.anno.RedissonLock)")
    public void redissonLockPointCut() {
    }

    @Around("redissonLockPointCut()")
    public Object lockAndUnlock(ProceedingJoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        return lock(joinPoint, method);
    }

    private Object lock(ProceedingJoinPoint joinPoint, Method method) {
        RedissonLock redissonLock = AnnotationUtils.findAnnotation(method, RedissonLock.class);
        boolean tryLock = Objects.requireNonNull(redissonLock).tryLock();
        if (tryLock)
            return tryLock(joinPoint, redissonLock);
        else
            return lock(joinPoint, redissonLock);
    }

    private Object tryLock(ProceedingJoinPoint joinPoint, RedissonLock redissonLock) {
        return redissonLockTemplate.tryLock(new RedissonLockCallBack<Object>() {
            @Override
            public Object process() {
                return proceed(joinPoint);
            }

            @Override
            public String getLockName() {
                return formatKey(redissonLock.lockName());
            }
        }, redissonLock.expireTime(), redissonLock.waitTime(), redissonLock.timeUnit(), redissonLock.noLockException(), redissonLock.exceptionMessage());
    }

    private Object lock(ProceedingJoinPoint joinPoint, RedissonLock redissonLock) {
        return redissonLockTemplate.lock(new RedissonLockCallBack<Object>() {
            @Override
            public Object process() {
                return proceed(joinPoint);
            }

            @Override
            public String getLockName() {
                return formatKey(redissonLock.lockName());
            }
        }, redissonLock.expireTime(), redissonLock.timeUnit(), redissonLock.noLockException(), redissonLock.exceptionMessage());
    }

    private Object proceed(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }


}
