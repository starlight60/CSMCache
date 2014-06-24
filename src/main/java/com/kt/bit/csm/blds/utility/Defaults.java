/************************************************************************************************************	
Package Name:	com.kt.bit.csm.blds.utility
Author:			Pushpendra Pandey
Description:	
This package contains utility classes like logging and Data access framework of CSM 

Modification Log:	
When                           Version   			Who					 What	
21-12-2010                     1.0                  Pushpendra Pandey    New class created
----------------------------------------------------------------------------------------------------------	
***************************************************************************************************************/

package com.kt.bit.csm.blds.utility;

/**************************************************************************
Defaults
========
This class contains default values to load in message data structure when 
no value is specified.
**************************************************************************/
public class Defaults {
	public static final String	NODATA_STRING	  = "";
	public static final int	   NODATA_INT	      = -1;
	//	public static String LEVEL = LogLevel.DEBUG;
	public static String	   bldsComponentValue	= "BLDS";
	public static String	   defaultCategory	  = "Success";
	public static String	   blpComponentValue	= "BLP";
}
