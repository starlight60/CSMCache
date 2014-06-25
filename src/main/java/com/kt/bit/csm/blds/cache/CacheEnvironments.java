package com.kt.bit.csm.blds.cache;

public class CacheEnvironments {

	public static CacheEnvironments env = new CacheEnvironments();
	
	public CacheEnvironments() {
		
	}
	
	public static CacheEnvironments getInstance() {
		return env;
	}
	
	private int bufferSize = 0;
	private int minPoolSize = 0;
	private int maxPoolSize = 0;
    private int queueCount = 0;

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public int getMinPoolSize() {
        return minPoolSize;
    }

    public void setMinPoolSize(int minPoolSize) {
        this.minPoolSize = minPoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getQueueCount() {
        return queueCount;
    }

    public void setQueueCount(int queueCount) {
        this.queueCount = queueCount;
    }
}
