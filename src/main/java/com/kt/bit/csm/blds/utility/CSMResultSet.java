package com.kt.bit.csm.blds.utility;

import java.sql.Date;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;

public interface CSMResultSet {

    public static int RESULT_MODE_CACHE = 1;
    public static int RESULT_MODE_ORACLE = 2;

    public boolean next() throws SQLException;
    public Integer getInt(int i) throws SQLException;
    public String getString(int i) throws SQLException;
    public Long getLong(int i) throws SQLException;
    public Long getLong(String string) throws SQLException;
    public String getString(String string) throws SQLException;
    public Integer getInt(String string) throws SQLException;
    public Date getDate(String string) throws SQLException;
    public Date getDate(int i) throws SQLException;
    public Timestamp getTimestamp(String string) throws SQLException;
    public Timestamp getTimestamp(int i) throws SQLException;
    public int getRow() throws SQLException;
    public boolean last() throws SQLException;
    public byte[] getBytes(int string) throws SQLException;
    public byte[] getBytes(String string) throws SQLException;
    public ResultSetMetaData getMetaData() throws SQLException;
    public void close() throws SQLException;
    public int getRowCount() throws SQLException;
    public int getFetchSize() throws SQLException;

    public int getDataSourceType();
    public void setDataSourceType(int dataSourceType);

}
