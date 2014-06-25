package com.kt.bit.csm.blds.cache.storage;

import com.kt.bit.csm.blds.cache.CacheCommand;

import java.util.LinkedList;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: toto
 * Date: 14. 6. 24
 * Time: 오후 1:59
 * To change this template use File | Settings | File Templates.
 */
public class CachePutQueue {
    private final int queueSize;
    private final int maxPoolSize;
    private final int minPoolSize;
    private long keepAliveTime = 5000;

    LinkedBlockingQueue queue;
    ExecutorService threadPoolExecutor = null;

    public CachePutQueue(int queueSize, int minPoolSize, int maxPoolSize){
        this.minPoolSize  =    minPoolSize;
        this. maxPoolSize   =   maxPoolSize;
        this.queueSize = queueSize;

        queue = new LinkedBlockingQueue<Runnable>(this.queueSize);

        threadPoolExecutor =
                new ThreadPoolExecutor(
                        this.minPoolSize,
                        this.maxPoolSize,
                        this.keepAliveTime,
                        TimeUnit.MILLISECONDS,
                        queue
                );
    }

    public void addCacheWork(CacheCommand command){
        System.out.println("add task : "+command.getKey());
        RedisWorker redisWorker = new RedisWorker(command);
        threadPoolExecutor.execute(redisWorker);
    }
}
