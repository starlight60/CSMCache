package com.kt.bit.csm.blds;

import java.io.IOException;

import org.junit.Test;

import com.kt.bit.csm.blds.cache.CacheEnvironments;
import com.kt.bit.csm.blds.cache.config.CacheConfigManager;
import com.kt.bit.csm.blds.cache.storage.RedisCacheManager;

public class ConfigPropertyConfigurationTest {

	private int testCase = 2;
	
	private void onlyOne() {
		
		String fileName1 = "D:/dev_sdp/eclipse/cache-config.properties";
		
		try {
			
			CacheConfigManager manager = CacheConfigManager.getInstance();
			manager.setPropertyChangeListener(fileName1, 100);
			
			CacheEnvironments env = CacheEnvironments.getInstance();
			RedisCacheManager rm = RedisCacheManager.getInstance();
			
			System.out.println("Cache On : " + rm.isCacheOn());
			System.out.println("Queue Size : " + env.getQueueSize());
			System.out.println("Min Pool Size : " + env.getMinPoolSize());
			System.out.println("Max Pool Size : " + env.getMaxPoolSize());

			while(true) {
				
				if (manager.isChanged()) {
					
					manager.setChanged(false);

					System.out.println("------Only One-----");
					System.out.println("Cache On : " + rm.isCacheOn());
					System.out.println("Queue Size : " + env.getQueueSize());
					System.out.println("Min Pool Size : " + env.getMinPoolSize());
					System.out.println("Max Pool Size : " + env.getMaxPoolSize());
					
				}
				
				Thread.sleep(1000);
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void multiCase() {
		
		String fileName1 = "D:/dev_sdp/eclipse/cache-config.properties";
		String fileName2 = "D:/dev_sdp/eclipse/cache-config2.properties";
		
		try {
			
			CacheConfigManager manager = CacheConfigManager.getInstance();
			manager.setPropertyChangeListener("Config1", fileName1, 100);
			manager.setPropertyChangeListener("Config2", fileName2, 100);
			
			CacheEnvironments env = CacheEnvironments.getInstance();
			RedisCacheManager rm = RedisCacheManager.getInstance();
			
			System.out.println("Cache On : " + rm.isCacheOn());
			System.out.println("Queue Size : " + env.getQueueSize());
			System.out.println("Min Pool Size : " + env.getMinPoolSize());
			System.out.println("Max Pool Size : " + env.getMaxPoolSize());

			while(true) {
				
				if (manager.isChanged("Config1")) {
					
					manager.setChanged("Config1", false);

					System.out.println("------Config1-----");
					System.out.println("Cache On : " + rm.isCacheOn());
					System.out.println("Queue Size : " + env.getQueueSize());
					System.out.println("Min Pool Size : " + env.getMinPoolSize());
					System.out.println("Max Pool Size : " + env.getMaxPoolSize());
					
				}
				
				if (manager.isChanged("Config2")) {
					
					manager.setChanged("Config2", false);

					System.out.println("------Config2-----");
					System.out.println("Cache On : " + rm.isCacheOn());
					System.out.println("Queue Size : " + env.getQueueSize());
					System.out.println("Min Pool Size : " + env.getMinPoolSize());
					System.out.println("Max Pool Size : " + env.getMaxPoolSize());
					
				}
				
				Thread.sleep(1000);
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
