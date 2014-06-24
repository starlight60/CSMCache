/************************************************************************************************************
 Package Name:	com.kt.bit.csm.blds.utility
 Author:			Pushpendra Pandey
 Description:
 This package contains utility classes to access the DB

 Modification Log:
 When                           Version   			Who					 What
 21-12-2010                     1.0                  Pushpendra Pandey    New class created
 04-04-2011					   2.0					Jasmine Kua			 Modified according to RESORT result
 04-04-2011					   3.0					Jasmine Kua			 Refer the JNDI name from Constants.java
 ----------------------------------------------------------------------------------------------------------
 ***************************************************************************************************************/
package com.kt.bit.csm.blds.utility;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.kt.bit.csm.blds.common.Constants;

/**************************************************************************
 DBAccessor
 ==========
 This class manages the connection to the database
 **************************************************************************/
public final class DBAccessor implements Serializable {

    private static final long	serialVersionUID	= -365692321130149898L;
    private DataSource	      ds;

    private String	  driver;
    private String	  url;
    private String	  user;
    private String    psw;

    private boolean	          jndiUsing	         = true;

    /**************************************************************************
     DBAccessor
     ==========
     Default constructor.
     @exception - Exception
     **************************************************************************/
    private DBAccessor() {

        jndiUsing = false;
            try {
                driver = "oracle.jdbc.driver.OracleDriver";//ConfigUtil.getString("DRIVER");
                url = "jdbc:oracle:thin:@localhost:1521:orcl";//ConfigUtil.getString("URL");
                user = "test";//ConfigUtil.getString("USER");
                psw = "test";//ConfigUtil.getString("PSW");
            } catch (Exception e) {
                e.printStackTrace();
                throw new SDPException("-1", "fail to connect to database", "", e);
            }


    }

    /**************************************************************************
     closeConnection
     ===============
     This method takes the connection Request and close the Connection
     @param con
     @return boolean
     @exception - SQLException
     **************************************************************************/
    public static boolean closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
            return true;
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        return false;
    }

    /**************************************************************************
     commit
     ======
     This method takes the connection Request and close the Connection
     @param con
     @exception - SQLException
     **************************************************************************/
    public static void commit(Connection con) throws SQLException {
        try {
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**************************************************************************
     getInstance
     ============
     This method will return Instance of the DBAccessor class
     @return DBAccessor myInstance
     @exception - SQLException
     **************************************************************************/
    public static DBAccessor getInstance() throws Exception {
        return new DBAccessor();
    }

    /**************************************************************************
     rollBack
     ========
     This method will return Instance of the DBAccessor class
     @param con Connection
     @exception - SQLException
     **************************************************************************/
    public static void rollBack(Connection con) throws SQLException {
        try {
            con.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
		/*finally
		{
			if ( con != null )
			{
				con.close();
				con = null;
			}
		}*/
    }

    /**************************************************************************
     getConnection
     =============
     This method will return the DB Connection
     @return Connection cs
     @exception - SQLException
     **************************************************************************/
    public Connection getConnection() throws Exception {
        Connection con = null;

        if (jndiUsing) {
            try {
                con = ds.getConnection();
                con.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            try {

                Thread.currentThread().getContextClassLoader().loadClass(this.driver).newInstance();
                con = DriverManager.getConnection(this.url, this.user, this.psw);
                con.setAutoCommit(false);
            } catch (SQLException e) {
               e.printStackTrace();
                throw e;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();

                throw e;
            }
        }

        return con;
    }
}