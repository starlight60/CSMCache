package com.kt.bit.csm.blds.cache.storage;


import com.github.jedis.lock.JedisLock;
import com.kt.bit.csm.blds.cache.CacheColumn;
import com.kt.bit.csm.blds.cache.CacheFetchConstants;
import com.kt.bit.csm.blds.cache.CacheManager;
import com.kt.bit.csm.blds.cache.CachePolicy;
import com.kt.bit.csm.blds.cache.CachedResultSet;
import com.kt.bit.csm.blds.cache.config.CacheConfigManager;
import com.kt.bit.csm.blds.utility.*;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;


public class RedisCacheManager implements CacheManager {
    private String  host;
    private int     port;
    private boolean isTest = false;
    
    private AtomicBoolean cacheOn = new AtomicBoolean(false);
    public ConcurrentHashMap cacheTargetList = null;
    public static RedisCacheManager instance = null;
    public static final String configFilePath = "redis-config.properties";     // The file should be within classpath
    public static final String cacheConfigFilePath = "cache-config.properties";     // The file should be within classpath
    public static final String cachePolicyFilePath = "cache-policy.properties";     // The file should be within classpath

    /**
     * for Test
     * @return
     * @throws IOException
     */
    public void setTestFlag(boolean flag) {
    	this.isTest = flag;
    }
    
    public static RedisCacheManager getInstance() throws IOException {
        if(instance == null){
            synchronized (RedisCacheManager.class) {
                instance = new RedisCacheManager();
            }
        }

        return instance;
    }

    public static RedisCacheManager getInstance(final String host, final int port) throws IOException {
        if(instance == null){
            synchronized (RedisCacheManager.class) {
                instance = new RedisCacheManager(host, port);
            }
        }

        return instance;
    }

    private JedisPool pool;

    private RedisCacheManager() throws IOException, NumberFormatException {

        // Load basic properties
        Properties properties = new Properties();
        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(configFilePath));
        this.host = properties.getProperty("redis.host");
        this.port = Integer.valueOf(properties.getProperty("redis.port"));

        // Load Cache Config, Policy Properties Files and Monitoring
        if (!isTest) {
    		CacheConfigManager manager = CacheConfigManager.getInstance();
    		manager.setPropertyChangeListener("cache-config", cacheConfigFilePath, 60000);
    		manager.setPropertyChangeListener("cache-policy", cachePolicyFilePath, 60000);
        }
        
        pool = new JedisPool(new JedisPoolConfig(), this.host, this.port);

