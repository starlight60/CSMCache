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
SDPExceptionInterface
=====================
SDPExceptionInterface defines getter method to get error code and error description.
**************************************************************************/
public interface SDPExceptionInterface {
	String getErrorDescription();
	String getErrorCode();
}
