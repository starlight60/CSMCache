package com.kt.bit.csm.blds;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.kt.bit.csm.blds.cache.CacheManager;
import com.kt.bit.csm.blds.cache.CachePolicy;
import com.kt.bit.csm.blds.cache.CachedResultSet;
import com.kt.bit.csm.blds.cache.storage.RedisCacheManager;
import com.kt.bit.csm.blds.utility.*;
import com.kt.bit.csm.blds.utility.serializer.GsonDataFormatter;
import oracle.jdbc.OracleTypes;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class JsonVsSerializationSerializeTest extends AbstractBenchmark {

    private static String sales_year = "2007";
    private static String staff = "1";
    private static String spName = "pr_personal_annual";
    private static String returnParamName = "cur_resultset";
    private static final DAMParam[] param = { new DAMParam("in_year", sales_year, OracleTypes.VARCHAR),
            new DAMParam("in_no", staff, OracleTypes.VARCHAR) };

    public static Object[] expectedDataList = new Object[]{ "1    ", "A", "CEO", "CEO", "2007", 7000, 6500, 500, "ABCD1234" };

    private static CachedResultSet cachedResultSet;
    private static DataFormatter formmater;

    @BeforeClass
    public static void callAndMakeCachedata() throws Exception {

        formmater = GsonDataFormatter.getInstance();

        // Clean up cache if exists
        RedisCacheManager cm = RedisCacheManager.getInstance();
        if (cm.exists(cm.makeKey(spName, param))) {
            cm.clear();
        }

        cm.addCachePolicy(spName, new CachePolicy());

        // Make cache
        DataAccessManager dam = new DataAccessManager();
        CSMResultSet rs = dam.executeStoredProcedureForQuery(spName, returnParamName, param, CacheManager.CacheMode.CACHE_MODE_OFF);

        assertTrue(rs instanceof DatabaseResultSet);

        cachedResultSet = cm.makeCachedResultSet(spName, rs, cm.getCachePolicy(spName));
        rs.close();

    }

    @BenchmarkOptions(benchmarkRounds = 100000, warmupRounds = 2)
    @Test
    public void serializeByObjectOutputStream() throws Exception {

        assertNotNull("rowData is null", cachedResultSet);

        ObjectOutputStream oos = new ObjectOutputStream(new ByteArrayOutputStream());
        oos.writeObject(cachedResultSet.getCacheRows());
        oos.close();

    }

    @BenchmarkOptions(benchmarkRounds = 100000, warmupRounds = 2)
    @Test
    public void serializeByJson() throws Exception {

        assertNotNull("rowData is null", cachedResultSet);

        formmater.toJson(cachedResultSet);

    }

    @BenchmarkOptions(benchmarkRounds = 100000, warmupRounds = 2)
    @Test
    public void serializeByObjectOutputStreamAndSaveToRedis() throws Exception {

        assertNotNull("rowData is null", cachedResultSet);

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bao);
        oos.writeObject(cachedResultSet.getCacheRows());

        RedisCacheManager cacheManager = RedisCacheManager.getInstance();
        cacheManager.setByteData("test", bao.toByteArray());
        oos.close();
    }

    @BenchmarkOptions(benchmarkRounds = 100000, warmupRounds = 2)
    @Test
    public void serializeByJsonAndSaveToRedis() throws Exception {

        assertNotNull("rowData is null", cachedResultSet);

        RedisCacheManager cacheManager = RedisCacheManager.getInstance();
        cacheManager.set("test", formmater.toJson(cachedResultSet));

    }

    @BenchmarkOptions(benchmarkRounds = 100000, warmupRounds = 2, concurrency = 10)
    @Test
    public void serializeByObjectOutputStreamConcurrent() throws Exception {

        assertNotNull("rowData is null", cachedResultSet);

        ObjectOutputStream oos = new ObjectOutputStream(new ByteArrayOutputStream());
        oos.writeObject(cachedResultSet.getCacheRows());
        oos.close();

    }

    @BenchmarkOptions(benchmarkRounds = 100000, warmupRounds = 2, concurrency = 10)
    @Test
    public void serializeByJsonConcurrent() throws Exception {

        assertNotNull("rowData is null", cachedResultSet);

        formmater.toJson(cachedResultSet);

    }

    @BenchmarkOptions(benchmarkRounds = 100000, warmupRounds = 2, concurrency = 10)
    @Test
    public void serializeByObjectOutputStreamAndSaveToRedisConcurrent() throws Exception {

        assertNotNull("rowData is null", cachedResultSet);

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bao);
        oos.writeObject(cachedResultSet.getCacheRows());

        RedisCacheManager cacheManager = RedisCacheManager.getInstance();
        cacheManager.setByteData("test", bao.toByteArray());
        oos.close();
    }

    @BenchmarkOptions(benchmarkRounds = 100000, warmupRounds = 2, concurrency = 10)
    @Test
    public void serializeByJsonAndSaveToRedisConcurrent() throws Exception {

        assertNotNull("rowData is null", cachedResultSet);

        RedisCacheManager cacheManager = RedisCacheManager.getInstance();
        cacheManager.set("test", formmater.toJson(cachedResultSet));

    }

    @AfterClass
    public static void cleanup() throws Exception {
        RedisCacheManager cacheManager = RedisCacheManager.getInstance();
        cacheManager.del("test");
    }

}
