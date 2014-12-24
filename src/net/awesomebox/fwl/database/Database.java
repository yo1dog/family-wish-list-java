package net.awesomebox.fwl.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.sql.DataSource;

/**
 * Manages the database connections.
 * 
 * @author michael.moore
 */

public class Database
{
	/** Source to get connections from. */
	private static DataSource datasource = null;
	
	
	
	//================================================================================
    // Initiate
	//================================================================================
	
	public static void init()
	{
		try
		{
			InitialContext initialContext = new InitialContext();
			
			// actual jndi name is "jdbc/postgres"
			datasource = (DataSource)initialContext.lookup("java:/comp/env/jdbc/postgres");
			
			if (datasource == null)
				throw new ServletException("Unable to find data source.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	//================================================================================
    // Connection Management
	//================================================================================
	
	
	/**
	 * Gets a connection to the database.
	 * 
	 * @return
	 * 	Connection to the database.
	 * 
	 * @throws SQLException
	 */
	public static synchronized Connection getConnection() throws SQLException
	{
		return datasource.getConnection();
	}
	
	/**
	 * Closes a connection to the database.
	 * 
	 * @param connection
	 * 	Database connection to close.
	 * 
	 * @throws SQLException
	 */
	public static synchronized void closeConnection(Connection connection) throws SQLException
	{
		connection.close();
	}
	
	
	
	//================================================================================
    // Helper Methods
	//
	//  Provides methods for easing the transfer of null values to and from the
	//  database.
	//================================================================================
	
	//--------------------------------------------------------------------------------
	// Integer
	//--------------------------------------------------------------------------------
	
	/**
	 * Gets a possibly null integer column value from a result set.
	 * 
	 * @param rs
	 * @param columnLabel
	 * @return
	 * @throws SQLException
	 * 
	 * @see java.sql.ResultSet#getInt
	 */
	public static Integer gnvInt(ResultSet rs, String columnLabel) throws SQLException
	{
		Integer value = rs.getInt(columnLabel);
		return rs.wasNull() ? null : value;
	}
	/**
	 * Gets a possibly null integer column value from a result set.
	 * 
	 * @param rs
	 * @param columnIndex
	 * @return
	 * @throws SQLException
	 * 
	 * @see java.sql.ResultSet#getInt
	 */
	public static Integer gnvInt(ResultSet rs, int columnIndex) throws SQLException
	{
		Integer value = rs.getInt(columnIndex);
		return rs.wasNull() ? null : value;
	}
	
	/**
	 * Sets a possibly null integer column value in a prepared statement.
	 * 
	 * @param st
	 * @param parameterIndex
	 * @param value
	 * @throws SQLException
	 * 
	 * @see java.sql.ResultSet#setInt
	 */
	public static void snvInt(PreparedStatement st, int parameterIndex, Integer value) throws SQLException
	{
		if (value == null)
			st.setNull(parameterIndex, java.sql.Types.INTEGER);
		else
			st.setInt(parameterIndex, value);
	}
	
	//--------------------------------------------------------------------------------
	// Short
	//--------------------------------------------------------------------------------
	
	/**
	 * Gets a possibly null short column value from a result set.
	 * 
	 * @param rs
	 * @param columnLabel
	 * @return
	 * @throws SQLException
	 * 
	 * @see java.sql.ResultSet#getShort
	 */
	public static Short gnvShort(ResultSet rs, String columnLabel) throws SQLException
	{
		Short value = rs.getShort(columnLabel);
		return rs.wasNull() ? null : value;
	}
	/**
	 * Gets a possibly null short column value from a result set.
	 * 
	 * @param rs
	 * @param columnIndex
	 * @return
	 * @throws SQLException
	 * 
	 * @see java.sql.ResultSet#getShort
	 */
	public static Short gnvShort(ResultSet rs, int columnIndex) throws SQLException
	{
		Short value = rs.getShort(columnIndex);
		return rs.wasNull() ? null : value;
	}
	
	/**
	 * Sets a possibly null short column value in a prepared statement.
	 * 
	 * @param st
	 * @param parameterIndex
	 * @param value
	 * @throws SQLException
	 * 
	 * @see java.sql.ResultSet#setShort
	 */
	public static void snvShort(PreparedStatement st, int parameterIndex, Short value) throws SQLException
	{
		if (value == null)
			st.setNull(parameterIndex, java.sql.Types.SMALLINT);
		else
			st.setShort(parameterIndex, value);
	}
	
	
	//--------------------------------------------------------------------------------
	// Boolean
	//--------------------------------------------------------------------------------
	
	/**
	 * Gets a possibly null boolean column value from a result set.
	 * 
	 * @param rs
	 * @param columnLabel
	 * @return
	 * @throws SQLException
	 * 
	 * @see java.sql.ResultSet#getBoolean
	 */
	public static Boolean gnvBoolean(ResultSet rs, String columnLabel) throws SQLException
	{
		Boolean value = rs.getBoolean(columnLabel);
		return rs.wasNull() ? null : value;
	}
	/**
	 * Gets a possibly null boolean column value from a result set.
	 * 
	 * @param rs
	 * @param columnIndex
	 * @return
	 * @throws SQLException
	 * 
	 * @see java.sql.ResultSet#getBoolean
	 */
	public static Boolean gnvBoolean(ResultSet rs, int columnIndex) throws SQLException
	{
		Boolean value = rs.getBoolean(columnIndex);
		return rs.wasNull() ? null : value;
	}
	
	/**
	 * Sets a possibly null boolean column value in a prepared statement.
	 * 
	 * @param st
	 * @param parameterIndex
	 * @param value
	 * @throws SQLException
	 * 
	 * @see java.sql.PreparedStatement#setBoolean
	 */
	public static void snvBoolean(PreparedStatement st, int parameterIndex, Boolean value) throws SQLException
	{
		if (value == null)
			st.setNull(parameterIndex, java.sql.Types.BOOLEAN);
		else
			st.setBoolean(parameterIndex, value);
	}
}
