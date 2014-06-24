package com.kt.bit.csm.blds.jmx;

public interface CacheEnvConfigMBean {

	//Getter / Setter Cache On/Off
	public void setCacheOn(boolean flag);
	public boolean getCacheOn();
	
	//Getter / Setter Queue Size
	public void setCacheQueueSize(int size);
	public int getCacheQueueSize();
	
	//Getter / Setter Minimum Pool Size
	public void setCacheMinPoolSize(int size);
	public int getCacheMinPoolSize();
	
	//Getter / Setter Maximum Pool Size
	public void setCacheMaxPoolSize(int size);
	public int getCacheMaxPoolSize();
	
	//Set Total Settings
	public void setTotalSettings(boolean flag, int workers, int queueSize, int minPoolSize, int maxPoolSize);
	//Get Total Settings
	public String getTotalSettings();
	
}
