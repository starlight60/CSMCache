package com.kt.bit.csm.blds;

import java.io.IOException;

import org.junit.Test;

import com.kt.bit.csm.blds.cache.config.CacheConfigNotifier;

public class CacheConfigNotifierTest {

	@Test
	public void notifierTest() {
		
		try {
			CacheConfigNotifier.watch("D:/dev_sdp/eclipse/");			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
