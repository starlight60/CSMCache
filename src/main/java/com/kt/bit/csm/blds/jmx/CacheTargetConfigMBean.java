package com.kt.bit.csm.blds.jmx;

public interface CacheTargetConfigMBean {

	public void setCacheTarget(String spName);
	public void setCacheTarget(String spName, boolean isTarget);
	public void setCacheTarget(String spName, boolean isTarget, String fetchSize);
	public void setCacheTarget(String spName, boolean isTarget, String fetchSize, int ttl);
	public void setCacheTarget(String spName, boolean isTarget, String fetchSize, int ttl, int maxCount);
	
	public String getCacheTargetPolicies();
	
}
