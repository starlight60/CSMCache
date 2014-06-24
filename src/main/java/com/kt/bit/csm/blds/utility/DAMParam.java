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
DAMParam
========
This class defines the kind of object that should be passed to methods of 
DataAccessManager to execute procedure.
**************************************************************************/
public class DAMParam

{
	private final Object   value;
	private final int	   type;
	private final String   paramName;

	/**************************************************************************
	DAMParam
	========
	This constructor create the DAMParam, in order to send build an array of
	DAMParams to execute the stored procedure
	
	@param param_name - parameters name as defined in the stored procedure
	@param value - of the input object for the stored procedure
	@param oracleType - of this parameter
	**************************************************************************/
	public DAMParam(String param_name, Object value, int oracleType) {

		this.value = value;
		this.type = oracleType;
		this.paramName = param_name;
	}

	/**************************************************************************
	getValue
	========
	This method returns the value of the Object stored in the class.
	@return Object m_name value of the Object stored in the class.
	**************************************************************************/
	public Object getValue() {
		return value;
	}

	/**************************************************************************
	getType
	=======
	This method returns the Oracle type of the Object stored in the class.
	@return int type SQL Type of the Object stored in the class. This is an integer value.
	**************************************************************************/
	public int getType() {
		return type;
	}

	/**************************************************************************
	getParamName
	============
	@return String param_name the parameter name in order to call the stored
	       procedure
	**************************************************************************/
	public String getParamName() {
		return paramName;
	}
}