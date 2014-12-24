package net.awesomebox.fwl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLWarning;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.awesomebox.fwl.database.Database;
import net.awesomebox.servletmanager.ManagedHttpServlet;

public class FWLManagedHttpServlet extends ManagedHttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void afterRequests(HttpServletRequest request, HttpServletResponse response, HttpMethod method) throws Exception
	{
		// DEBUG: show database warnings
		try
		{
			if (databaseConnection != null)
			{
				for (SQLWarning warning = databaseConnection.getWarnings(); warning != null; warning = warning.getNextWarning())
					warning.printStackTrace();
			}
		}
		finally
		{
			// ALWAYS close the database connection!
			closeDatabaseConnection();
		}
	}
	
	
	
	//================================================================================
    // Database Connection Management
	//================================================================================
	
	private Connection databaseConnection = null;
	
	/**
	 * Connects to the database.
	 * 
	 * @return
	 * 	Database connection.
	 * 
	 * @throws SQLException
	 */
	public Connection getDatabaseConnection() throws SQLException
	{
		if (databaseConnection == null)
			databaseConnection = Database.getConnection();
		
		return databaseConnection;
	}
	
	/**
	 * Closes the database connection if one exists.
	 * 
	 * @throws SQLException
	 */
	public void closeDatabaseConnection() throws SQLException
	{
		if (databaseConnection != null)
		{
			Database.closeConnection(databaseConnection);
			databaseConnection = null;
		}
	}
}
