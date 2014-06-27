package com.kt.bit.csm.blds;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.kt.bit.csm.blds.cache.CacheManager;
import com.kt.bit.csm.blds.cache.CachePolicy;
import com.kt.bit.csm.blds.cache.CachedResultSet;
import com.kt.bit.csm.blds.cache.storage.RedisCacheManager;
import com.kt.bit.csm.blds.cache.util.DataFormatter;
import com.kt.bit.csm.blds.cache.util.serializer.GsonDataFormatter;
import com.kt.bit.csm.blds.utility.CSMResultSet;
import com.kt.bit.csm.blds.utility.DAMParam;
import com.kt.bit.csm.blds.utility.DataAccessManager;
import com.kt.bit.csm.blds.utility.DatabaseResultSet;
import oracle.jdbc.OracleTypes;
import org.apache.commons.lang.StringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;

import static org.junit.Assert.assertTrue;

public class AsyncSimplePutTest extends AbstractBenchmark {

    private static String sales_year = "2007";
    private static String staff = "1";
    private static String spName = "pr_personal_annual";
    private static String returnParamName = "cur_resultset";
    private static final DAMParam[] param = { new DAMParam("in_year", sales_year, OracleTypes.VARCHAR),
            new DAMParam("in_no", staff, OracleTypes.VARCHAR) };

    public static Object[] expectedDataList = new Object[]{ "1    ", "A", "CEO", "CEO", "2007", 7000, 6500, 500, "ABCD1234", new Timestamp(1051963364000L), new Date(1051887600000L), null };
    public static RedisCacheManager cm;
    public static String key;
    private static DataFormatter formatter;

    @BeforeClass
    public static void makeDatabaseResultSet() throws Exception {

        // Clean up cache if exists
        cm = RedisCacheManager.getInstance();
        if (cm.exists(cm.makeKey(spName, param))) {
            cm.clear();
        }

        cm.addCachePolicy(spName, new CachePolicy());

        key = cm.makeKey(spName, param);
        formatter = GsonDataFormatter.getInstance();
    }

    @BenchmarkOptions(benchmarkRounds = 1000, warmupRounds = 2, concurrency = 10)
    @Test
    public void benchmarkTest() throws Exception {

        // Make cache
        DataAccessManager dam = new DataAccessManager();
        CSMResultSet rs = dam.executeStoredProcedureForQuery(spName, returnParamName, param, CacheManager.CacheMode.CACHE_MODE_OFF);

        assertTrue(rs instanceof DatabaseResultSet);

        long startTIme = System.currentTimeMillis();

        final CachePolicy policy = cm.getCachePolicy(spName);
        final CachedResultSet cachedResultSet = cm.makeCachedResultSet(spName, rs, policy);

        long endTime = System.currentTimeMillis();
        System.out.println(endTime+"(durataion:"+(endTime-startTIme)+")");

        if (cachedResultSet!=null) {
            cm.asynchSet(key, formatter.toJson(cachedResultSet), policy.getTimeToLive());
            long endTime2 = System.currentTimeMillis();
            System.out.println(endTime2+"(durataion:"+(endTime2-endTime)+",totalDuration:"+(endTime2-startTIme)+")");
        }

    }

    @AfterClass
    public static void removeCachedata() throws IOException {

        // Clean up cache if exists
        CacheManager cm = RedisCacheManager.getInstance();
        cm.clear();

    }

}
