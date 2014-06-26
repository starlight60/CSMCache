package com.kt.bit.csm.blds.utility;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyManager {

    public static Properties loadPropertyFromFile(final String path, final String javaPropertyName) throws IOException, IllegalArgumentException {
        Properties properties = new Properties();

        try {
            if (StringUtils.isEmpty(javaPropertyName))
                properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(path));
            else {
                final String javaPropertyValue = System.getProperty(javaPropertyName);
                if (StringUtils.isEmpty(javaPropertyValue)) {
                    properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(path));
                } else {

                    File file = new File(javaPropertyValue);
                    if (!file.exists()) {
                        throw new IOException(javaPropertyName+" is not exists");
                    }
                    if (!file.isFile()) {
                        throw new IOException(javaPropertyName+" must be in file");
                    }
                    properties.load(new FileInputStream(file));

                }
            }
        } catch (NullPointerException e) {
            System.err.println(e.getMessage()+",arguments:path->"+path+",javaPropertyName->"+javaPropertyName);
        }

        return properties;
    }


}
