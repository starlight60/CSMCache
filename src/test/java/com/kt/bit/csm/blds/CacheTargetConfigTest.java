package com.kt.bit.csm.blds;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.junit.Test;

import com.kt.bit.csm.blds.cache.jmx.CacheTargetConfig;

public class CacheTargetConfigTest {

	@Test
	public static void main(String[] args) throws MalformedObjectNameException, InterruptedException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
		
		//Get the MBean server
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		//register the MBean
		CacheTargetConfig mBean = new CacheTargetConfig();
		ObjectName name = new ObjectName("com.kt.bit.csm.blds.jmx:type=CacheTargetConfig");
		mbs.registerMBean(mBean, name);
		
		do{
            Thread.sleep(3000);
    		System.out.println(mBean.getCacheTargetPolicies());
        }while(true);	
		
	}
	
}
