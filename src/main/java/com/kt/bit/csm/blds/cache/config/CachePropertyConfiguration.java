package com.kt.bit.csm.blds.cache.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import com.kt.bit.csm.blds.cache.CachePolicy;
import com.kt.bit.csm.blds.cache.storage.RedisCacheManager;

public class CachePropertyConfiguration extends AbstractConfiguration {

	private final File file;
	private long lastModified = 0;
	
	public CachePropertyConfiguration(String filename, int period) throws IOException {
	    super(period);
	    file = new File(filename);
	    if (!file.exists()) {
	      storeProperties();
	    }
	    loadProperties();
	}

	
	private void loadProperties() throws IOException {
		Properties properties = new Properties();
		properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(file.getName()));
		setAllProperties(properties);
	}

	private void setAllProperties(Properties properties) throws IOException {

		// Cache Policy Check
		if (properties.containsKey("sp.names")) {
			
			String[] spNames = properties.getProperty("sp.names").split(",");
			RedisCacheManager manager = RedisCacheManager.getInstance();
			
			String target = "";
			String fetchSize = "";
			String maxCount = "";
			String multiRow = "";
			String ttl = "";
			
			for (String spName : spNames) {
				
				target = properties.getProperty(spName.concat(".target"), "true");
				fetchSize = properties.getProperty(spName.concat(".fetchsize"), "all");
				maxCount = properties.getProperty(spName.concat(".maxCount"), "1000");
				multiRow = properties.getProperty(spName.concat(".multirow"), "true");
				ttl = properties.getProperty(spName.concat(".ttl"), "10");
				
				CachePolicy policy = new CachePolicy();
				policy.setCacheTarget(Boolean.valueOf(target));
				policy.setFetchSize(fetchSize);
				policy.setMaxCount(Integer.valueOf(maxCount));
				policy.setMultiRow(Boolean.valueOf(multiRow));
				policy.setTimeToLive(Integer.valueOf(ttl));
				
				manager.addCachePolicy(spName, policy);
			}
		}
		
		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			setProperty((String) entry.getKey(), entry.getValue());
		}
	}
	
	public void storeProperties() {
		Properties properties = new Properties();
		for (Map.Entry<String, Object> entry : getAllProperties()) {
			properties.put(entry.getKey(), entry.getValue());
		}
		
		try {
			properties.store(new FileOutputStream(file), "Cache Configuration File is saved.");
		} catch (IOException e) {
			ConfigException.throwException(e);
		}
	}
	
	@Override
	protected void checkForPropertyChanges() {
		if (lastModified != file.lastModified()) {
			isChanged = true;
			try {
				lastModified = file.lastModified();
				loadProperties();				
			} catch (IOException e) {
				ConfigException.throwException(e);
			}
		}
	}

	public boolean isChanged() {
		return isChanged;
	}
	
	public void setChanged(boolean flag) {
		isChanged = flag;
	}
	
	private boolean isChanged = false;
}
