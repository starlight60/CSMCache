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

import com.kt.bit.csm.blds.common.Constants;

import java.rmi.dgc.VMID;

/**************************************************************************
SDPException
============
SDPException class extends RuntimeException class to define a user defined 
exception.
**************************************************************************/
public class SDPException extends RuntimeException implements
        SDPExceptionInterface {

	//	private static LoggingManager logger = new LoggingManager(
	//			SDPException.class.getName());
	private static final long	serialVersionUID	= -3230643208121814012L;
	private String	          errorCause	     = Defaults.NODATA_STRING;
	private String	          errorCode	         = Defaults.NODATA_STRING;
	private String	          errorDescription	 = Defaults.NODATA_STRING;
	private String	          exceptionID	     = "";

	//	private SDPException() {
	//
	//	}



	/**************************************************************************
	SDPException
	============
	This method takes the request and set error code and error description

	@param errorCode - error code
	@param errorMessage - error message
	**************************************************************************/
	public SDPException(String errorCode, String errorMessage) {
	    super( errorMessage );
		if ( "0".equals( errorCode ) ) {
			this.errorCode = Constants.EXCEPTION_ERROR_CODE;
		} else {
			this.errorCode = errorCode;
		}
		
		this.errorDescription = errorMessage;
	}

	/**************************************************************************
	SDPException
	============
	This method takes the request and set them to Exception

	@param errorCode - Error Code
	@param errorDescription
	@param errorCause
	@param ex
	**************************************************************************/
	public SDPException(String errorCode, String errorDescription,
                        String errorCause, Exception ex) {
		super(ex);
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
		this.errorCause = errorCause;

		VMID d = new VMID();
		exceptionID = d.toString();

	}

	/**************************************************************************
	getCategory
	============
	This method will return the categoryName by calling the
	loadCategoryNameByCategoryID
	
	@return void
	 **************************************************************************/
//	public String getCategory() {
//		String categoryID = errorCode.substring(2, 4);
//		String categoryName = null;
//		try {
//			categoryName = ErrorDescription.loadCategoryNameByCategoryID(categoryID);
//		} catch (Exception e1) {
//			ExceptionHandlerManager.handleException(new Exception(
//			        "Could not find exception category file: "
//			                + PropertiesFilenames.EXCEPTIONS_CATEGORY));
//			//			JavaMessage javaMsg = new JavaMessage(
//			//					"Could not open exception category file: "
//			//							+ PropertiesFilenames.EXCEPTIONS_CATEGORY);
//			//			javaMsg.setLEVEL("ERROR");
//			//			logger.logMessage(javaMsg);
//		}
//
//		return categoryName;
//	}

	/**************************************************************************
	getComponent
	============
	This method will Get first two digits of errorCode to load component Name
	
	@return void
	 **************************************************************************/
//	public String getComponent() {
//		// Get first two digits of errorCode to load component Name
//		String componentID = errorCode.substring(0, 2);
//		// System.out.println("DEBUG: componentID: " + componentID);
//		String componentName = null;
//		try {
//			componentName = ErrorDescription.loadComponentNameByComponentID(componentID);
//		} catch (Exception e1) {
//			ExceptionHandlerManager.handleException(new Exception("Could not find componentsID in cache"));
//			//			JavaMessage javaMsg = new JavaMessage(
//			//					"Could not open componentsID file: "
//			//							+ PropertiesFilenames.COMPONENTIDS_FILENAME);
//			//			javaMsg.setLEVEL("ERROR");
//			//			logger.logMessage(javaMsg);
//		}
//
//		return componentName;
//	}

	public String getErrorCause() {
		return errorCause;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public String getExceptionID() {
		return exceptionID;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setErrorDescription(final String errorDescription) {
		this.errorDescription = errorDescription;
	}
}