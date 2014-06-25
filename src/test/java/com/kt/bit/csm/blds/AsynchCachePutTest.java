package com.kt.bit.csm.blds;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.kt.bit.csm.blds.cache.CacheManager;
import com.kt.bit.csm.blds.cache.storage.RedisCacheManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: toto
 * Date: 14. 6. 24
 * Time: 오후 3:56
 * To change this template use File | Settings | File Templates.
 */
public class AsynchCachePutTest extends AbstractBenchmark {

    private static String   redis_host = "127.0.0.1";
    private static int      redis_port = 6379;
    private int dataSize = 1000;
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


    @BenchmarkOptions(benchmarkRounds = 100000, warmupRounds = 2, concurrency = 5)
    @Test
    public void asynchCachePut(){
        try{
            cm.asynchSet(String.valueOf("key" + Math.random() * 10000), value, 600);
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
