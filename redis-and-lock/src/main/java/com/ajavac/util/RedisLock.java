package com.ajavac.util;

import com.ajavac.exception.LockTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Redis锁
 * Created by wyp0596 on 30/03/2017.
 */
public class RedisLock implements Lock {

    private static final Logger logger = LoggerFactory.getLogger(RedisLock.class);
    //redis存储的值,随意
    private static final String value = "Locked";
    //每次轮询获取锁的间隔(ms)
    private static final long SLEEP_TIME = 200;
    //超时时间(ms)
    private static final long TIME_OUT = 1000;
    //锁的有效期(ms)
    private static final long EXPIRE = 30000;
    //时间单位
    private static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;
    //当前锁的状态
    private volatile boolean locked = false;
    private final String key;
    private final StringRedisTemplate stringRedisTemplate;

    public RedisLock(StringRedisTemplate stringRedisTemplate, String key) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.key = key;
    }

    /**
     * 尝试预设定的超时时间(1s)内获取锁,失败则抛出异常
     *
     * @throws LockTimeoutException 超时异常
     */
    @Override
    public void lock() {
        try {
            lockInterruptibly();
        } catch (InterruptedException e) {
            logger.warn(e.getMessage(), e);
        }
    }

    /**
     * 尝试预设定的超时时间(1s)内获取锁,失败则抛出异常
     *
     * @throws InterruptedException 中断异常
     * @throws LockTimeoutException 超时异常
     */
    @Override
    public void lockInterruptibly() throws InterruptedException {
        if (!tryLock(TIME_OUT, TIME_UNIT)) {
            throw new LockTimeoutException();
        }
    }

    /**
     * 尝试获取锁
     *
     * @return 获取成功返回true, 否则返回false
     */
    @Override
    public boolean tryLock() {
        if (!locked &&
                stringRedisTemplate.opsForValue().setIfAbsent(key, value)) {
            stringRedisTemplate.expire(key, EXPIRE, TIME_UNIT);
            return locked = true;
        }
        return false;
    }

    /**
     * 尝试获取锁
     *
     * @param time 超时时长
     * @param unit 时间单位
     * @return 获取成功返回true, 否则返回false
     * @throws InterruptedException 中断异常
     */
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        long end = System.nanoTime() + unit.toNanos(time);
        while (System.nanoTime() < end) {
            if (tryLock()) {
                return true;
            }
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            Thread.sleep(SLEEP_TIME);
        }
        return false;
    }

    /**
     * 释放锁
     */
    @Override
    public void unlock() {
        if (locked) {
            stringRedisTemplate.delete(key);
            locked = false;
        }
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }
}
