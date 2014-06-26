package com.kt.bit.csm.blds.cache;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CachedResultSetMetadata implements ResultSetMetaData {

    private int columnCount = 0;
    private Map<Integer,String> columnIndex = new HashMap<Integer,String>();

    @Override
    public int getColumnCount() throws SQLException {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    @Override
    public boolean isAutoIncrement(int column) throws SQLException {
        throw new SQLException("not supported");
    }

    @Override
    public boolean isCaseSensitive(int column) throws SQLException {
        throw new SQLException("not supported");

    }

    @Override
    public boolean isSearchable(int column) throws SQLException {
        throw new SQLException("not supported");

    }

    @Override
    public boolean isCurrency(int column) throws SQLException {
        throw new SQLException("not supported");
    }

    @Override
    public int isNullable(int column) throws SQLException {
        throw new SQLException("not supported");
    }

    @Override
    public boolean isSigned(int column) throws SQLException {
        throw new SQLException("not supported");
    }

    @Override
    public int getColumnDisplaySize(int column) throws SQLException {
        throw new SQLException("not supported");
    }

    @Override
    public String getColumnLabel(int column) throws SQLException {
        throw new SQLException("not supported");
    }

    @Override
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

    @Override
    public String getSchemaName(int column) throws SQLException {
        throw new SQLException("not supported");
    }

    @Override
    public int getPrecision(int column) throws SQLException {
        throw new SQLException("not supported");
    }

    @Override
    public int getScale(int column) throws SQLException {
        throw new SQLException("not supported");
    }

    @Override
    public String getTableName(int column) throws SQLException {
        throw new SQLException("not supported");
    }

    @Override
    public String getCatalogName(int column) throws SQLException {
        throw new SQLException("not supported");
    }

    @Override
    public int getColumnType(int column) throws SQLException {
        throw new SQLException("not supported");
    }

    @Override
    public String getColumnTypeName(int column) throws SQLException {
        throw new SQLException("not supported");
    }

    @Override
    public boolean isReadOnly(int column) throws SQLException {
        throw new SQLException("not supported");
    }

    @Override
    public boolean isWritable(int column) throws SQLException {
        throw new SQLException("not supported");
    }

    @Override
    public boolean isDefinitelyWritable(int column) throws SQLException {
        throw new SQLException("not supported");
    }

    @Override
    public String getColumnClassName(int column) throws SQLException {
        throw new SQLException("not supported");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLException("not supported");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new SQLException("not supported");
    }

}
