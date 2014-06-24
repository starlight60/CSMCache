package com.kt.bit.csm.blds.jmx;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import com.kt.bit.csm.blds.cache.CacheFetchConstants;
import com.kt.bit.csm.blds.cache.CachePolicy;
import com.kt.bit.csm.blds.cache.storage.RedisCacheManager;

public class CacheTargetConfig implements CacheTargetConfigMBean {

	@Override
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

	@Override
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
					.append("isTarget : ").append(policy.isCacheTarget()).append(", ")
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

	@Override
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

	@Override
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

	@Override
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

	@Override
	public void setCacheTarget(String spName, boolean isTarget,
			String fetchSize, int ttl, int maxCount) {
		
		try {
			RedisCacheManager manager = RedisCacheManager.getInstance();

			//Default Setting
			CachePolicy policy = new CachePolicy();
			policy.setCacheTarget(isTarget);
			policy.setFetchSize(fetchSize);
			policy.setTimeToLive(ttl);
			policy.setMaxCount(maxCount);
			
			manager.addCachePolicy(spName, policy);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
