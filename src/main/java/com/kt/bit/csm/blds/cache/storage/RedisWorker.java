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
public class RedisWorker implements Runnable  {
    public CacheCommand command;

    public RedisWorker(CacheCommand command){
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


    private void processCommand() throws Exception {
        if( command instanceof RedisCacheCommand) command.doPut();
        else throw new Exception("Redis Cache Work can only Redis job");
    }


}
