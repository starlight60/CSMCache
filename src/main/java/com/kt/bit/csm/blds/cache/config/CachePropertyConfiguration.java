package com.kt.bit.csm.blds.cache.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

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
	
	public CachePropertyConfiguration(Properties defaults, String filename, int period) throws IOException {
	    super(period);
	    setAllProperties(defaults);
	    file = new File(filename);
	    if (!file.exists()) {
	      storeProperties();
	    }
	    loadProperties();
	}
	
	private void loadProperties() throws IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(file));
		setAllProperties(properties);
	}

	private void setAllProperties(Properties properties) {
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
