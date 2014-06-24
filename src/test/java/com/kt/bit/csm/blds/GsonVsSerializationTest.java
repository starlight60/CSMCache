package com.kt.bit.csm.blds;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.google.gson.Gson;
import com.kt.bit.csm.blds.cache.CacheManager;
import com.kt.bit.csm.blds.cache.CachePolicy;
import com.kt.bit.csm.blds.cache.storage.RedisCacheManager;
import com.kt.bit.csm.blds.utility.CSMResultSet;
import com.kt.bit.csm.blds.utility.DAMParam;
import com.kt.bit.csm.blds.utility.DataAccessManager;
import com.kt.bit.csm.blds.utility.DatabaseResultSet;
import oracle.jdbc.OracleTypes;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class GsonVsSerializationTest extends AbstractBenchmark {

    private static String sales_year = "2007";
    private static String staff = "1";
    private static String spName = "pr_personal_annual";
    private static String returnParamName = "cur_resultset";
    private static final DAMParam[] param = { new DAMParam("in_year", sales_year, OracleTypes.VARCHAR),
            new DAMParam("in_no", staff, OracleTypes.VARCHAR) };

    public static Object[] expectedDataList = new Object[]{ "1    ", "A", "CEO", "CEO", "2007", 7000, 6500, 500, "ABCD1234" };

    private static List<Object> rowData;

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
        CSMResultSet rs = dam.executeStoredProcedureForQuery(spName, returnParamName, param, CacheManager.CacheMode.CACHE_MODE_OFF);

        assertTrue(rs instanceof DatabaseResultSet);

        rs.next();

        int i = 1;
        rowData = new ArrayList<Object>();
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
            rowData.add(pv);
            i++;
        }

        rs.close();

    }

    @BenchmarkOptions(benchmarkRounds = 100000, warmupRounds = 2)
         @Test
         public void serializeByObjectOutputStream() throws Exception {

        assertNotNull("rowData is null", rowData);

        ObjectOutputStream oos = new ObjectOutputStream(new ByteArrayOutputStream());
        oos.writeObject(rowData);
        oos.close();

    }

    @BenchmarkOptions(benchmarkRounds = 100000, warmupRounds = 2)
    @Test
    public void serializeByGson() throws Exception {

        assertNotNull("rowData is null", rowData);

        Gson gson = new Gson();
        gson.toJson(rowData);

    }

    @BenchmarkOptions(benchmarkRounds = 100000, warmupRounds = 2)
    @Test
    public void serializeByObjectOutputStreamAndSaveToRedis() throws Exception {

        assertNotNull("rowData is null", rowData);

        ObjectOutputStream oos = new ObjectOutputStream(new ByteArrayOutputStream());
        oos.writeObject(rowData);
        oos.close();

    }

    @BenchmarkOptions(benchmarkRounds = 100000, warmupRounds = 2)
    @Test
    public void serializeByGsonAndSaveToRedis() throws Exception {

        assertNotNull("rowData is null", rowData);

        Gson gson = new Gson();
        gson.toJson(rowData);

    }

}
