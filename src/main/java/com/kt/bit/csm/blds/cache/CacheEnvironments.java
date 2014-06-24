package com.kt.bit.csm.blds.cache;

public class CacheEnvironments {

	public static CacheEnvironments env = new CacheEnvironments();
	
	public CacheEnvironments() {
		
	}
	
	public static CacheEnvironments getInstance() {
		return env;
	}
	
	private int queueSize = 0;
	private int minPoolSize = 0;
	private int maxPoolSize = 0;
	
	public int getQueueSize() {
		return queueSize;
	}
	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
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
	
}
