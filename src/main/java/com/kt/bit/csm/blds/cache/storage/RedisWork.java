package com.kt.bit.csm.blds.cache.storage;

import com.kt.bit.csm.blds.cache.CacheCommand;
import redis.clients.jedis.Jedis;

/**
 * Created with IntelliJ IDEA.
 * User: toto
 * Date: 14. 6. 24
 * Time: 오후 1:54
 * To change this template use File | Settings | File Templates.
 */
public class RedisWork implements Runnable  {
    public CacheCommand command;

    public RedisWork(CacheCommand command){
        this.command = command;
    }


    @Override
    public void run() {
        try {
            processCommand();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void processCommand() throws Exception {
        if( command instanceof RedisCacheCommand) command.doPut();
        else throw new Exception("Redis Cache Work can only Redis job");
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