package com.kt.bit.csm.blds;

import com.kt.bit.csm.blds.cache.CacheManager;
import com.kt.bit.csm.blds.cache.storage.RedisCacheManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: toto
 * Date: 14. 6. 24
 * Time: 오후 3:56
 * To change this template use File | Settings | File Templates.
 */
public class AsynchCachePutTest {

    private String   redis_host = "127.0.0.1";
    private int      redis_port = 6379;
    private int dataSize = 5;


    @Test
    public void asynchCachePut(){


        try {
            CacheManager cm = RedisCacheManager.getInstance(redis_host, redis_port);
            String key = "key";
            String value = "{\n" +
                    "    \"id\": 1,\n" +
                    "    \"name\": \"A green door\",\n" +
                    "    \"price\": 12.50,\n" +
                    "    \"tags\": [\"home\", \"green\"]\n" +
                    "}";

            for( int i = 0; i< dataSize; i++){
                key = String.valueOf(key+i);
                cm.asynchSet(key, value);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
