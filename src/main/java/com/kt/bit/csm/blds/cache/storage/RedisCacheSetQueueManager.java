package com.kt.bit.csm.blds.cache.storage;

import com.kt.bit.csm.blds.cache.CacheCommand;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: toto
 * Date: 14. 6. 24
 * Time: 오후 1:59
 * To change this template use File | Settings | File Templates.
 */
public class RedisCacheSetQueueManager {
    private final int bufferSize;
    private final int maxPoolSize;
    private final int minPoolSize;
    private final int queueCount;
    private long keepAliveTime = 5000;
    private JedisPool jedisPool;

    LinkedBlockingQueue[] queues;
    ExecutorService[] threadPoolExecutors = null;

    public RedisCacheSetQueueManager(int bufferSize, int minPoolSize, int maxPoolSize, int queueCount, JedisPool jedisPool){
        this.minPoolSize  =    minPoolSize;
        this. maxPoolSize   =   maxPoolSize;
        this.bufferSize = bufferSize;
        this.queueCount = queueCount;
        this.jedisPool = jedisPool;

        threadPoolExecutors = new ExecutorService[this.queueCount];
        queues = new LinkedBlockingQueue[this.queueCount];

        for(int i = 0; i < queues.length; i++){
            queues[i] =  new LinkedBlockingQueue<Runnable>(this.bufferSize);
        }

        for(int i = 0; i < threadPoolExecutors.length; i++){
            threadPoolExecutors[i] =  new ThreadPoolExecutor(
                    this.minPoolSize,
                    this.maxPoolSize,
                    this.keepAliveTime,
                    TimeUnit.MILLISECONDS,
                    queues[i],
                    new RedisCacheExceptionHandler()
            );
        }
    }

    public void addCacheWork(CacheCommand command){
        RedisWork redisWork = new RedisWork(command, this.jedisPool);
        threadPoolExecutors[command.hashCode()%queues.length].execute(redisWork);
    }
}


class RedisCacheExceptionHandler implements RejectedExecutionHandler {

    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
        RedisWork work = (RedisWork)runnable;
        System.out.println("Redis work fail to put date to cache : " + work.toString());
    }
}
