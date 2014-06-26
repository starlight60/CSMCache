package com.kt.bit.csm.blds.cache.storage;


import com.github.jedis.lock.JedisLock;
import com.kt.bit.csm.blds.cache.*;
import com.kt.bit.csm.blds.cache.config.CacheConfigManager;
import com.kt.bit.csm.blds.cache.util.DataFormatter;
import com.kt.bit.csm.blds.cache.util.JDBCTypeMappingUtil;
import com.kt.bit.csm.blds.cache.util.PropertyManager;
import com.kt.bit.csm.blds.cache.util.serializer.GsonDataFormatter;
import com.kt.bit.csm.blds.utility.*;

import org.apache.commons.lang.StringUtils;
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
    private int readTimeout;
    private int writeTimeout;
    
    private AtomicBoolean cacheOn = new AtomicBoolean(false);
    public ConcurrentHashMap<String,CachePolicy> cacheTargetList = new ConcurrentHashMap<String,CachePolicy>();
    public static RedisCacheManager instance = null;

    public static final String redisConfigFilePath = "redis-config.properties";     // The file should be within classpath
    public static final String redisConfigFileKey = "redis.config.file";

    private RedisCacheSetQueueManager queue = null;
    private DataFormatter formatter;
    private CacheEnvironments env;

    private Properties common;

    public static RedisCacheManager getInstance() throws IOException {
        if(instance == null){
            synchronized (RedisCacheManager.class) {
                instance = new RedisCacheManager();
                instance.init();
            }
        }
        return instance;
    }

    public static RedisCacheManager getInstance(String host, int port, int readTimeout, int writeTimeout) throws IOException {
        if(instance == null){
            synchronized (RedisCacheManager.class) {
                instance = new RedisCacheManager(host, port, readTimeout, writeTimeout);
                instance.init();
            }
        }
        return instance;
    }

    private JedisPool getPool;
    private JedisPool setPool;

    private void init() throws IOException {

        getPool = new JedisPool(new JedisPoolConfig(), this.host, this.port, this.readTimeout);
        setPool = new JedisPool(new JedisPoolConfig(), this.host, this.port, this.writeTimeout);

        // Load Cache Config, Policy Properties Files and Monitoring
        CacheConfigManager manager = CacheConfigManager.getInstance();
        manager.setPropertyChangeListener("cache-config", cacheConfigFilePath, cacheConfigFileKey, 60000);
        manager.setPropertyChangeListener("cache-policy", cachePolicyFilePath, cachePolicyFileKey, 60000);

        env = CacheEnvironments.getInstance();

        try {
            if(!this.ping().equalsIgnoreCase("PONG")){
                throw new Exception("Fail to connect to redis cache");
            }

            queue = new RedisCacheSetQueueManager(env.getBufferSize(), env.getMinPoolSize(), env.getMaxPoolSize(), env.getQueueCount(), setPool);
        } catch(Exception e){
            // Redis Cache 접속이실패하는 경우 Cache 를 사용하지 않도록 설정함.
            this.setCacheOn(false);
        }

        common = PropertyManager.loadPropertyFromFile(cacheConfigFilePath, cacheConfigFileKey);
        JDBCTypeMappingUtil.init(common);
    }

    private RedisCacheManager() throws IOException, NumberFormatException {

        formatter = GsonDataFormatter.getInstance();

        // Load basic properties
        Properties properties = PropertyManager.loadPropertyFromFile(redisConfigFilePath, redisConfigFileKey);
        this.host = properties.getProperty("redis.host");
        this.port = Integer.valueOf(properties.getProperty("redis.port"));
        this.readTimeout = Integer.valueOf(properties.getProperty("redis.read.timeoutInMS"));
        this.writeTimeout = Integer.valueOf(properties.getProperty("redis.write.timeoutInMS"));

    }

    private RedisCacheManager(String host, int port, int readTimeout, int writeTimeout) throws IOException, NumberFormatException {

        formatter = GsonDataFormatter.getInstance();
        this.host = host;
        this.port = port;
        this.readTimeout = readTimeout;
        this.writeTimeout = writeTimeout;

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
            return jedis.setex(key, ttl, value);
        } finally {
            revert(jedis);
        }

    }

    public void asynchSet(String key, String value, int ttl){
        if(queue != null ) queue.addCacheWork(new RedisCacheCommand(CacheCommand.CACHE_WORK_MODE_PUT, key, value, ttl));
    }

    public void asynchSet(String key, byte[] value, int ttl){
        if(queue != null ) queue.addCacheWork(new RedisCacheCommand(CacheCommand.CACHE_WORK_MODE_PUT, key.getBytes(), value, ttl));
    }

    public void asynchSet(String key, String value){
        if(queue != null ) queue.addCacheWork(new RedisCacheCommand(CacheCommand.CACHE_WORK_MODE_PUT, key, value));
    }

    public void asynchSet(String key, byte[] value){
        if(queue != null ) queue.addCacheWork(new RedisCacheCommand(CacheCommand.CACHE_WORK_MODE_PUT, key.getBytes(), value));
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
            return formatter.fromJson(get(key));
        }

        return resultSet;
    }

    public CachedResultSet saveResultSetToCache(String spName, String key, CSMResultSet result) throws SQLException {

        if (!(result instanceof DatabaseResultSet)) {
            throw new IllegalArgumentException("the data to be cached must be DatabaseResultSet");
        }

        if (StringUtils.isEmpty(spName)) {
            throw new IllegalArgumentException("spName must not be empty");
        }

        final CachePolicy policy = cacheTargetList.get(spName);
        final CachedResultSet cachedResultSet = makeCachedResultSet(spName, result, policy);

        if (cachedResultSet!=null) {
            this.asynchSet(key, formatter.toJson(cachedResultSet), policy.getTimeToLive());
        }

        return cachedResultSet;
    }

    public CachedResultSet makeCachedResultSet(String spName, CSMResultSet result, CachePolicy policy) throws SQLException {

        if (!(result instanceof DatabaseResultSet)) {
            throw new IllegalArgumentException("the data to be cached must be DatabaseResultSet");
        }

        if (StringUtils.isEmpty(spName)) {
            throw new IllegalArgumentException("spName must not be empty");
        }

        if (policy==null) return null;

        // Make Cache ResultSet from Database ResultSet
        {
            final DatabaseResultSet databaseResultSet = (DatabaseResultSet) result;

            try {
                final ResultSetMetaData meta = result.getMetaData();
                final CachedResultSet cachedResultSet = new CachedResultSet();
                final CachedResultSetMetadata crsm = (CachedResultSetMetadata) cachedResultSet.getMetaData();

                // Column Count / Index
                int colCount = meta.getColumnCount();
                crsm.setColumnCount(colCount);

                // Row Count
                int rowCount = 0, totalCount;
                if (policy.isMultiRow()) {
                    if (policy.isMultiRow() && policy.getMaxCount() == 0)
                        throw new IllegalArgumentException(spName + " Fetch Size Policy is invalid.");

                    // Fetch
                    if (policy.getFetchSize().equals(CacheFetchConstants.FETCH_SIZE))
                        totalCount = databaseResultSet.getFetchSize();
                    else if (policy.getFetchSize().equals(CacheFetchConstants.FETCH_ALL))
                        totalCount = -1;
                    else totalCount = 1;
                } else totalCount = 1;

                while ( databaseResultSet.next() ){
                    CacheColumn[] cacheColumns = new CacheColumn[colCount];
                    for(int i=1; i <= colCount;i++){
                        final String columnName = meta.getColumnName(i).toLowerCase();
                        cacheColumns[i-1] = new CacheColumn(columnName, meta.getColumnType(i), databaseResultSet.getObject(i));
                        crsm.addColumnIndex(i, columnName.toLowerCase());
                    }
                    cachedResultSet.addRow(cacheColumns, rowCount);
                    rowCount++;

                    if ((totalCount != -1) && (rowCount == totalCount)) {
                        break;
                    }
                }

                return cachedResultSet;

            } catch (SQLException e) {
                throw e;
            }
        }
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

        getPool.destroy();
        setPool.destroy();

    }

    /**
     *
     * @return
     */
    public Jedis borrow() {

        return getPool.getResource();

    }

    /**
     *
     * @return
     */
    public Jedis borrow( CacheResourceType cacheResourceType) {

        if(cacheResourceType == CacheResourceType.SET_CACHE ) return setPool.getResource();
        else return getPool.getResource();
    }

    /**
     *
     * @param jedis
     */
    public void revert(Jedis jedis) {

        getPool.returnResource(jedis);

    }

    public void revert(Jedis jedis, CacheResourceType cacheResourceType ) {

        if(cacheResourceType == CacheResourceType.SET_CACHE ) setPool.returnResource(jedis);
        else getPool.returnResource(jedis);

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

    public CachePolicy getCachePolicy(String spName) { return cacheTargetList.get(spName); }

    public Map getCachePolicy() {
        return cacheTargetList;
    }
    
    public Map getCachePolicies() {
        return cacheTargetList;
    }

    public boolean isCacheOn() {
        return cacheOn.get();
    }

    public void setCacheOn(boolean cacheOn) {
        this.cacheOn.set(cacheOn);
    }

    public Properties getCommonProperties() {
        return common;
    }

}
