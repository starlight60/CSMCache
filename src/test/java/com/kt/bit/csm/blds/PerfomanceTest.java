package com.kt.bit.csm.blds;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.kt.bit.csm.blds.cache.CacheManager;
import com.kt.bit.csm.blds.cache.CachePolicy;
import com.kt.bit.csm.blds.cache.CachedResultSet;
import com.kt.bit.csm.blds.cache.storage.RedisCacheManager;
import com.kt.bit.csm.blds.utility.CSMResultSet;
import com.kt.bit.csm.blds.utility.DAMParam;
import com.kt.bit.csm.blds.utility.DataAccessManager;
import com.kt.bit.csm.blds.utility.DatabaseResultSet;
import oracle.jdbc.OracleTypes;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PerfomanceTest extends AbstractBenchmark {

    private static String sales_year = "2007";
    private static String staff = "1";
    private static String spName = "pr_personal_annual";
    private static String returnParamName = "cur_resultset";
    private static final DAMParam[] param = { new DAMParam("in_year", sales_year, OracleTypes.VARCHAR),
            new DAMParam("in_no", staff, OracleTypes.VARCHAR) };

    public static Object[] expectedDataList = new Object[]{ "1    ", "A", "CEO", "CEO", "2007", 7000, 6500, 500 };

    @BeforeClass
    public static void callAndMakeCachedata() throws Exception {
        // Clean up cache if exists
        CacheManager cm = RedisCacheManager.getInstance();
        if (cm.exists(cm.makeKey(spName, param))) {
            cm.clear();
        }

        cm.addCachePolicy("pr_personal_annual", new CachePolicy());
        cm.addCachePolicy("pr_personal_annual2", new CachePolicy());

        // Make cache
        DataAccessManager dam = new DataAccessManager();
        CSMResultSet rs = dam.executeStoredProcedureForQuery(spName, returnParamName, param);

        assertTrue(rs instanceof DatabaseResultSet);
        rs.close();

    }

    @BenchmarkOptions(benchmarkRounds=1000, warmupRounds=2)
    @Test
    public void testGetFromDatabaseNormal() throws Exception {
        _doDatabaseTest();
    }

    @BenchmarkOptions(benchmarkRounds=1000, warmupRounds=2, concurrency = 10)
    @Test
    public void testGetFromDatabaseConcurrent() throws Exception {
        _doDatabaseTest();
    }

    private void _doDatabaseTest() throws Exception {
        // Make cache
        DataAccessManager dam = new DataAccessManager();
        CSMResultSet rs = dam.executeStoredProcedureForQuery(spName, returnParamName, param, CacheManager.CacheMode.CACHE_MODE_OFF);

        assertTrue("Result is not CachedResultSet type", rs instanceof DatabaseResultSet);

        rs.next();

        int i = 1;
        for (Object pv: expectedDataList) {
            if (pv instanceof String) {
                assertEquals(pv, rs.getString(i));
            } else if (pv instanceof Integer) {
                assertEquals(((Integer) pv).intValue(), rs.getInt(i));
            } else if (pv instanceof Date) {
                assertEquals(pv, rs.getDate(i));
            } else if (pv instanceof Long) {
                assertEquals(((Long) pv).longValue(), rs.getLong(i));
            } else if (pv instanceof byte[]) {
                assertEquals(pv, rs.getBytes(i));
            } else if (pv instanceof Timestamp) {
                assertEquals(pv, rs.getTimestamp(i));
            }
            i++;
        }

        rs.close();
    }

    @BenchmarkOptions(benchmarkRounds=1000, warmupRounds=2)
    @Test
    public void testGetFromCacheNormal() throws Exception {

        _doCacheTest();

    }

    @BenchmarkOptions(benchmarkRounds=1000, warmupRounds=2, concurrency = 10)
    @Test
    public void testGetFromCacheConcurrent() throws Exception {

        _doCacheTest();

    }

    private void _doCacheTest() throws Exception {
        // Make cache
        DataAccessManager dam = new DataAccessManager();
        CSMResultSet rs = dam.executeStoredProcedureForQuery(spName, returnParamName, param, CacheManager.CacheMode.CACHE_MODE_ON);

        assertTrue("Result is not CachedResultSet type", rs instanceof CachedResultSet);
        assertTrue("Row count must be 1, count:" + rs.getRowCount(), rs.getRowCount() == 1);

        rs.next();

        int i = 1;
        for (Object pv: expectedDataList) {
            if (pv instanceof String) {
                assertEquals(pv, rs.getString(i));
            } else if (pv instanceof Integer) {
                assertEquals(((Integer) pv).intValue(), rs.getInt(i));
            } else if (pv instanceof Date) {
                assertEquals(pv, rs.getDate(i));
            } else if (pv instanceof Long) {
                assertEquals(((Long) pv).longValue(), rs.getLong(i));
            } else if (pv instanceof byte[]) {
                assertEquals(pv, rs.getBytes(i));
            } else if (pv instanceof Timestamp) {
                assertEquals(pv, rs.getTimestamp(i));
            }
            i++;
        }

        rs.close();
    }

    @AfterClass
    public static void removeCachedata() throws IOException {

        // Clean up cache if exists
        CacheManager cm = RedisCacheManager.getInstance();
        if (cm.exists(cm.makeKey(spName, param))) {
            cm.clear();
        }
    }

}
