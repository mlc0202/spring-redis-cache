package com.fredfu.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * User: fafu
 * Date: 13-11-13
 * Time: 下午4:32
 */
public class RedisCache implements Cache {
    private JedisPool pool;
    private String cacheName;
    private static final String CHARSET = "utf-8";

    public RedisCache(String cacheName,JedisPool pool){
        Assert.notNull(cacheName,"Cache name should not be null");
        this.pool = pool;
        this.cacheName = cacheName;
    }

    @Override
    public String getName() {
        return cacheName;
    }

    @Override
    public Object getNativeCache() {
        return pool;
    }

    @Override
    public ValueWrapper get(Object key) {
        Assert.notNull(key,"Cache key should not be null");
        Object element = null;
        Jedis cache = pool.getResource();
        try{
            byte[] belement = cache.hget(this.cacheName.getBytes(),SerializeUtil.serialize(key));
            if(belement!=null&&belement.length>0){
                element = SerializeUtil.deserialize(belement);
            }
        }finally{
            pool.returnResource(cache);
        }
        return (element != null ? new SimpleValueWrapper(element) : null);
    }

    @Override
    public void put(Object key, Object value) {
        Assert.notNull(key,"Cache key should not be null");
        Assert.notNull(value,"Cache value should not be null");
        Jedis cache = pool.getResource();
        try{
            cache.hset(this.cacheName.getBytes(),SerializeUtil.serialize(key), SerializeUtil.serialize(value));
        }finally{
            pool.returnResource(cache);
        }
    }

    @Override
    public void evict(Object key) {
        Assert.notNull(key,"Cache key should not be null");
        Jedis cache = pool.getResource();
        try{
            if(key instanceof String){
               cache.hdel(cacheName.getBytes(),SerializeUtil.serialize(key));
            }
        }finally{
            pool.returnResource(cache);
        }
    }

    @Override
    public void clear() {
        Jedis cache = pool.getResource();
        try{
            cache.del(cacheName.getBytes());
        }finally{
            pool.returnResource(cache);
        }
    }
}
