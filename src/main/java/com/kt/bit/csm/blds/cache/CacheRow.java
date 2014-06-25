package com.kt.bit.csm.blds.cache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CacheRow implements Serializable {
    private Map<String, CacheColumn> cacheColumns;
    int rowIndex = 0;

    public CacheRow() {
        this.cacheColumns = new HashMap<String, CacheColumn>();
    }

    public CacheRow(CacheColumn[] cacheColumns){
        this.cacheColumns = new HashMap<String, CacheColumn >();
        for( int i = 1; i <= cacheColumns.length ; i++ ){
            int dataColumnIndex = i-1;
            this.cacheColumns.put(cacheColumns[dataColumnIndex].getName(), cacheColumns[dataColumnIndex]);
            this.cacheColumns.put(String.valueOf(i), cacheColumns[dataColumnIndex]);
        }
        this.rowIndex = cacheColumns.length;
    }

    public CacheRow(CacheColumn[] cacheColumns, int rowIndex){
        this.cacheColumns = new HashMap<String, CacheColumn >();
        for( int i = 1; i <= cacheColumns.length ; i++ ){
            int dataColumnIndex = i-1;
            this.cacheColumns.put(cacheColumns[dataColumnIndex].getName(), cacheColumns[dataColumnIndex]);
            this.cacheColumns.put(String.valueOf(i), cacheColumns[dataColumnIndex]);
        }
        this.rowIndex = rowIndex;
    }

    public CacheColumn getColumn(int idx){
        CacheColumn cacheColumn = null;
        if(cacheColumns != null ){
            cacheColumn = this.cacheColumns.get(String.valueOf(idx));
        }
        return cacheColumn;
    }

    public CacheColumn getColumn(String name){
        CacheColumn cacheColumn = null;
        if(cacheColumns != null ){
            cacheColumn = this.cacheColumns.get(name);
        }
        return cacheColumn;
    }

    public void setColumn(int idx, CacheColumn cacheColumn){
        if(cacheColumns != null ){
            cacheColumns.put(cacheColumn.getName(), cacheColumn);
            cacheColumns.put(""+idx, cacheColumn);
        }
    }

    int getRowIndex() {
        return rowIndex;
    }

    void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    Map getCacheColumns() {
        return cacheColumns;
    }

    void setCacheColumns(CacheColumn[] cacheColumns) {
        this.cacheColumns = new HashMap<String, CacheColumn>();
        for( int i = 0; i < cacheColumns.length ; i++ ){
            this.cacheColumns.put(cacheColumns[i].getName(), cacheColumns[i]);
            this.cacheColumns.put(""+i, cacheColumns[i]);
        }
    }

    public void addCacheColumn(final String key, final CacheColumn column) {
        this.cacheColumns.put(key, column);
    }

}
