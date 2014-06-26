package com.kt.bit.csm.blds.cache.config;

import com.kt.bit.csm.blds.cache.CacheManager;
import com.kt.bit.csm.blds.cache.storage.RedisCacheManager;
import org.apache.commons.lang.StringUtils;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CacheConfigManager {

	public static CacheConfigManager configManager = new CacheConfigManager();
	Map<String, CacheConfiguration> cacheConfigMap = new HashMap<String, CacheConfiguration>();
	CacheConfiguration cfg = null;
	
	public static CacheConfigManager getInstance() {
		return configManager;
	}

	private CacheConfigManager() {
	}
	
	public void setPropertyChangeListener(String fileName, String propertyKey, int period) throws IOException {

        String propertyValue = null;
        if (StringUtils.isNotEmpty(propertyKey)) propertyValue = System.getProperty(propertyKey);

        if (StringUtils.isNotEmpty(propertyValue))
            cfg = new CachePropertyConfiguration(propertyValue, period);
        else
            cfg = new CachePropertyConfiguration(fileName, period);

        cfg.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                // TODO: Add logging
                System.out.println("Property " + evt.getPropertyName() + " has now changed from <" + evt.getOldValue() + "> to <" + evt.getNewValue() + ">");
            }
        });
		
	}
	
	public void setPropertyChangeListener(String keyword, String fileName, String propertyKey, int period) throws IOException {

        if (cacheConfigMap.containsKey(keyword)) {
            throw new IllegalArgumentException("Configuration Change Listener for [" + keyword + "] already exists.");
        }

        String propertyValue = null;
        if (StringUtils.isNotEmpty(propertyKey)) propertyValue = System.getProperty(propertyKey);

        CacheConfiguration keyCfg;
        if (StringUtils.isNotEmpty(propertyValue))
            keyCfg = new CachePropertyConfiguration(propertyValue, period);
        else
            keyCfg = new CachePropertyConfiguration(fileName, period);

        keyCfg.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                // TODO: Add logging
            System.out.println("Property " + evt.getPropertyName() + " has now changed from <" + evt.getOldValue() + "> to <" + evt.getNewValue() + ">");
            }
        });

        cacheConfigMap.put(keyword, keyCfg);
		
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
