package com.kt.bit.csm.blds.cache;

public class CachePolicy {
    private boolean cacheTarget = true;
    private int maxCount;
    private boolean multiRow;

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

    private int timeToLive = 10;
}
