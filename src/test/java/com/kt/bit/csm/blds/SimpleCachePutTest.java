package com.kt.bit.csm.blds;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.kt.bit.csm.blds.cache.CacheCommand;
import com.kt.bit.csm.blds.cache.CacheManager;
import com.kt.bit.csm.blds.cache.storage.RedisCacheCommand;
import com.kt.bit.csm.blds.cache.storage.RedisCacheManager;
import com.kt.bit.csm.blds.cache.storage.RedisWork;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: toto
 * Date: 14. 6. 24
 * Time: 오후 3:56
 * To change this template use File | Settings | File Templates.
 */
public class SimpleCachePutTest extends AbstractBenchmark {

    private static String   redis_host = "127.0.0.1";
    private static int      redis_port = 6379;
    public static CacheManager cm = null;
    String value = "{\n" +
            "    \"id\": 1,\n" +
            "    \"name\": \"A green door\",\n" +
            "    \"price\": 12.50,\n" +
            "    \"tags\": [\"home\", \"green\"]\n" +
            "}";

    @BeforeClass
    public static void init(){
        try {

            cm = RedisCacheManager.getInstance(redis_host, redis_port);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    @BenchmarkOptions(benchmarkRounds = 300000, warmupRounds = 2, concurrency = 100)
    @Test
    public void asynchCachePut(){
        try{
            RedisCacheCommand command = new RedisCacheCommand(CacheCommand.CACHE_WORK_MODE_PUT, "key" + Math.random() * 1000000, value, 600);
//            command.doPut();

            RedisWork work = new RedisWork(command);
//            Thread t = new Thread(work);
//            t.start();
            work.processCommand();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @AfterClass
    public static void finish(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
