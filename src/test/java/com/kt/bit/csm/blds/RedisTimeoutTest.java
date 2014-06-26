package com.kt.bit.csm.blds;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.kt.bit.csm.blds.cache.CacheManager;
import com.kt.bit.csm.blds.cache.CachePolicy;
import com.kt.bit.csm.blds.cache.CachedResultSet;
import com.kt.bit.csm.blds.cache.storage.RedisCacheManager;
import com.kt.bit.csm.blds.utility.*;
import oracle.jdbc.OracleTypes;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;

import static org.junit.Assert.*;

public class RedisTimeoutTest extends AbstractBenchmark {

    private static String sales_year = "2007";
    private static String staff = "1";
    private static String spName = "pr_personal_annual";
    private static String returnParamName = "cur_resultset";
    private static final DAMParam[] param = { new DAMParam("in_year", sales_year, OracleTypes.VARCHAR),
            new DAMParam("in_no", staff, OracleTypes.VARCHAR) };

    public static Object[] expectedDataList = new Object[]{ "1    ", "A", "CEO", "CEO", "2007", 7000, 6500, 500, "ABCD1234", new Timestamp(1051963364000L), new Date(1051887600000L), null };

    private static int passedByDatabase = 0;
    private static int passedByCache = 0;

    @BeforeClass
    public static void callAndMakeCachedata() throws Exception {

        // Load basic properties
        Properties properties = PropertyManager.loadPropertyFromFile(RedisCacheManager.redisConfigFilePath, RedisCacheManager.redisConfigFileKey);
        String host = properties.getProperty("redis.host");
        int port = Integer.valueOf(properties.getProperty("redis.port"));
        int readTimeout = 5;
        int writeTimeout = 2000;

        // Clean up cache if exists
        CacheManager cm = RedisCacheManager.getInstance(host, port, readTimeout, writeTimeout);
        if (cm.exists(cm.makeKey(spName, param))) {
            cm.clear();
        }

        cm.addCachePolicy(spName, new CachePolicy());

        // Make cache
        DataAccessManager dam = new DataAccessManager();
        CSMResultSet rs = dam.executeStoredProcedureForQuery(spName, returnParamName, param);

        checkCachedRow(rs);

        rs.close();

    }

    @BenchmarkOptions(benchmarkRounds=1000000, warmupRounds = 2, concurrency = 10)
    @Test
    public void tryToGetCache() throws Exception {

        // Make cache
        DataAccessManager dam = new DataAccessManager();
        CSMResultSet rs = dam.executeStoredProcedureForQuery(spName, returnParamName, param, CacheManager.CacheMode.CACHE_READ_ONLY);

        checkCachedRow(rs);

        rs.close();
    }

    public static void checkCachedRow(CSMResultSet rs) throws SQLException {
        if (rs instanceof CachedResultSet) passedByCache++;
        if (rs instanceof DatabaseResultSet) passedByDatabase++;

        rs.next();

        int i = 1;
        for (Object pv: expectedDataList) {
            if (pv instanceof String) {
                assertEquals(pv, rs.getString(i));
            } else if (pv instanceof Integer) {
                assertEquals(pv, rs.getInt(i));
            } else if (pv instanceof Date) {
                assertEquals(pv, rs.getDate(i));
            } else if (pv instanceof Long) {
                assertEquals(pv, rs.getLong(i));
            } else if (pv instanceof byte[]) {
                assertEquals(pv, rs.getBytes(i));
            } else if (pv instanceof Timestamp) {
                assertEquals(pv, rs.getTimestamp(i));
            } else {
                assertNull(pv);
            }

            i++;
        }
    }

    @AfterClass
    public static void removeCachedata() throws IOException {

        // Clean up cache if exists
        CacheManager cm = RedisCacheManager.getInstance();
        if (cm.exists(cm.makeKey(spName, param))) {
            cm.clear();
        }

        System.out.println("Total:"+(passedByCache+passedByDatabase));
        System.out.println("passedByDatabase:"+passedByDatabase);
        System.out.println("passedByCache:"+passedByCache);
    }

}
