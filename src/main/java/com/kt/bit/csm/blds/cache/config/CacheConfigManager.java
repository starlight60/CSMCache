package com.kt.bit.csm.blds.cache.config;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CacheConfigManager {

	public static CacheConfigManager configManager = new CacheConfigManager();
	Properties defaults = null;
	Map<String, CacheConfiguration> cacheConfigMap = new HashMap<String, CacheConfiguration>();
	CacheConfiguration cfg = null;
	
	public static CacheConfigManager getInstance() {
		return configManager;
	}
	
	private void setDefaultConfig() {

		defaults = new Properties();
		defaults.setProperty("cacheon", "true");
		defaults.setProperty("queue.size", "1000");
		defaults.setProperty("min.threads.pool", "100");
		defaults.setProperty("max.threads.pool", "200");
		
	}
	
	private CacheConfigManager() {
		setDefaultConfig();
	}
	
	public void setPropertyChangeListener(String fileName, int period) {
		
		try {
			
			if (defaults == null) {
				cfg = new CachePropertyConfiguration(fileName, period);
			}
			else {
				cfg = new CachePropertyConfiguration(defaults, fileName, period);
			}
			
			cfg.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					System.out.println("Property " + evt.getPropertyName() + " has now changed from <" + evt.getOldValue() + "> to <" + evt.getNewValue() + ">");
				}
			});
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setPropertyChangeListener(String keyword, String fileName, int period) {
		
		try {
			if (cacheConfigMap.containsKey(keyword)) {
				throw new IllegalArgumentException("Configuration Change Listener for [" + keyword + "] already exists.");
			}
			
			CacheConfiguration keyCfg = null;
			
			if (defaults == null) {
				keyCfg = new CachePropertyConfiguration(fileName, period);
			}
			else {
				keyCfg = new CachePropertyConfiguration(defaults, fileName, period);
			}
			
			keyCfg.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					System.out.println("Property " + evt.getPropertyName() + " has now changed from <" + evt.getOldValue() + "> to <" + evt.getNewValue() + ">");
				}
			});
			
			cacheConfigMap.put(keyword, keyCfg);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean isChanged() {
		return cfg.isChanged();
	}
	
	public boolean isChanged(String keyword) {
		CacheConfiguration keyCfg = cacheConfigMap.get(keyword);
		return keyCfg.isChanged();
	}
	
	public void setChanged(boolean flag) {
		cfg.setChanged(flag);
	}

	public void setChanged(String keyword, boolean flag) {
		CacheConfiguration keyCfg = cacheConfigMap.get(keyword);
		keyCfg.setChanged(flag);
	}
	
}
