package com.kt.bit.csm.blds.cache.config;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Map;
import java.util.Properties;

import com.kt.bit.csm.blds.cache.CacheEnvironments;
import com.kt.bit.csm.blds.cache.CacheFetchConstants;
import com.kt.bit.csm.blds.cache.storage.RedisCacheManager;

public class CacheConfigNotifier {

	private static void setProperties(Properties configuration) {
		
		if (configuration == null || configuration.isEmpty()) {
			throw new IllegalArgumentException("Properties is null.");
		}
		
		String propertyName = "";
		Object value = null;
		
		for (Map.Entry<Object, Object> entry : configuration.entrySet()) {
		
	        // Set the Value in Singleton Class of Properties
			try {
		        CacheEnvironments env = CacheEnvironments.getInstance();
		        RedisCacheManager manager = RedisCacheManager.getInstance();
		        propertyName = (String) entry.getKey();
		        value = entry.getValue();
		        if (propertyName.equalsIgnoreCase("min.threads.pool")) {
		        	env.setMinPoolSize(Integer.valueOf(value.toString()));
		        }
		        else if (propertyName.equalsIgnoreCase("max.threads.pool")) {
		        	env.setMaxPoolSize(Integer.valueOf(value.toString()));
		        }
		        else if (propertyName.equalsIgnoreCase("buffer.size")) {
                    env.setBufferSize(Integer.valueOf(value.toString()));
                }
                else if (propertyName.equalsIgnoreCase("queue.count")) {
                    env.setQueueCount(Integer.valueOf(value.toString()));
                }
                else if (propertyName.equalsIgnoreCase("cacheon")) {
		        	manager.setCacheOn(Boolean.valueOf(value.toString()));;
		        }
		        
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	private static Properties readProperties(Path path) {
		
		//Properties Loading...
		Properties configuration = null;
		File file = path.toFile();
		
		if (file.exists()) {
			long size = file.length();
			
			if (size > CacheFetchConstants.FETCH_FILE_SIZE_LIMIT) {
				System.out.println("Property File Size cannot over 10M.");
				return configuration;
			}
			
		}
		
		InputStream in = null;
		try {
			configuration = new Properties();
			in = new FileInputStream(file);
			configuration.load(in);
		} catch (FileNotFoundException e) {
            // TODO: Add logging
			e.printStackTrace();
		} catch (IOException e) {
            // TODO: Add logging
			e.printStackTrace();
		}
		
		return configuration;
	}
	
	public static void watch(String dir) {
		
		Path path = Paths.get(dir);	// Get the directory to be monitored
		
		// Check if path is a folder
		try {
			Boolean isFolder = (Boolean) Files.getAttribute(path, "basic:isDirectory", NOFOLLOW_LINKS);
			if (!isFolder) {
				throw new IllegalArgumentException("Path: " + dir + " is not a folder");
			}
		} catch (IOException ioe) {
			// Folder does not exists
			ioe.printStackTrace();
		}
		
		try {
			
			// Create a WatchService
			WatchService service = FileSystems.getDefault().newWatchService();
			
			path.register(service, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);	// Register the directory
			
			// Start the infinite polling loop
			WatchKey key = null;
			
			while(true) {
				
				key = service.take();	// retrieve the watchkey
				
				// Dequeueing events
				Kind<?> kind = null;
				for (WatchEvent<?> watchEvent : key.pollEvents()) {
					
					// Get the type of the event
					kind = watchEvent.kind();
					
					if (kind == StandardWatchEventKinds.OVERFLOW) {
						continue;
					} else if (StandardWatchEventKinds.ENTRY_CREATE == kind) {
						// A new Path was created 
						Path newPath = ((WatchEvent<Path>) watchEvent).context();
						System.out.println("New path created: " + newPath);
						
						//Properties Loading...
						Properties configuration = readProperties(newPath);
						
						//Properties Setting....
						setProperties(configuration);
						
					} else if (StandardWatchEventKinds.ENTRY_MODIFY == kind) {
						// A Path was modified 
						Path modifiedPath = ((WatchEvent<Path>) watchEvent).context();
						System.out.println("Modified path: " + modifiedPath);
						
						//Properties Loading...
						Properties configuration = readProperties(modifiedPath);
						
						//Properties Setting....
						setProperties(configuration);
						
					} else if (StandardWatchEventKinds.ENTRY_DELETE == kind) {
						// A Path was delete 
						Path deletedPath = ((WatchEvent<Path>) watchEvent).context();
						System.out.println("Deleted path: " + deletedPath);					
					}
				}
				
				boolean valid = key.reset();
				
				if (!valid) {
					break;
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
