package com.kt.bit.csm.blds.cache.storage;

import com.kt.bit.csm.blds.cache.CacheCommand;
import com.kt.bit.csm.blds.cache.CacheManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: toto
 * Date: 14. 6. 24
 * Time: 오후 3:15
 * To change this template use File | Settings | File Templates.
 */
public class RedisCacheCommand implements CacheCommand {
    private Jedis jedis;
    private int cacheType;
    private Object key;
    private Object value;
    private int ttl;

    public RedisCacheCommand( int commandType, Object key, Object value, int ttl ){
        this.cacheType = commandType;
        this.value = value;
        this.key = key;
        this.ttl = ttl;
    }

    public RedisCacheCommand( int commandType, Object key, Object value ){
        this.cacheType = commandType;
        this.value = value;
        this.key = key;
        this.ttl = 10;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Object getKey() {
        return key;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public void setCacheType(int cacheType) {
        this.cacheType = cacheType;
    }

    public int getCacheType() {
        return this.cacheType;
    }

    public void setJedis(Jedis jedis){
        this.jedis = jedis;
    }

    public void doPut(){
        try {
            try {
            if(jedis != null && key != null && value != null){
                if( key instanceof String)  jedis.setex((String)key, this.ttl, (String)value);
                else if( key instanceof byte[])  jedis.setex((byte[])key, this.ttl, (byte[])value);
            }
            } catch (JedisException e) {
                e.printStackTrace();
            } catch (ClassCastException e) {
                e.printStackTrace();
             }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public Object doGet(){
        return null;
    }
}
