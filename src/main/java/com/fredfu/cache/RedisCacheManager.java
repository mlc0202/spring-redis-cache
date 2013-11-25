package com.fredfu.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;
import redis.clients.jedis.JedisPool;

import java.util.Collection;
import java.util.Collections;

/**
 * User: fafu
 * Date: 13-11-13
 * Time: 下午5:16
 * This class is a cache manager implementation of spring-cache module.
 */
public class RedisCacheManager extends AbstractCacheManager {
    private JedisPool jedisPool;

    public RedisCacheManager(){

    }

    public RedisCacheManager(JedisPool jedisPool){
        this.jedisPool = jedisPool;
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        return Collections.EMPTY_LIST;
    }
    @Override
    public Cache getCache(String name) {
        Cache cache = super.getCache(name);
        if(cache != null)return cache;
        Cache cacheNew = new RedisCache(name,jedisPool);
        super.addCache(cacheNew);
        return cacheNew;
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
