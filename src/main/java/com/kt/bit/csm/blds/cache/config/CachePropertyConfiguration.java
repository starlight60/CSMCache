package com.kt.bit.csm.blds.cache.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.kt.bit.csm.blds.cache.CacheFetchConstants;
import com.kt.bit.csm.blds.cache.CachePolicy;
import com.kt.bit.csm.blds.cache.storage.RedisCacheManager;

public class CachePropertyConfiguration extends AbstractConfiguration {

	private File file;
	private long lastModified = 0;
	
	private void init(String filename) {
		
		try {
			
		    file = new File(filename);

			//Config Save only if JVM Option config file path.
	        if (file.getPath().contains("/") || file.getPath().contains("\\")) {
	    	    if (!file.exists()) {
	    		      storeProperties();
	    		    }
	        }
		    
			loadProperties();
					    
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	    
	}
	
	public CachePropertyConfiguration(String filename, int period) {
	    super(period);
	    init(filename);
	}

	private void loadProperties() {
		Properties properties = new Properties();

		//Check Property File Size
		if (file.isFile() && file.exists()) {
			long size = file.length();
			
			if (size > CacheFetchConstants.FETCH_FILE_SIZE_LIMIT) {
				System.out.println("Proeprty File Size cannot over 10M.");
				return;
			}
		}
		
		//JVM Option config file
        if (file.getPath().contains("/") || file.getPath().contains("\\")) {
            try {
				properties.load(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        else {
            try {
				properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(file.getName()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

		try {
			setAllProperties(properties);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setAllProperties(Properties properties) throws IOException {

		// Cache Policy Check
		if (properties.containsKey("sp.names")) {
			
			String spNamesKey = properties.getProperty("sp.names");
			if (spNamesKey == null || spNamesKey.isEmpty()) {
				System.out.println("Does not exist Cached Stored Procedures in Property File.");
				return;
			}
			
			String[] spNames = spNamesKey.split(",");
			RedisCacheManager manager = RedisCacheManager.getInstance();
			
			String target = "";
			String fetchSize = "";
			String maxCount = "";
			String multiRow = "";
			String ttl = "";
			
			//Check Removed Policy and Remove at memory
			@SuppressWarnings("unchecked")
			Map<String, CachePolicy> policies = manager.getCachePolicies();
			String key = "";
			
			for (Entry<String, CachePolicy> policy : policies.entrySet()) {
				key = policy.getKey();
				
				if (!spNamesKey.contains(key)) {
					manager.delCachePolicy(key);
				}
			}
			
			//Add Policies
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

	protected void checkForPropertyChanges() {
		if (lastModified != file.lastModified()) {
			isChanged = true;
			lastModified = file.lastModified();
			loadProperties();
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
