package com.kt.bit.csm.blds.cache.jmx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class CachePropertiesHandler {

	public static void storeProperties(String fileName, Properties properties) {
		
		try {
			File file = new File(fileName);
			properties.store(new FileOutputStream(file), "");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Properties loadProperties(String fileName) {
		
		try {
			File file = new File(fileName);
			Properties properties = new Properties();
			properties.load(new FileInputStream(file));
			
			return properties;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
