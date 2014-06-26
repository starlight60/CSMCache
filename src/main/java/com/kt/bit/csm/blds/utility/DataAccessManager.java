/************************************************************************************************************	
Package Name:	com.kt.bit.csm.blds.utility
Author:			Pushpendra Pandey
Description:	
This package contains utility classes like logging and Data access framework of CSM 

Modification Log:	
When                           Version   			Who					 What	
21-12-2010                     1.0                  Pushpendra Pandey    New class created
----------------------------------------------------------------------------------------------------------	
 ***************************************************************************************************************/
package com.kt.bit.csm.blds.utility;

import com.kt.bit.csm.blds.cache.CacheManager;
import com.kt.bit.csm.blds.cache.storage.RedisCacheManager;
import com.kt.bit.csm.blds.cache.util.JDBCTypeMappingUtil;
import com.kt.bit.csm.blds.cache.util.PropertyManager;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleTypes;

import java.sql.Connection;
import java.sql.SQLException;

/**************************************************************************
 * This class exposes capabilities for executing stored procedures against the
 * database, by means of the Reusable Architecture Framework Data Access Module.
 * 
 **************************************************************************/
public class DataAccessManager {
	private final DBAccessor dbAccessor;
	private Connection conn = null;
    private CacheManager cacheManager;

	/**
	 * Default constructor.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public DataAccessManager() throws Exception {
	    /*
		if ( autocommitProcMap.isEmpty() ) {
			String[] autocommitProcKeys = ConfigUtil.getStringArray("CSM.AUTOCOMMIT.PROCNAMES");
			if( autocommitProcKeys != null ) {
    			for (String key : autocommitProcKeys) {
    				autocommitProcMap.put(key, "true");
    			}
			}
		}
		*/
		this.dbAccessor = DBAccessor.getInstance();
        this.cacheManager = RedisCacheManager.getInstance();
	}

	/**
	 * This method takes the request Generate the SQL Command.
	 * 
	 * @param spName
	 *            -SP Name
	 * @param paramNumber
	 *            - Param number
	 * @return sqlCommand - Generated SQL command
	 */
	private String buildString(String spName, int paramNumber) {
	    StringBuffer sql = new StringBuffer( "{call " ).append( spName ).append( "(?" );
		for (int i = 0; i < paramNumber; i++) {
		    sql.append( ",?" );
		}
		sql.append( ")}" );
		return sql.toString();
	}

	/**
	 * This method takes in the request to execute a stored procedure against
	 * the SDP Database to execute queries that return a String as result.
	 * 
	 * @param conn
	 *            - Connection object
	 * @param spName
	 *            - StoredProcedure name
	 * @param params
	 *            the params
	 * @return params DAMParam
	 * @throws java.sql.SQLException
	 *             the sQL exception
	 */
	private OracleCallableStatement prepareCallableStatement(Connection conn, String spName, DAMParam[] params) throws SQLException {

        String sqlCommand = buildString(spName, (params!=null)? params.length:0);
        OracleCallableStatement cs = (OracleCallableStatement) conn.prepareCall(sqlCommand);

        if (params!=null) {
            for (int i = 0; i < params.length; i++) {
                cs.setObject(params[i].getParamName(), params[i].getValue(), params[i].getType());
            }
        }
        return cs;
	}

    public CSMResultSet executeStoredProcedureForQuery(String spName, String outParamName, DAMParam[] param) throws Exception {
        return executeStoredProcedureForQuery(spName, outParamName, param, CacheManager.CacheMode.CACHE_MODE_ON);
    }

    public CSMResultSet executeStoredProcedureForQuery(String spName, String outParamName, DAMParam[] param, CacheManager.CacheMode cacheMode) throws Exception {
        OracleCallableStatement oraCallStmt;
        OracleResultSet deptResultSet;
        CSMResultSet sdpResultSet = null;
        boolean isCacheTarget = false;
        String key = null;

        /**
         *  cache 에서 Data 를 조회하는 구간
         *  1. Cache  대상인지 확인
         *  2. 대상인 경우 Cache 에 존재하는지 확인 ( Key = spName + param.getValue()[] )
         *  3. Cache 에 존재하는 경우 Cache 에서 조회하여 DatabaseResultSet 구성하여 return
         *  4. Cache 에 존재하지 않는 경우 기존 로직 수행
         *  5. Cache 대상이 아닌 경우도 기존 로직 수행
         */
        try {
            if (cacheMode == CacheManager.CacheMode.CACHE_MODE_ON || cacheMode == CacheManager.CacheMode.CACHE_READ_ONLY)
                isCacheTarget = this.cacheManager.isCacheTarget(spName);

            if(isCacheTarget){
                key = cacheManager.makeKey(spName, param);
                sdpResultSet = this.cacheManager.getResultSet(key);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            // TODO : skip, but we should generate the log for this exception
        }

        if( sdpResultSet == null ){
            if (conn == null || conn.isClosed()) {
                conn = dbAccessor.getConnection();
            }
            // Prepare the callable statement
            oraCallStmt = prepareCallableStatement(conn, spName, param);
            oraCallStmt.registerOutParameter(outParamName, OracleTypes.CURSOR);
            oraCallStmt.execute();

            deptResultSet = (OracleResultSet) oraCallStmt.getObject(outParamName);
            sdpResultSet = new DatabaseResultSet(deptResultSet, oraCallStmt);

            if(isCacheTarget && key != null && cacheManager.isCacheOn() && cacheMode != CacheManager.CacheMode.CACHE_READ_ONLY){
                sdpResultSet = this.cacheManager.saveResultSetToCache(spName, key, sdpResultSet);
            }
        }

        return sdpResultSet;
    }
}

