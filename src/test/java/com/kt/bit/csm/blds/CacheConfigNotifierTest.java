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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
