package com.kt.bit.csm.blds.cache;

import com.kt.bit.csm.blds.utility.CSMResultSet;
import com.kt.bit.csm.blds.utility.DatabaseResultSet;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static com.kt.bit.csm.blds.cache.util.JDBCTypeMappingUtil.*;

public class CachedResultSet implements Serializable, CSMResultSet {

    private static final long serialVersionUID = 2815450470746294066L;

    private List<CacheRow> cacheRows;
    private ListIterator iterator;
    private CacheRow currentCacheRow;
    private CachedResultSetMetadata metaData;
    private int currentIndex = 1;

    private int dataSourceType = DatabaseResultSet.RESULT_MODE_CACHE;

    public CachedResultSet(){
        this.cacheRows = new ArrayList<CacheRow>();
        this.metaData = new CachedResultSetMetadata();
        currentIndex = 1;
    }

    public CacheRow getCurrentCacheRow() {
        return currentCacheRow;
    }

    public void addRow(CacheRow row){
        cacheRows.add(row);
    }

    public void addRow(CacheColumn[] cacheColumns){
        cacheRows.add(new CacheRow(cacheColumns));
    }

    public void addRow(CacheColumn[] cacheColumns, int rowIndex){
        cacheRows.add(new CacheRow(cacheColumns, rowIndex));
    }

    public int getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(int dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public boolean next(){
        if(iterator == null)
            iterator = cacheRows.listIterator();

        if(iterator.hasNext()){
            currentCacheRow = (CacheRow)iterator.next();
            currentIndex++;
            return true;
        }
        else
            return false;
    }
    
    private CacheColumn getCacheColumn(final Object i) throws SQLException {
        
        if (currentCacheRow==null) throw new SQLException("please next() call first");
        if (i==null) throw new SQLException("i is empty");
        
        if (i instanceof Integer) {
            Integer ii = (Integer) i;
            if (ii>metaData.getColumnCount()) {
                throw new SQLException(i+" is over than column count:("+metaData.getColumnCount()+")");
            }
            final String columnName = metaData.getColumnName(ii);
            if (StringUtils.isEmpty(columnName)) {
                throw new SQLException("column name invalid");
            }
            return currentCacheRow.getColumn(columnName.toLowerCase());
        }
        else if (i instanceof String)
            return currentCacheRow.getColumn(((String) i).toLowerCase());
        
        throw new SQLException("the argument must be column index or name");

    }

    public String getString(int idx) throws SQLException{
        return _getString(getCacheColumn(idx));
    }

    public String getString(String name) throws SQLException{
        return _getString(getCacheColumn(name));
    }

    public Integer getInt(int idx) throws SQLException{
        return _getInt(getCacheColumn(idx));
    }

    public Integer getInt(String name) throws SQLException{
        return _getInt(getCacheColumn(name));
    }

    public Long getLong(int idx) throws SQLException{
        return _getLong(getCacheColumn(idx));
    }

    public Long getLong(String name) throws SQLException{
        return _getLong(getCacheColumn(name));
    }

    public Date getDate(int idx) throws SQLException{
        return _getDate(getCacheColumn(idx));
    }

    public Date getDate(String name) throws SQLException{
        return _getDate(getCacheColumn(name));
    }

    public Timestamp getTimestamp(int idx) throws SQLException {
        return _getTimestamp(getCacheColumn(idx));
    }

    public Timestamp getTimestamp(String name) throws SQLException {
        return _getTimestamp(getCacheColumn(name));
    }

    public int getRow() throws SQLException {
        return currentIndex;
    }

    public byte[] getBytes(String name) throws SQLException {
        return _getBytes(getCacheColumn(name));
    }

    public byte[] getBytes(int idx) throws SQLException {
        return _getBytes(getCacheColumn(idx));
    }

    public ResultSetMetaData getMetaData() throws SQLException {
        return metaData;
    }

    public void setMetaData(CachedResultSetMetadata metaData) {
        this.metaData = metaData;
    }

    public void close() throws SQLException {
        cacheRows.clear();
    }

    public boolean last(){
        return !iterator.hasNext();
    }

    public int getRowCount(){
        return cacheRows.size();
    }

    public int getFetchSize() throws SQLException {
        return -1;
    }

    public List<CacheRow> getCacheRows() {
        return cacheRows;
    }

    public void setCacheRows(ArrayList<CacheRow> cacheRows) {
        this.cacheRows = cacheRows;
    }
}

