package com.kt.bit.csm.blds.cache.storage;

import com.kt.bit.csm.blds.cache.CacheCommand;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisWork implements Runnable  {
    public CacheCommand command;
    public JedisPool jedisPool;

    public RedisWork(CacheCommand command){
        this.command = command;
    }

    public RedisWork(CacheCommand command, JedisPool jedisPool){
        this.command = command;
        this.jedisPool = jedisPool;
    }

    public void run() {
        try {
            processCommand();
        } catch (Exception e) {
            // TODO: We should generate the log for this situation
            e.printStackTrace();
        }
    }


    public void processCommand() throws Exception {
        Jedis jedis = null;
        RedisCacheManager rcm = RedisCacheManager.getInstance();
        if( rcm != null && rcm.isCacheOn() && rcm.isServerStatusOn()){
            try{
                if( command instanceof RedisCacheCommand){
                    RedisCacheCommand redisCacheCommand = (RedisCacheCommand)command;
                    jedis = this.borrow();
                    redisCacheCommand.setJedis(jedis);
                    command.doPut();
                }
                else throw new Exception("Redis Cache Work can only Redis job");
            }finally {
                if(jedis != null) this.revert(jedis);
            }
        }
    }

    private Jedis borrow(){
        return this.jedisPool.getResource();
    }

    private void revert(Jedis jedis){
        this.jedisPool.returnResource(jedis);
    }

    public String toString(){
        Object key = command.getKey();
        int type = command.getCacheType();
        StringBuffer returnStr = new StringBuffer();

        if( type == CacheCommand.CACHE_WORK_MODE_PUT ) returnStr.append("TYPE : CACHE_PUT");
        else if( type == CacheCommand.CACHE_WORK_MODE_GET ) returnStr.append("TYPE : CACHE_GET");
        else returnStr.append("TYPE : UNKNOWN");

        returnStr.append(", key = " + key);
        return returnStr.toString();
    }


}
