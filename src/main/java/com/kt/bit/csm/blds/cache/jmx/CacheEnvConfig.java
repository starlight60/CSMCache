package com.kt.bit.csm.blds.cache.jmx;

import java.io.IOException;

import com.kt.bit.csm.blds.cache.CacheEnvironments;
import com.kt.bit.csm.blds.cache.storage.RedisCacheManager;

public class CacheEnvConfig implements CacheEnvConfigMBean {

	public CacheEnvConfig() {}

	public void setCacheOn(boolean flag) {
		
		try {
			RedisCacheManager manager = RedisCacheManager.getInstance();
			manager.setCacheOn(flag);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean getCacheOn() {
		
		try {
			RedisCacheManager manager = RedisCacheManager.getInstance();
			return manager.isCacheOn();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
		
	}

    public void setCacheBufferSize(int size) {

        CacheEnvironments env = CacheEnvironments.getInstance();
        env.setBufferSize(size); //Setting Queue Size

    }

    public int getCacheBufferSize() {

		CacheEnvironments env = CacheEnvironments.getInstance();
		return env.getBufferSize();
		
	}
	
	public void setCacheMinPoolSize(int size) {

		CacheEnvironments env = CacheEnvironments.getInstance();
		env.setMinPoolSize(size); //Setting Minimum Pool Size
		
	}

	public int getCacheMinPoolSize() {
		
		CacheEnvironments env = CacheEnvironments.getInstance();
		return env.getMinPoolSize();
		
	}
		
	public void setCacheMaxPoolSize(int size) {

		CacheEnvironments env = CacheEnvironments.getInstance();
		env.setMaxPoolSize(size); //Setting Maximum Pool Size
		
	}
	
	public int getCacheMaxPoolSize() {
		
		CacheEnvironments env = CacheEnvironments.getInstance();
		return env.getMaxPoolSize();
		
	}

    public void setCacheQueueCount(int size) {
        CacheEnvironments env = CacheEnvironments.getInstance();
        env.setQueueCount(size);
    }

    public int getCacheQueueCount() {
        CacheEnvironments env = CacheEnvironments.getInstance();
        return env.getQueueCount();
    }

    public void setTotalSettings(boolean flag, int bufferSize, int queueCount, int minPoolSize, int maxPoolSize) {

        CacheEnvironments env = CacheEnvironments.getInstance();
        env.setMinPoolSize(minPoolSize);
        env.setMaxPoolSize(maxPoolSize);
        env.setQueueCount(queueCount);
        env.setBufferSize(bufferSize);

        this.setCacheOn(flag);

    }

    public String getTotalSettings() {
		
		StringBuilder sb = new StringBuilder();
		
		try {
			CacheEnvironments env = CacheEnvironments.getInstance();
			RedisCacheManager manager = RedisCacheManager.getInstance();
			
			sb.append("Cache On/Off : ").append(manager.isCacheOn()).append("\n");
			sb.append("Buffer Size : ").append(env.getBufferSize()).append("\n");
            sb.append("Queue Count : ").append(env.getQueueCount()).append("\n");
			sb.append("Min Pool Size : ").append(env.getMinPoolSize()).append("\n");
			sb.append("Max Pool Size : ").append(env.getMaxPoolSize());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();

	}

}
