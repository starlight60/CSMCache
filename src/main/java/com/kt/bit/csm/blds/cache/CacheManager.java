package com.kt.bit.csm.blds.cache;


import com.kt.bit.csm.blds.utility.CSMResultSet;
import com.kt.bit.csm.blds.utility.*;

import java.util.Map;
import java.util.Set;


public interface CacheManager {

    public static enum CacheMode { CACHE_MODE_ON, CACHE_MODE_OFF };

    public byte[] getByteData(String key);
    public String setByteData(String key, byte[] value);

    public long dbSize();

    public Set<String> keys(String pattern);

    public long incr(String key);

    public long decr(String key);

    public long del(String key);

    public String get(String key);

    public String set(String key, String value);

    public void asynchSet(String key, String value);

    public String clear();

    public boolean exists(String key);

    public boolean isCacheTarget(String spName);

    public CachedResultSet getResultSet(String key);

    public CachedResultSet saveResultSetToCache(String key, CSMResultSet result);

    public String makeKey(final String spName, final DAMParam[] param);

    public void addCachePolicy(String spName, CachePolicy policy);

    public void addCachePolicy(String spName, boolean isCacheTarget, boolean isMultiRow, int maxCount);

    public void addCachePolicy(String spName, boolean isCacheTarget, boolean isMultiRow, int maxCount, int timeToLive);

    public void delCachePolicy(String spName);

    public Map getCachePolicy();

    public boolean isCacheOn();
}
