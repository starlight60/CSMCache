package com.kt.bit.csm.blds.cache;

import com.kt.bit.csm.blds.utility.CSMResultSet;
import com.kt.bit.csm.blds.utility.DatabaseResultSet;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static com.kt.bit.csm.blds.cache.util.JDBCTypeMappingUtil.*;

/**
 * Todo: Exception (eg. check the status and manipulate error if the user call "get" without calling "next"
 */
public class CachedResultSet implements Serializable, CSMResultSet {

    private static final long serialVersionUID = 2815450470746294066L;

    private List<CacheRow> cacheRows;
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

    public String getString(int idx) throws SQLException{
        final CacheColumn column = currentCacheRow.getColumn(idx);
        return (column!=null) ? _getString(column.getValue()):null;
    }

    public String getString(String name) throws SQLException{
        final CacheColumn column = currentCacheRow.getColumn(name);
        return (column!=null) ? _getString(column.getValue()):null;
    }

    public Integer getInt(int idx) throws SQLException{
        final CacheColumn column = currentCacheRow.getColumn(idx);
        return (column!=null) ? _getInt(column.getValue()):null;
    }

    public Integer getInt(String name) throws SQLException{
        final CacheColumn column = currentCacheRow.getColumn(name);
        return (column!=null) ? _getInt(column.getValue()):null;
    }

    public Long getLong(int idx) throws SQLException{
        final CacheColumn column = currentCacheRow.getColumn(idx);
        return (column!=null) ? _getLong(column.getValue()):null;
    }

    public Long getLong(String name) throws SQLException{
        final CacheColumn column = currentCacheRow.getColumn(name);
        return (column!=null) ? _getLong(column.getValue()):null;
    }

    public Date getDate(int idx) throws SQLException{
        final CacheColumn column = currentCacheRow.getColumn(idx);
        return (column!=null) ? _getDate(column.getValue()):null;
    }

    public Date getDate(String name) throws SQLException{
        final CacheColumn column = currentCacheRow.getColumn(name);
        return (column!=null) ? _getDate(column.getValue()):null;
    }

    public Timestamp getTimestamp(int idx) throws SQLException {
        final CacheColumn column = currentCacheRow.getColumn(idx);
        return (column!=null) ? _getTimestamp(column.getValue()):null;
    }

    public Timestamp getTimestamp(String name) throws SQLException {
        final CacheColumn column = currentCacheRow.getColumn(name);
        return (column!=null) ? _getTimestamp(column.getValue()):null;
    }

    public int getRow() throws SQLException {
        return currentIndex;
    }

    public byte[] getBytes(String name) throws SQLException {
        final CacheColumn column = currentCacheRow.getColumn(name);
        return (column!=null) ? _getBytes(column.getValue()):null;
    }

    public byte[] getBytes(int idx) throws SQLException {
        final CacheColumn column = currentCacheRow.getColumn(idx);
        return (column!=null) ? _getBytes(column.getValue()):null;
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
        return -1;
    }

    public List<CacheRow> getCacheRows() {
        return cacheRows;
    }

    public void setCacheRows(ArrayList<CacheRow> cacheRows) {
        this.cacheRows = cacheRows;
    }
}

