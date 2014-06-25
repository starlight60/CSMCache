package com.kt.bit.csm.blds.cache.jmx;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.kt.bit.csm.blds.cache.CacheFetchConstants;
import com.kt.bit.csm.blds.cache.CachePolicy;
import com.kt.bit.csm.blds.cache.storage.RedisCacheManager;

public class CacheTargetConfig implements CacheTargetConfigMBean {

	private final String fileName = "D:/dev_sdp/eclipse/cache-policy.properties";
	
	public void setCacheTarget(String spName) {

		try {
			RedisCacheManager manager = RedisCacheManager.getInstance();

			//Default Setting
			CachePolicy policy = new CachePolicy();
			policy.setCacheTarget(true);
			policy.setFetchSize(CacheFetchConstants.FETCH_ALL);
			policy.setTimeToLive(10);
			
			manager.addCachePolicy(spName, policy);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	public String getCacheTargetPolicies() {
		
		StringBuilder sb = new StringBuilder();
		
		try {
			RedisCacheManager manager = RedisCacheManager.getInstance();

			Map policies = manager.getCachePolicies();
			Iterator it = policies.keySet().iterator();
			
			while (it.hasNext()) {
				String key = (String) it.next();
				CachePolicy policy = (CachePolicy) policies.get(key);
				sb.append("SP Name : ".concat(key).concat(", "))
					.append("Cache Target : ").append(policy.isCacheTarget()).append(", ")
					.append("Fetch Size : ").append(policy.getFetchSize()).append(", ")
					.append("TTL : ").append(policy.getTimeToLive()).append(", ")
					.append("isMultiRow : ").append(policy.isMultiRow()).append(", ")
					.append("Max Count : ").append(policy.getMaxCount()).append("\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}

	
	public void setCacheTarget(String spName, boolean isTarget) {


		try {
			RedisCacheManager manager = RedisCacheManager.getInstance();

			//Default Setting
			CachePolicy policy = new CachePolicy();
			policy.setCacheTarget(isTarget);
			policy.setFetchSize(CacheFetchConstants.FETCH_ALL);
			policy.setTimeToLive(10);
			
			manager.addCachePolicy(spName, policy);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	
	public void setCacheTarget(String spName, boolean isTarget, String fetchSize) {

		try {
			RedisCacheManager manager = RedisCacheManager.getInstance();

			//Default Setting
			CachePolicy policy = new CachePolicy();
			policy.setCacheTarget(isTarget);
			policy.setFetchSize(fetchSize);
			policy.setTimeToLive(10);
			
			manager.addCachePolicy(spName, policy);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	public void setCacheTarget(String spName, boolean isTarget,
			String fetchSize, int ttl) {

		try {
			RedisCacheManager manager = RedisCacheManager.getInstance();

			//Default Setting
			CachePolicy policy = new CachePolicy();
			policy.setCacheTarget(isTarget);
			policy.setFetchSize(fetchSize);
			policy.setTimeToLive(ttl);
			
			manager.addCachePolicy(spName, policy);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	public void setCacheTarget(String spName, boolean isTarget,
			String fetchSize, int ttl, int maxCount) {
		
		try {
			RedisCacheManager manager = RedisCacheManager.getInstance();

			//Default Setting
			CachePolicy policy = new CachePolicy();
			policy.setCacheTarget(isTarget);
			if (fetchSize == null || fetchSize.isEmpty()) {
				fetchSize = "all";
			}
			policy.setFetchSize(fetchSize);
			if (ttl == 0) {
				ttl = 10;
			}
			policy.setTimeToLive(ttl);
			policy.setMaxCount(maxCount);
			
			manager.addCachePolicy(spName, policy);
			
			//Load Properties and Replace and Store.
			Properties prop = CachePropertiesHandler.loadProperties(fileName);
			String spNames = prop.getProperty("sp.names");
			
			// Already Exists.
			if (spNames.contains(spName)) {
				
				prop.remove(spName.concat(".target"));
				prop.remove(spName.concat(".multirow"));
				prop.remove(spName.concat(".fetchsize"));
				prop.remove(spName.concat(".maxcount"));
				prop.remove(spName.concat(".ttl"));
				
				prop.put(spName.concat(".target"), isTarget);
				prop.put(spName.concat(".multirow"), true);
				prop.put(spName.concat(".fetchsize"), fetchSize);
				prop.put(spName.concat(".maxcount"), maxCount);
				prop.put(spName.concat(".ttl"), ttl);
				
			} else {
				
				prop.put("sp.names", spNames.concat(",").concat(spName));
				prop.put(spName.concat(".target"), isTarget);
				prop.put(spName.concat(".multirow"), true);
				prop.put(spName.concat(".fetchsize"), fetchSize);
				prop.put(spName.concat(".maxcount"), maxCount);
				prop.put(spName.concat(".ttl"), ttl);
				
			}
			
			CachePropertiesHandler.storeProperties(fileName, prop);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
