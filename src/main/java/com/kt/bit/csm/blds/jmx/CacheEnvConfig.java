package com.kt.bit.csm.blds.jmx;

import java.io.IOException;

import com.kt.bit.csm.blds.cache.CacheEnvironments;
import com.kt.bit.csm.blds.cache.storage.RedisCacheManager;

public class CacheEnvConfig implements CacheEnvConfigMBean {

	public CacheEnvConfig() {}

	@Override
	public void setCacheOn(boolean flag) {
		
		try {
			RedisCacheManager manager = RedisCacheManager.getInstance();
			manager.setCacheOn(flag);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean getCacheOn() {
		
		try {
			RedisCacheManager manager = RedisCacheManager.getInstance();
			return manager.isCacheOn();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	@Override
	public void setCacheQueueSize(int size) {

		CacheEnvironments env = CacheEnvironments.getInstance();
		env.setQueueSize(size); //Setting Queue Size
		
	}
	
	@Override
	public int getCacheQueueSize() {

		CacheEnvironments env = CacheEnvironments.getInstance();
		return env.getQueueSize();
		
	}
	
	@Override
	public void setCacheMinPoolSize(int size) {

		CacheEnvironments env = CacheEnvironments.getInstance();
		env.setMinPoolSize(size); //Setting Minimum Pool Size
		
	}

	@Override
	public int getCacheMinPoolSize() {
		
		CacheEnvironments env = CacheEnvironments.getInstance();
		return env.getMinPoolSize();
		
	}
	
	@Override
	public void setCacheMaxPoolSize(int size) {

		CacheEnvironments env = CacheEnvironments.getInstance();
		env.setMaxPoolSize(size); //Setting Maximum Pool Size
		
	}

	@Override
	public int getCacheMaxPoolSize() {
		
		CacheEnvironments env = CacheEnvironments.getInstance();
		return env.getMaxPoolSize();
		
	}
	
	@Override
	public void setTotalSettings(boolean flag, int workers, int queueSize, int minPoolSize, int maxPoolSize) {

		try {
			RedisCacheManager manager = RedisCacheManager.getInstance();
			manager.setCacheOn(flag);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		CacheEnvironments env = CacheEnvironments.getInstance();
		
		env.setQueueSize(queueSize); //Setting Queue Size
		env.setMinPoolSize(minPoolSize); //Setting Minimum Pool Size
		env.setMaxPoolSize(maxPoolSize); //Setting Maximum Pool Size
		
	}

	@Override
	public String getTotalSettings() {
		
		StringBuilder sb = new StringBuilder();
		
		try {
			CacheEnvironments env = CacheEnvironments.getInstance();
			RedisCacheManager manager = RedisCacheManager.getInstance();
			
			sb.append("Cache On/Off : ").append(manager.isCacheOn()).append("\n");
			sb.append("Queue Size : ").append(env.getQueueSize()).append("\n");
			sb.append("Min Pool Size : ").append(env.getMinPoolSize()).append("\n");
			sb.append("Max Pool Size : ").append(env.getMaxPoolSize());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();

	}

}
