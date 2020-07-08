package com.gyy.shiro;

import com.gyy.utils.RedisUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 缓存管理器
 * @author GYY
 * @version 1.0
 * @date 2020/7/8 21:04
 */
public class RedisCacheManager implements CacheManager {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new RedisCache<>(s,redisUtils);
    }
}
