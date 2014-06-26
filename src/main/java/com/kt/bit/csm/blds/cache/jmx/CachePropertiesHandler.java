package com.kt.bit.csm.blds.cache.jmx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class CachePropertiesHandler {

	public static void storeProperties(String fileName, Properties properties) throws IOException {

        File file = new File(fileName);
        properties.store(new FileOutputStream(file), "");

	}
	
	public static Properties loadProperties(String fileName) throws IOException {

        File file = new File(fileName);
        Properties properties = new Properties();
        properties.load(new FileInputStream(file));

        return properties;
	}
}
