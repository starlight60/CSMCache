package com.kt.bit.csm.blds;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.kt.bit.csm.blds.cache.CacheManager;
import com.kt.bit.csm.blds.cache.CachePolicy;
import com.kt.bit.csm.blds.cache.CacheRow;
import com.kt.bit.csm.blds.cache.CachedResultSet;
import com.kt.bit.csm.blds.cache.storage.RedisCacheManager;
import com.kt.bit.csm.blds.cache.util.DataFormatter;
import com.kt.bit.csm.blds.utility.*;
import com.kt.bit.csm.blds.utility.serializer.GsonDataFormatter;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import oracle.jdbc.OracleTypes;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class JsonVsSerializationDeserializeTest extends AbstractBenchmark {

    private static String sales_year = "2007";
    private static String staff = "1";
    private static String spName = "pr_personal_annual";
    private static String returnParamName = "cur_resultset";
    private static final DAMParam[] param = { new DAMParam("in_year", sales_year, OracleTypes.VARCHAR),
            new DAMParam("in_no", staff, OracleTypes.VARCHAR) };

    public static Object[] expectedDataList = new Object[]{ "1    ", "A", "CEO", "CEO", "2007", 7000, 6500, 500, "ABCD1234" };

    private static CachedResultSet cachedResultSet;
    private static byte[] serializedInByte;
    private static String serializedString;
    private static DataFormatter formmater;

    @BeforeClass
    public static void callAndMakeCachedata() throws Exception {

        formmater = GsonDataFormatter.getInstance();

        // Clean up cache if exists
        RedisCacheManager cm = RedisCacheManager.getInstance();
        if (cm.exists(cm.makeKey(spName, param))) {
            cm.clear();
        }

        CachePolicy policy = new CachePolicy();
        policy.setTimeToLive(1000);
        cm.addCachePolicy(spName, policy);

        // Make cache
        DataAccessManager dam = new DataAccessManager();
        CSMResultSet rs = dam.executeStoredProcedureForQuery(spName, returnParamName, param, CacheManager.CacheMode.CACHE_MODE_ON);

        assertTrue(rs instanceof CachedResultSet);

        cachedResultSet = (CachedResultSet) rs;

        // Serialize
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bao);
        oos.writeObject(cachedResultSet.getCacheRows());
        serializedInByte = bao.toByteArray();
        cm.setByteData("test", serializedInByte);

        serializedString = cm.get(cm.makeKey(spName, param));

        oos.close();
        rs.close();
    }

    @BenchmarkOptions(benchmarkRounds = 100000, warmupRounds = 2)
    @Test
    public void deserializeByObjectInputStream() throws Exception {

        assertNotNull("serializedInByte is null", serializedInByte);

        ObjectInputStream ois = new ObjectInputStream(new ByteInputStream(serializedInByte, serializedInByte.length));
        ois.readObject();
        ois.close();

    }

    @BenchmarkOptions(benchmarkRounds = 100000, warmupRounds = 2)
    @Test
    public void deserializeByJson() throws Exception {

        assertNotNull("serializedString is null", serializedString);

        formmater.fromJson(serializedString);

    }

    @BenchmarkOptions(benchmarkRounds = 100000, warmupRounds = 2)
    @Test
    public void deserializeByObjectInputStreamAndLoadFromRedis() throws Exception {

        assertNotNull("cachedResultSet is null", cachedResultSet);

        RedisCacheManager cacheManager = RedisCacheManager.getInstance();

        byte[] serialized = cacheManager.getByteData("test");

        ObjectInputStream ois = new ObjectInputStream(new ByteInputStream(serialized, serialized.length));
        Object t = ois.readObject();
        ois.close();

        assertTrue(t instanceof List);

        CachedResultSet rs = new CachedResultSet();
        rs.setCacheRows((ArrayList<CacheRow>) t);

        assertNotNull(rs);
    }

    @BenchmarkOptions(benchmarkRounds = 100000, warmupRounds = 2)
    @Test
    public void deserializeByJsonAndLoadFromRedis() throws Exception {

        assertNotNull("cachedResultSet is null", cachedResultSet);

        RedisCacheManager cacheManager = RedisCacheManager.getInstance();
        CachedResultSet rs = cacheManager.getResultSet(cacheManager.makeKey(spName, param));

        assertNotNull(rs);
    }

    @BenchmarkOptions(benchmarkRounds = 100000, warmupRounds = 2, concurrency = 10)
    @Test
    public void deserializeByObjectInputStreamConcurrent() throws Exception {

        assertNotNull("serializedInByte is null", serializedInByte);

        ObjectInputStream ois = new ObjectInputStream(new ByteInputStream(serializedInByte, serializedInByte.length));
        ois.readObject();
        ois.close();

    }

    @BenchmarkOptions(benchmarkRounds = 100000, warmupRounds = 2, concurrency = 10)
    @Test
    public void deserializeByJsonConcurrent() throws Exception {

        assertNotNull("serializedString is null", serializedString);

        formmater.fromJson(serializedString);

    }

    @BenchmarkOptions(benchmarkRounds = 100000, warmupRounds = 2, concurrency = 10)
    @Test
    public void deserializeByObjectInputStreamAndLoadFromRedisConcurrent() throws Exception {

        assertNotNull("cachedResultSet is null", cachedResultSet);

        RedisCacheManager cacheManager = RedisCacheManager.getInstance();

        byte[] serialized = cacheManager.getByteData("test");

        ObjectInputStream ois = new ObjectInputStream(new ByteInputStream(serialized, serialized.length));
        Object t = ois.readObject();
        ois.close();

        assertTrue(t instanceof List);

        CachedResultSet rs = new CachedResultSet();
        rs.setCacheRows((ArrayList<CacheRow>) t);

        assertNotNull(rs);
    }

    @BenchmarkOptions(benchmarkRounds = 100000, warmupRounds = 2, concurrency = 10)
    @Test
    public void deserializeByJsonAndLoadFromRedisConcurrent() throws Exception {

        assertNotNull("cachedResultSet is null", cachedResultSet);

        RedisCacheManager cacheManager = RedisCacheManager.getInstance();
        CachedResultSet rs = cacheManager.getResultSet(cacheManager.makeKey(spName, param));

        assertNotNull(rs);
    }

    @AfterClass
    public static void cleanup() throws Exception {
        RedisCacheManager cacheManager = RedisCacheManager.getInstance();
        cacheManager.del("test");
        cacheManager.del(cacheManager.makeKey(spName, param));
    }

}
