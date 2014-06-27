package com.kt.bit.csm.blds;

import org.junit.Test;

import com.kt.bit.csm.blds.cache.config.CacheConfigNotifier;

public class CacheConfigNotifierTest {

	@Test
	public void notifierTest() {
		
		CacheConfigNotifier.watch("D:/dev_sdp/eclipse/");
	}

}
