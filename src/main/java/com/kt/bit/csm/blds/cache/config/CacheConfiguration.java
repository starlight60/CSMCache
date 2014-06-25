package com.kt.bit.csm.blds.cache.config;

import java.beans.PropertyChangeListener;
import java.util.Map;
import java.util.Set;

public interface CacheConfiguration {

	// Get Property : (String key, Object value) pair.
	Object getProperty(String propertyName);
	// Get All Properties
	Set<Map.Entry<String,Object>> getAllProperties();
	// Add Property Listener for monitoring file Changed.
	void addPropertyChangeListener(PropertyChangeListener listener);
	// Remove Property Listener
	boolean removePropertyChangeListener(PropertyChangeListener listener);
	boolean isChanged();
	void setChanged(boolean flag);
}
