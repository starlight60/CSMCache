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

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleResultSet;

import java.sql.Date;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;

/**************************************************************************
DatabaseResultSet
============
This class implements utility methods to iterate database result set.
**************************************************************************/
public class DatabaseResultSet implements CSMResultSet {

	private OracleCallableStatement	cs	= null;
	private OracleResultSet	        rs	= null;
    private int                     dataSourceType = DatabaseResultSet.RESULT_MODE_ORACLE;

    /**************************************************************************
     DatabaseResultSet
     ============
     Parameterized constructor to construct DatabaseResultSet object

     @param deptResultSet - OracleResultSet object
     @param cs - OracleCallableStatement object
     **************************************************************************/
    public DatabaseResultSet(OracleResultSet deptResultSet, OracleCallableStatement cs) {
        this.rs = deptResultSet;
        this.cs = cs;
    }

    
    public int getDataSourceType() {
        return dataSourceType;
    }

    
    public void setDataSourceType(int dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

	/**************************************************************************
	close
	=====
	This method used to close connection with database
		
	@exception java.sql.SQLException - Database exception
	**************************************************************************/
    
	public void close() throws SQLException {
        rs.close();
        cs.close();
        rs = null;
        cs = null;
	}

    
    public int getRowCount() throws SQLException {
        return rs.getRow();
    }

    /**************************************************************************
	next
	====
	This method used to return next row from database result set

	@return boolean
	@exception java.sql.SQLException - Database exception
	**************************************************************************/
    
	public boolean next() throws SQLException {
        return rs.next();
	}

	/**************************************************************************
	getInt
	======
	This method used to get value of NUMBER type column from result set

	@param i - CacheColumn number
	@return int
	@exception java.sql.SQLException - Database exception
	**************************************************************************/
    
	public int getInt(int i) throws SQLException {
        return rs.getInt(i);
	}

	/**************************************************************************
	getString
	=========
	This method used to get row value of column type VARCHAR from result set

	@param
	@return String
	@exception java.sql.SQLException - Database exception
	**************************************************************************/
    
	public String getString(int i) throws SQLException {
        return rs.getString(i);
	}

	/**************************************************************************
	getLong
	=======
	This method used to get value of LONG type column from result set

	@param i - CacheColumn number
	@return long
	@exception java.sql.SQLException - Database exception
	**************************************************************************/
    
	public long getLong(int i) throws SQLException {
        return rs.getLong(i);
	}

	/**************************************************************************
	getLong
	=======
	This method used to get value of LONG type column from result set

	@param  string
	@return long
	@exception java.sql.SQLException - Database exception
	**************************************************************************/
    
	public long getLong(String string) throws SQLException {
        return rs.getLong(string);
	}

	/**************************************************************************
	getString
	=========
	This method used to get row value of column type VARCHAR from  result set

	@param string - CacheColumn name
	@return String
	@exception java.sql.SQLException - Database exception
	**************************************************************************/
    
	public String getString(String string) throws SQLException {
        return rs.getString(string);
	}

	/**************************************************************************
	getInt
	======
	This method used to get value of NUMBER type column from result set

	@param string - CacheColumn name
	@return int
	@exception java.sql.SQLException - Database exception
	**************************************************************************/
    
	public int getInt(String string) throws SQLException {
        return rs.getInt(string);
	}

    
	public Date getDate(String string) throws SQLException {
        return rs.getDate(string);
	}

    
	public Date getDate(int i) throws SQLException {
        return rs.getDate(i);
	}

    
	public Timestamp getTimestamp(String string) throws SQLException {
        return rs.getTimestamp(string);
	}

    
	public Timestamp getTimestamp(int i) throws SQLException {
        return rs.getTimestamp(i);
	}

    
    public int getFetchSize() throws SQLException {
        return rs.getFetchSize();
    }

    public Object getObject(int i) throws SQLException{
        return rs.getObject(i);
    }


    public Object getObject(String string) throws SQLException{
        return rs.getObject(string);
    }

    
	public int getRow() throws SQLException {
		return rs.getRow();
	}

    
	public boolean last() throws SQLException {
		return rs.last();
	}

    
	public byte[] getBytes(String string) throws SQLException {
		return rs.getBytes(string);
	}

    
    public byte[] getBytes(int idx) throws SQLException {
        return rs.getBytes(idx);
    }

	public ResultSetMetaData getMetaData() throws SQLException {
		return rs.getMetaData();
	}

}