        cacheTargetList = new ConcurrentHashMap();
    }

    private RedisCacheManager(String host, int port) {
        this.host = host;
        this.port = port;

        pool = new JedisPool(new JedisPoolConfig(), this.host, this.port);
        cacheTargetList = new ConcurrentHashMap();
    }

    /**
     * Added Date : 2014.06.20
     * ---------------------------
     * get info by key
     *
     * @param key
     * @return
     */
    public byte[] getByteData(String key) {

        Jedis jedis = borrow();
        try {
            return jedis.get(key.getBytes());
        } finally {
            revert(jedis);
        }

    }

    /**
     * Added Date : 2014.08.20
     * ---------------------------
     * set info by key (insert, update)
     *
     * @param key
     * @param value
     * @return
     */
    public String setByteData(String key, byte[] value) {

        Jedis jedis = borrow();
        try {
            return jedis.set(key.getBytes(), value);
        } finally {
            revert(jedis);
        }

    }

    /**
     * Added Date : 2014.06.17
     * ---------------------------
     * Redis Ping command for health check.
     *
     * @return
     */
    public String ping() {

        Jedis jedis = borrow();
        try {
            return jedis.ping();
        } finally {
            revert(jedis);
        }

    }

    /**
     * Added Date : 2014.06.16
     * ---------------------------
     * Write changed configurations to local file.
     * This is executed Lua script at redis server side.
     *
     * @return : Lua Script SHA1 value
     */
    public String configRewrite() {

        Jedis jedis = borrow();
        try {
            return jedis.scriptLoad("return redis.call('config', 'rewrite')");
        } finally {
            revert(jedis);
        }

    }

    /**
     * Added Date : 2014.06.16
     * ---------------------------
     * Check the configuration rewrite lua script execution result.
     *
     * @param sha1 : Script SHA1 value
     * @return : result message. if success, "OK" returned.
     */
    public Object configRewriteResult(String sha1) {

        Jedis jedis = borrow();
        try {
            return jedis.evalsha(sha1, 0);
        } finally {
            revert(jedis);
        }

    }

    /**
     * Added Date : 2014.06.16
     * ---------------------------
     * Change the replication setting.
     * Set current slave to master.
     *
     * @return
     */
    public String slaveOfNoOne() {

        Jedis jedis = borrow();
        try {
            return jedis.slaveofNoOne();
        } finally {
            revert(jedis);
        }

    }

    /**
     * Added Date : 2014.06.16
     * ---------------------------
     * Change the replication setting.
     * This is the same as SLAVEOF command.
     *
     * @param host : Master Node IP Address
     * @param port : Master Node Port
     * @return
     */
    public String slaveOf(String host, int port) {

        Jedis jedis = borrow();


        try {
            return jedis.slaveof(host, port);
        } finally {
            revert(jedis);
        }

    }

    /**
     * Added Date : 2014.06.12
     * ---------------------------
     *
     * @return : all key count
     */
    public long dbSize() {

        Jedis jedis = borrow();
        try {
            return jedis.dbSize();
        } finally {
            revert(jedis);
        }

    }

    /**
     * Added Date : 2014.06.09
     * ---------------------------
     * Redis Configuration Setter
     *
     * @param parameter : config parameter
     * @param value : config value
     * @return
     */
    public String configSet(String parameter, String value) {

        Jedis jedis = borrow();
        try {
            return jedis.configSet(parameter, value);
        } finally {
            revert(jedis);
        }

    }

    /**
     * Added Date : 2014.06.09
     * ---------------------------
     * Get all configuration
     *
     * @param pattern
     * @return
     */
    public List<String> configGet(String pattern) {

        Jedis jedis = borrow();
        try {

            if (pattern == null || pattern.isEmpty()) {
                return jedis.configGet("*");
            }
            else {
                return jedis.configGet(pattern);
            }

        } finally {
            revert(jedis);
        }

    }

    /**
     * Added Date : 2014.05.27
     * ---------------------------
     * Get all keys by pattern
     *
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {

        Jedis jedis = borrow();
        try {
            return jedis.keys(pattern);
        } finally {
            revert(jedis);
        }

    }

    /**
     * Added Date : 2014.05.20
     * ---------------------------
     * auto increment by key
     *
     * @param key
     * @return
     */
    public long incr(String key) {

        Jedis jedis = borrow();
        long result = 0L;
        JedisLock lock = new JedisLock(jedis,  "incr_lock:" + key, 10000, 60000);

        try {
            lock.acquire();
            result = jedis.incr(key);
        } catch (InterruptedException e) {
            //logger.debug(e.getMessage());
            e.printStackTrace();
        } finally {
            lock.release();
            revert(jedis);
        }

        return result;

    }

    /**
     * Added Date : 2014.05.20
     * ---------------------------
     * auto decrement by key
     *
     * @param key
     * @return
     */
    public long decr(String key) {

        Jedis jedis = borrow();
        long result = 0L;
        JedisLock lock = new JedisLock(jedis, "decr_lock:" + key, 10000, 60000);

        try {
            lock.acquire();
            result = jedis.decr(key);
        } catch (InterruptedException e) {
            //logger.debug(e.getMessage());
            e.printStackTrace();
        } finally {
            lock.release();
            revert(jedis);
        }

        return result;

    }

    /**
     * Added Date : 2014.05.20
     * ---------------------------
     * delete info by key
     *
     * @param key
     * @return
     */
    public long del(String key) {

        Jedis jedis = borrow();
        long result = 0L;
        JedisLock lock = new JedisLock(jedis, "del_lock:" + key, 10000, 60000);

        try {
            lock.acquire();
            result = jedis.del(key);
        } catch (InterruptedException e) {
//            logger.debug(e.getMessage());
            e.printStackTrace();
        } finally {
            revert(jedis);
        }

        return result;

    }

    /**
     * Added Date : 2014.05.20
     * ---------------------------
     * get info by key
     *
     * @param key
     * @return
     */
    public String get(String key) {

        Jedis jedis = borrow();
        try {
            return jedis.get(key);
        } finally {
            revert(jedis);
        }

    }

    /**
     * Added Date : 2014.05.20
     * ---------------------------
     * set info by key (insert, update)
     *
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value) {

        Jedis jedis = borrow();
        try {
            return jedis.set(key, value);
        } finally {
            revert(jedis);
        }

    }

    public String set(String key, String value, int ttl) {

        Jedis jedis = borrow();
        try {
            return jedis.setex(key, ttl ,value);
        } finally {
            revert(jedis);
        }

    }

    /**
     * Added Date : 2014.05.20
     * ---------------------------
     * remove all data in db.
     * If use this method, critical.
     * Be careful.
     *
     * @return
     */
    public String clear() {

        Jedis jedis = borrow();
        try {
            return jedis.flushDB();
        } finally {
            revert(jedis);
        }

    }

    /**
     * Added Date : 2014.05.20
     * ---------------------------
     * Check the key exists or not.
     *
     * @param key
     * @return
     */
    public boolean exists(String key) {

        Jedis jedis = borrow();
        try {
            return jedis.exists(key);
        } finally {
            revert(jedis);
        }

    }

    /**
     * Added Date : 2014.05.20
     * ---------------------------
     * get all redis server info for monitoring
     *
     * @return
     */
    public String info() {

        Jedis jedis = borrow();
        try {
            return jedis.info();
        } finally {
            revert(jedis);
        }

    }

    /**
     * Added Date : 2014.05.20
     * ---------------------------
     * get redis server info for monitoring of given section
     *
     * @param section
     * @return
     */
    public String info(String section) {

        Jedis jedis = borrow();
        try {
            return jedis.info(section);
        } finally {
            revert(jedis);
        }

    }



    /**
     * @TO-DO
     * @param spName
     * @return
     */
    public boolean isCacheTarget(String spName){
        CachePolicy policy = (CachePolicy)cacheTargetList.get(spName);
        if(policy != null && policy.isCacheTarget() )
            return true;
        else
            return false;
    }

    public CachedResultSet getResultSet(String key){
        CachedResultSet resultSet = null;
        if( exists(key) ){
            return DataFormmater.fromJson(get(key));
        }

        return resultSet;
    }

    public CachedResultSet saveResultSetToCache(String key, CSMResultSet result){

        assert result instanceof DatabaseResultSet;

        DatabaseResultSet resultInCSM = (DatabaseResultSet) result;

        try {
            ResultSetMetaData meta = result.getMetaData();
            CachedResultSet cachedResultSet = new CachedResultSet();
            int colCount = meta.getColumnCount();
            int rowCount = 0;

            while( result.next() ){
                CacheColumn[] cacheColumns = new CacheColumn[colCount];
                for(int i=1; i <= colCount;i++){
                    cacheColumns[i-1] = new CacheColumn(meta.getColumnName(i), meta.getColumnType(i), resultInCSM.getObject(i));
                }
                cachedResultSet.addRow(cacheColumns, rowCount);
                rowCount++;
            }

            this.set(key, DataFormmater.toJson(cachedResultSet));

            return cachedResultSet;
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public CachedResultSet saveResultSetToCache(String spName, String key, CSMResultSet result){

        assert result instanceof DatabaseResultSet;

        DatabaseResultSet resultInCSM = (DatabaseResultSet) result;

        if (spName == null || spName.equals("")) {
            saveResultSetToCache(key, resultInCSM);
        }

        try {
            ResultSetMetaData meta = result.getMetaData();
            CachedResultSet cachedResultSet = new CachedResultSet();
            int colCount = meta.getColumnCount();
            int rowCount = 0;

            /**
             * TO-DO : 2014.06.23
             * ---------------------------
             * Add multi-rows select according to the Policy of Stored Procedure Name
             * If isMultiRow == true and MaxCount == -1, that means read full rows.
             * If isMultiRow == true and MaxCount is any number, that means read rows to MaxCount.
             * If isNultiRow == false, that means only one row.
             *
             */
            int totalCount = 0;
            CachePolicy policy = (CachePolicy) cacheTargetList.get(spName);

            if (policy.isMultiRow()) {

                if (policy.isMultiRow() && policy.getMaxCount() == 0) {
                    throw new IllegalArgumentException(spName + " Fetch Size Policy is invalid.");
                }

                if (policy.getFetchSize().equals(CacheFetchConstants.FETCH_SIZE)) {
                    totalCount = result.getFetchSize();
                }
                else if (policy.getFetchSize().equals(CacheFetchConstants.FETCH_ALL)) {
                    totalCount = -1;
                }
                else {
                    totalCount = 1;
                }

            }
            else {
                totalCount = 1;
            }

            while( result.next() ){
                CacheColumn[] cacheColumns = new CacheColumn[colCount];
                for(int i=1; i <= colCount;i++){
                    cacheColumns[i-1] = new CacheColumn(meta.getColumnName(i), meta.getColumnType(i), resultInCSM.getObject(i));
                }
                cachedResultSet.addRow(cacheColumns, rowCount);
                rowCount++;

                if ((totalCount != -1) && (rowCount == totalCount)) {
                    break;
                }
            }

            this.set(key, DataFormmater.toJson(cachedResultSet), policy.getTimeToLive());

            return cachedResultSet;

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Added Date : 2014.06.09
     * ---------------------------
     * migrate given key to target redis server
     *
     * @param host
     * @param port
     * @param key
     * @return
     */
    public String migrate(String host, int port, String key) {

        Jedis jedis = borrow();
        try {
            return jedis.migrate(host, port, key, 0, 100000);
        } finally {
            revert(jedis);
        }

    }

    /**
     * Modified Date : 2014.06.12
     * ---------------------------
     * Added Date : 2014.06.09
     * ---------------------------
     * migration all keys in given slot to target redis server.
     * redis cluster is working, dev mode.
     *
     * @param host
     * @param port
     * @param slot
     * @return
     */
    @Deprecated
    public String migrate(String host, int port, int slot) {

        Jedis jedis = borrow();
        String result = "";
        try {

            List<String> keys = jedis.clusterGetKeysInSlot(slot, 1000);

            for (String key : keys) {
                result = migrate(host, port, key);
            }

        } finally {
            revert(jedis);
        }

        return result;

    }

    /**
     *
     */
    public void destory() {

        pool.destroy();

    }

    /**
     *
     * @return
     */
    public Jedis borrow() {

        return pool.getResource();

    }

    /**
     *
     * @param jedis
     */
    public void revert(Jedis jedis) {

        pool.returnResource(jedis);

    }

    @Deprecated
    public byte[] bGetJ(String key) {

        Jedis jedis = borrow();
        try {
            byte[] value = jedis.get(key.getBytes());
            if (value != null) {
                return value;
            }
            return null;
        } finally {
            revert(jedis);
        }

    }

    @Deprecated
    public String bSetJ(String key, byte[] value) {

        Jedis jedis = borrow();
        try {
            return jedis.set(key.getBytes(), value);
        } finally {
            revert(jedis);
        }

    }

    @Deprecated
    public List<String> mget(String[] keys) {

        Jedis jedis = borrow();

        try {
            return jedis.mget(keys);
        } finally {
            revert(jedis);
        }

    }

    @Deprecated
    public Set<String> sCopy(String key, String new_key) {

        Jedis jedis = borrow();
        try {
            Set<String> oldSets = jedis.smembers(key);
            for (String str : oldSets) {
                jedis.sadd(new_key, str);
            }
            return oldSets;
        } finally {
            revert(jedis);
        }

    }

    @Deprecated
    public void sClear(String key, String oldKey) {

        Jedis jedis = borrow();
        try {
            Set<String> oldSets = jedis.smembers(key);
            for (String str : oldSets) {
                jedis.del(oldKey + ":" + str);
            }
            jedis.del(key);
        } finally {
            revert(jedis);
        }

    }

    @Deprecated
    public Set<String> sMove(String key, String new_key) {

        Jedis jedis = borrow();
        try {
            Set<String> oldSets = jedis.smembers(key);
            for (String str : oldSets) {
                jedis.smove(key, new_key, str);
            }
            return oldSets;
        } finally {
            revert(jedis);
        }

    }

    
    public String makeKey(final String spName, final DAMParam[] param) {
        if (spName==null||spName.length()<1) throw new RuntimeException("the stored procedure name should be exist");
        final StringBuffer keyInSB = new StringBuffer();
        keyInSB.append(spName);

        if (param!=null&&param.length>0) {
            keyInSB.append(":");
            for (DAMParam p: param){
                keyInSB.append(p.getValue()).append("|");
            }
        }
        return keyInSB.toString();
    }

    
    public void addCachePolicy(String spName, CachePolicy policy) {
        cacheTargetList.put(spName, policy);
    }

    
    public void addCachePolicy(String spName, boolean isCacheTarget, boolean isMultiRow, int maxCount) {

        CachePolicy policy = new CachePolicy();
        policy.setCacheTarget(isCacheTarget);
        policy.setMultiRow(isMultiRow);
        policy.setMaxCount(maxCount);
        policy.setTimeToLive(10);

        cacheTargetList.put(spName, policy);
    }

    
    public void addCachePolicy(String spName, boolean isCacheTarget, boolean isMultiRow, int maxCount, int timeToLive) {

        CachePolicy policy = new CachePolicy();
        policy.setCacheTarget(isCacheTarget);
        policy.setMultiRow(isMultiRow);
        policy.setMaxCount(maxCount);
        policy.setTimeToLive(timeToLive);

        cacheTargetList.put(spName, policy);
    }

    
    public void delCachePolicy(String spName) {

        cacheTargetList.remove(spName);
    }

    
    public Map getCachePolicies() {
        return cacheTargetList;
    }

    public Object getCachePolicy(String spName) {
        return cacheTargetList.get(spName);
    }
    
    
    public boolean isCacheOn() {
        return cacheOn.get();
    }


    public void setCacheOn(boolean cacheOn) {
        this.cacheOn.set(cacheOn);
    }

}
