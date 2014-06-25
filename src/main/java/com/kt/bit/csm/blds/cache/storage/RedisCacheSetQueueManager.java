package com.kt.bit.csm.blds.cache.storage;

import com.kt.bit.csm.blds.cache.CacheCommand;

import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: toto
 * Date: 14. 6. 24
 * Time: 오후 1:59
 * To change this template use File | Settings | File Templates.
 */
public class RedisCacheSetQueueManager {
    private final int queueSize;
    private final int maxPoolSize;
    private final int minPoolSize;
    private long keepAliveTime = 5000;

    LinkedBlockingQueue[] queues;
    ExecutorService[] threadPoolExecutors = null;

    public RedisCacheSetQueueManager(int queueSize, int minPoolSize, int maxPoolSize, int queueNumber){
        this.minPoolSize  =    minPoolSize;
        this. maxPoolSize   =   maxPoolSize;
        this.queueSize = queueSize;

        threadPoolExecutors = new ExecutorService[queueNumber];
        queues = new LinkedBlockingQueue[queueNumber];

        for(int i = 0; i < queues.length; i++){
            queues[i] =  new LinkedBlockingQueue<Runnable>(this.queueSize);
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

    public RedisCacheSetQueueManager(int queueSize, int minPoolSize, int maxPoolSize){
        this(queueSize, minPoolSize, maxPoolSize, 50);
    }

    public void addCacheWork(CacheCommand command){
        RedisWork redisWork = new RedisWork(command);
        threadPoolExecutors[command.hashCode()%queues.length].execute(redisWork);
    }
}


class RedisCacheExceptionHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
        RedisWork work = (RedisWork)runnable;
        System.out.println("Redis work fail to put date to cache : " + work.toString());
    }
}
