package com.gyy.shiro;

import com.alibaba.fastjson.JSON;
import com.gyy.utils.JwtTokenUtil;
import com.gyy.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * shiro缓存
 * @author GYY
 * @version 1.0
 * @date 2020/7/8 21:00
 */
@Slf4j
public class RedisCache<K,V> implements Cache<K,V> {

    private final static String PREFIX = "shiro-cache:";
    private String cacheKey;
    private long expire = 24;
    private RedisUtils redisUtils;

    public RedisCache(String name, RedisUtils redisUtils) {
        this.cacheKey = PREFIX + name + ":";
        this.redisUtils = redisUtils;
    }

    @Override
    public V get(K key) throws CacheException {
        log.info("Shiro从缓存中获取数据KEY值[{}]", key);
        if (key == null) {
            return null;
        }
        try {
            String redisCacheKey = getRedisCacheKey(key);
            Object rawValue = redisUtils.get(redisCacheKey);
            if (rawValue == null) {
                return null;
            }
            SimpleAuthorizationInfo simpleAuthenticationInfo = JSON.parseObject(rawValue.toString(), SimpleAuthorizationInfo.class);
            V value = (V) simpleAuthenticationInfo;
            return value;
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    @Override
    public V put(K key, V value) throws CacheException {
        log.info("put key [{}]", key);
        if (key == null) {
            log.warn("Saving a null key is meaningless, return value directly without call Redis.");
            return value;
        }
        try {
            String redisCacheKey = getRedisCacheKey(key);
            redisUtils.set(redisCacheKey, value != null ? value : null, expire, TimeUnit.HOURS);
            return value;
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    @Override
    public V remove(K key) throws CacheException {
        log.info("remove key [{}]", key);
        if (key == null) {
            return null;
        }
        try {
            String redisCacheKey = getRedisCacheKey(key);
            Object rawValue = redisUtils.get(redisCacheKey);
            V previous = (V) rawValue;
            redisUtils.delete(redisCacheKey);
            return previous;
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    @Override
    public void clear() throws CacheException {
        log.debug("clear cache");
        Set<String> keys = null;
        try {
            keys = redisUtils.keys(this.cacheKey + "*");
        } catch (Exception e) {
            log.error("get keys error", e);
        }
        if (keys == null || keys.size() == 0) {
            return;
        }
        for (String key : keys) {
            redisUtils.delete(key);
        }
    }

    @Override
    public int size() {
        int result = 0;
        try {
            result = redisUtils.keys(this.cacheKey + "*").size();
        } catch (Exception e) {
            log.error("get keys error", e);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<K> keys() {
        Set<String> keys = null;
        try {
            keys = redisUtils.keys(this.cacheKey + "*");
        } catch (Exception e) {
            log.error("get keys error", e);
            return Collections.emptySet();
        }
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptySet();
        }
        Set<K> convertedKeys = new HashSet<>();
        for (String key : keys) {
            try {
                convertedKeys.add((K) key);
            } catch (Exception e) {
                log.error("deserialize keys error", e);
            }
        }
        return convertedKeys;
    }

    @Override
    public Collection<V> values() {
        Set<String> keys = null;
        try {
            keys = redisUtils.keys(this.cacheKey + "*");
        } catch (Exception e) {
            log.error("get values error", e);
            return Collections.emptySet();
        }
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptySet();
        }
        List<V> values = new ArrayList<V>(keys.size());
        for (String key : keys) {
            V value = null;
            try {
                value = (V) redisUtils.get(key);
            } catch (Exception e) {
                log.error("deserialize values= error", e);
            }
            if (value != null) {
                values.add(value);
            }
        }
        return Collections.unmodifiableList(values);
    }

    private String getRedisCacheKey(K key) {
        if (null == key) {
            return null;
        } else {
            return this.cacheKey + JwtTokenUtil.getUserId(key.toString());
        }
    }
}
