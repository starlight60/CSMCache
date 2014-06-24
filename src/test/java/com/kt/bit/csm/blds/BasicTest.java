package com.kt.bit.csm.blds;

import com.kt.bit.csm.blds.cache.CacheManager;
import com.kt.bit.csm.blds.cache.CachePolicy;
import com.kt.bit.csm.blds.cache.CachedResultSet;
import com.kt.bit.csm.blds.cache.storage.RedisCacheManager;
import com.kt.bit.csm.blds.utility.*;
import com.kt.bit.csm.blds.utility.CSMResultSet;
import junit.framework.Assert;
import oracle.jdbc.OracleTypes;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;

public class BasicTest {

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

        Assert.assertTrue(rs instanceof DatabaseResultSet);
        rs.close();


    }

    @Test
    public void tryToGetCache() throws Exception {

        // Make cache
        DataAccessManager dam = new DataAccessManager();
        CSMResultSet rs = dam.executeStoredProcedureForQuery(spName, returnParamName, param);

        Assert.assertTrue("Result is not CachedResultSet type", rs instanceof CachedResultSet);
        Assert.assertTrue("Row count must be 1, count:"+rs.getRowCount(), rs.getRowCount()==1);

        rs.next();

        int i = 1;
        for (Object pv: expectedDataList) {
            if (pv instanceof String) {
                Assert.assertEquals(pv, rs.getString(i));
            } else if (pv instanceof Integer) {
                Assert.assertEquals(((Integer) pv).intValue(), rs.getInt(i));
            } else if (pv instanceof Date) {
                Assert.assertEquals(pv, rs.getDate(i));
            } else if (pv instanceof Long) {
                Assert.assertEquals(((Long) pv).longValue(), rs.getLong(i));
            } else if (pv instanceof byte[]) {
                Assert.assertEquals(pv, rs.getBytes(i));
            } else if (pv instanceof Timestamp) {
                Assert.assertEquals(pv, rs.getTimestamp(i));
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
