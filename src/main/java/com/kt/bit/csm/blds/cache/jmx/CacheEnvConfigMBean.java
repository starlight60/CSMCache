package com.kt.bit.csm.blds.cache.jmx;

public interface CacheEnvConfigMBean {

	//Getter / Setter Cache On/Off
	public void setCacheOn(boolean flag);
	public boolean getCacheOn();
	
	//Getter / Setter Queue Size
	public void setCacheBufferSize(int size);
	public int getCacheBufferSize();
	
	//Getter / Setter Minimum Pool Size
	public void setCacheMinPoolSize(int size);
	public int getCacheMinPoolSize();
	
	//Getter / Setter Maximum Pool Size
	public void setCacheMaxPoolSize(int size);
	public int getCacheMaxPoolSize();

    public void setCacheQueueCount(int size);
    public int getCacheQueueCount();
	
	//Set Total Settings
	public void setTotalSettings(boolean flag, int bufferSize, int queueCount, int minPoolSize, int maxPoolSize);
	//Get Total Settings
	public String getTotalSettings();
	
}
