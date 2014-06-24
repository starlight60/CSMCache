package com.kt.bit.csm.blds.cache;

/**
 * Created with IntelliJ IDEA.
 * User: toto
 * Date: 14. 6. 24
 * Time: 오후 3:04
 * To change this template use File | Settings | File Templates.
 */
public interface CacheCommand {
    public static int CACHE_WORK_MODE_PUT = 1;
    public static int CACHE_WORK_MODE_GET = 2;

    public void setCacheType(int type);
    public int getCacheType();

    public void setKey(Object key);
    public Object getKey();

    public void setValue(Object value);
    public Object getValue();

    public void doPut();
    public Object doGet();
}
