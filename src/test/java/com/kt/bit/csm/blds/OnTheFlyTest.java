package com.kt.bit.csm.blds;

import com.kt.bit.csm.blds.cache.storage.RedisCacheManager;
import com.kt.bit.csm.blds.utility.DataAccessManager;
import org.junit.Test;

import java.io.IOException;


public class OnTheFlyTest {

	@Test
	public void onthefly() throws IOException {
		
		//SETTING
		RedisCacheManager manager = RedisCacheManager.getInstance();
		
		//Cache ON
		System.out.println("Cache ON : " + manager.isCacheOn());
		System.out.println("SET Cache ON True");
		manager.setCacheOn(true);
		System.out.println("Cache ON : " + manager.isCacheOn());
		
		//SP_NAME, Policy
		manager.addCachePolicy("SP_NAME_TEST1", true, false, 1);
		manager.addCachePolicy("SP_NAME_TEST2", true, true, -1);
		manager.addCachePolicy("SP_NAME_TEST3", true, true, 1);
		manager.addCachePolicy("SP_NAME_TEST4", true, true, 100);
		
		try {
			DataAccessManager dam = new DataAccessManager();
			dam.executeStoredProcedureForQuery("SP_NAME_TEST1", "", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
