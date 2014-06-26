package com.kt.bit.csm.blds;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import com.kt.bit.csm.blds.cache.CacheManager;
import org.junit.Test;

import com.kt.bit.csm.blds.cache.CacheEnvironments;
import com.kt.bit.csm.blds.cache.CachePolicy;
import com.kt.bit.csm.blds.cache.config.CacheConfigManager;
import com.kt.bit.csm.blds.cache.storage.RedisCacheManager;

public class ConfigPropertyConfigurationTest {

	private int testCase = 2;
	
	private void onlyOne() {
		
		String fileName1 = "D:/dev_sdp/eclipse/cache-config.properties";
		
		try {
			
			CacheConfigManager manager = CacheConfigManager.getInstance();
			manager.setPropertyChangeListener(fileName1, null, 100);
			
			CacheEnvironments env = CacheEnvironments.getInstance();
			RedisCacheManager rm = RedisCacheManager.getInstance();
			
			System.out.println("Cache On : " + rm.isCacheOn());
			System.out.println("Buffer Size : " + env.getBufferSize());
            System.out.println("Queue Count : " + env.getQueueCount());
			System.out.println("Min Pool Size : " + env.getMinPoolSize());
			System.out.println("Max Pool Size : " + env.getMaxPoolSize());

			while(true) {
				
				if (manager.isChanged()) {
					
					manager.setChanged(false);

					System.out.println("------Only One-----");
					System.out.println("Cache On : " + rm.isCacheOn());
					System.out.println("Buffer Size : " + env.getBufferSize());
                    System.out.println("Queue Count : " + env.getQueueCount());
					System.out.println("Min Pool Size : " + env.getMinPoolSize());
					System.out.println("Max Pool Size : " + env.getMaxPoolSize());
					
				}
				
				Thread.sleep(1000);
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void multiCase() {
		
		String fileName1 = "D:/dev_sdp/eclipse/cache-config.properties";
		String fileName2 = "D:/dev_sdp/eclipse/cache-policy.properties";
		
		try {
			
			CacheConfigManager manager = CacheConfigManager.getInstance();
			manager.setPropertyChangeListener("cache-config", fileName1, CacheManager.cacheConfigFileKey, 100);
			manager.setPropertyChangeListener("cache-policy", fileName2, CacheManager.cachePolicyFileKey, 100);
			
			CacheEnvironments env = CacheEnvironments.getInstance();
			RedisCacheManager rm = RedisCacheManager.getInstance();
			CachePolicy policy = new CachePolicy();
			
			while(true) {
				
				if (manager.isChanged("cache-config")) {
					
					manager.setChanged("cache-config", false);

					System.out.println("------cache-config-----");
					System.out.println("Cache On : " + rm.isCacheOn());
					System.out.println("Buffer Size : " + env.getBufferSize());
                    System.out.println("Queue Count : " + env.getQueueCount());
					System.out.println("Min Pool Size : " + env.getMinPoolSize());
					System.out.println("Max Pool Size : " + env.getMaxPoolSize());
					
				}
				
				if (manager.isChanged("cache-policy")) {
					
					manager.setChanged("cache-policy", false);

					Map policies = rm.getCachePolicies();
					
					if (policies == null) {
						continue;
					}
					
					System.out.println("------cache-policy-----");
					for (Object key : policies.keySet()) {
						StringBuilder sb = new StringBuilder();
						policy = (CachePolicy) policies.get(key);
						sb.append("SP Name : ").append(key).append("\n");
						sb.append("Fetch Size : ").append(policy.getFetchSize()).append("\n");
						sb.append("Max Count : ").append(policy.getMaxCount()).append("\n");
						sb.append("TTL : ").append(policy.getTimeToLive());
						System.out.println(sb.toString());
					}
					
				}
				
				Thread.sleep(1000);
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void configTest() {
		
		if (testCase == 1) {
			onlyOne();
		}
		else {
			multiCase();
		}
	}
}
