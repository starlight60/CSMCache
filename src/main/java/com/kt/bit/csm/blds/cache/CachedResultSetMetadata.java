package com.kt.bit.csm.blds.cache;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CachedResultSetMetadata implements ResultSetMetaData {

    private int columnCount = 0;
    private Map<Integer,String> columnIndex = new HashMap<Integer,String>();

    public int getColumnCount() throws SQLException {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public boolean isAutoIncrement(int column) throws SQLException {
        throw new SQLException("not supported");
    }
    
    public boolean isCaseSensitive(int column) throws SQLException {
        throw new SQLException("not supported");

    }
    
    public boolean isSearchable(int column) throws SQLException {
        throw new SQLException("not supported");

    }
    
    public boolean isCurrency(int column) throws SQLException {
        throw new SQLException("not supported");
    }
    
    public int isNullable(int column) throws SQLException {
        throw new SQLException("not supported");
    }
    
    public boolean isSigned(int column) throws SQLException {
        throw new SQLException("not supported");
    }
    
    public int getColumnDisplaySize(int column) throws SQLException {
        throw new SQLException("not supported");
    }
    
    public String getColumnLabel(int column) throws SQLException {
        throw new SQLException("not supported");
    }
    
    public String getColumnName(int column) throws SQLException {
        return columnIndex.get(column);
    }

    public Map<Integer, String> getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(Map<Integer,String> columnIndex) {
        this.columnIndex = columnIndex;
    }

    public void addColumnIndex(int idx, String name) {
        this.columnIndex.put(idx, name);
    }
    
    public String getSchemaName(int column) throws SQLException {
        throw new SQLException("not supported");
    }
    
    public int getPrecision(int column) throws SQLException {
        throw new SQLException("not supported");
    }
    
    public int getScale(int column) throws SQLException {
        throw new SQLException("not supported");
    }
    
    public String getTableName(int column) throws SQLException {
        throw new SQLException("not supported");
    }
    
    public String getCatalogName(int column) throws SQLException {
        throw new SQLException("not supported");
    }
    
    public int getColumnType(int column) throws SQLException {
        throw new SQLException("not supported");
    }
    
    public String getColumnTypeName(int column) throws SQLException {
        throw new SQLException("not supported");
    }
    
    public boolean isReadOnly(int column) throws SQLException {
        throw new SQLException("not supported");
    }

    public boolean isWritable(int column) throws SQLException {
        throw new SQLException("not supported");
    }
    
    public boolean isDefinitelyWritable(int column) throws SQLException {
        throw new SQLException("not supported");
    }
    
    public String getColumnClassName(int column) throws SQLException {
        throw new SQLException("not supported");
    }
    
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLException("not supported");
    }
    
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new SQLException("not supported");
    }

}
