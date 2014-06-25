package com.kt.bit.csm.blds.cache;

public class CachePolicy {
	
    private boolean cacheTarget = true;
    private int maxCount;
    private boolean multiRow;
    private String fetchSize = CacheFetchConstants.FETCH_ALL;
    private int timeToLive = 10;
    
    public boolean isCacheTarget() {
        return cacheTarget;
    }

    public void setCacheTarget(boolean cacheTarget) {
        this.cacheTarget = cacheTarget;
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public boolean isMultiRow() {
        return multiRow;
    }

    public void setMultiRow(boolean multiRow) {
        this.multiRow = multiRow;
    }

    public String getFetchSize() {
		return fetchSize;
	}

	public void setFetchSize(String fetchSize) {
		this.fetchSize = fetchSize;
	}

}
