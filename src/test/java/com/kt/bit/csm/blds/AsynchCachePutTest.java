package com.kt.bit.csm.blds;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.kt.bit.csm.blds.cache.CacheManager;
import com.kt.bit.csm.blds.cache.storage.RedisCacheManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: toto
 * Date: 14. 6. 24
 * Time: 오후 3:56
 * To change this template use File | Settings | File Templates.
 */
public class AsynchCachePutTest extends AbstractBenchmark {

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
            cm = RedisCacheManager.getInstance();
            long time = System.currentTimeMillis();

            SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

            String str = dayTime.format(new Date(time));
            System.out.println("start : " + str);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


//    @BenchmarkOptions(benchmarkRounds = 1000000, warmupRounds = 2, concurrency = 2)
    @Test
    public void asynchCachePut(){
        try{
            cm.asynchSet(String.valueOf("key" + Math.random() * 10000), value, 600);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @AfterClass
    public static void finish(){
        try {
            long time = System.currentTimeMillis();

            SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

            String str = dayTime.format(new Date(time));
            System.out.println("finish : " + str);
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
