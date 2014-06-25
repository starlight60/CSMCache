package com.kt.bit.csm.blds.cache;

import com.kt.bit.csm.blds.utility.CSMResultSet;
import com.kt.bit.csm.blds.utility.DatabaseResultSet;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.ListIterator;

import static com.kt.bit.csm.blds.cache.util.JDBCTypeMappingUtil.*;

/**
 * Todo: Exception (eg. check the status and manipulate error if the user call "get" without calling "next"
 */
public class CachedResultSet implements Serializable, CSMResultSet {

    private static final long serialVersionUID = 2815450470746294066L;

    private ArrayList<CacheRow> cacheRows;
    private ListIterator iterator;
    private CacheRow currentCacheRow;
    private int currentIndex = 1;

    private int dataSourceType = DatabaseResultSet.RESULT_MODE_CACHE;


    public CachedResultSet(){
        this.cacheRows = new ArrayList<CacheRow>();
        currentIndex = 1;
    }

    public CacheRow getCurrentCacheRow() {
        return currentCacheRow;
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

    
    public String getString(int idx) throws SQLException{
        return _getString(currentCacheRow.getColumn(idx).getValue());
    }

    
    public String getString(String name) throws SQLException{
        return _getString(currentCacheRow.getColumn(name).getValue());
    }

    
    public int getInt(int idx) throws SQLException{
        return _getInt(currentCacheRow.getColumn(idx).getValue());
    }

    
    public int getInt(String name) throws SQLException{
        return _getInt(currentCacheRow.getColumn(name).getValue());
    }

    
    public long getLong(int idx) throws SQLException{
        return _getLong(currentCacheRow.getColumn(idx).getValue());
    }

    
    public long getLong(String name) throws SQLException{
        return _getLong(currentCacheRow.getColumn(name).getValue());
    }

    
    public Date getDate(int idx) throws SQLException{
        return _getDate(currentCacheRow.getColumn(idx).getValue());
    }

    
    public Date getDate(String name) throws SQLException{
        return _getDate(currentCacheRow.getColumn(name).getValue());
    }

    
    public Timestamp getTimestamp(int idx) throws SQLException {
        return _getTimestamp(currentCacheRow.getColumn(idx).getValue());
    }

    
    public Timestamp getTimestamp(String name) throws SQLException {
        return _getTimestamp(currentCacheRow.getColumn(name).getValue());
    }

    
    public int getRow() throws SQLException {
        return currentIndex;
    }

    
    public byte[] getBytes(String string) throws SQLException {
        return _getBytes(currentCacheRow.getColumn(string).getValue());
    }

    
    public byte[] getBytes(int idx) throws SQLException {
        return _getBytes(currentCacheRow.getColumn(idx).getValue());
    }

    /**
     * TODO: please implement this procedure
     * @return
     * @throws SQLException
     */
    
    public ResultSetMetaData getMetaData() throws SQLException {
        return null;
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
        throw new SQLException("not supported");
    }

}

