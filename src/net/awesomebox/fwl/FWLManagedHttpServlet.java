package net.awesomebox.fwl;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLWarning;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.awesomebox.fwl.database.Database;
import net.awesomebox.fwl.models.User;
import net.awesomebox.servletmanager.ManagedHttpServlet;
import net.awesomebox.servletmanager.ManagedServletException;
import net.awesomebox.servletmanager.exceptions.UnauthorizedException;

public class FWLManagedHttpServlet extends ManagedHttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void beforeRequests(HttpServletRequest request, HttpServletResponse response, HttpMethod method) throws Exception
	{
		Integer loggedInUserID = AuthManager.getSessionLoggedInUserID(request);
		
		if (loggedInUserID != null)
		{
			User loggedInUser = User.getByID(getDatabaseConnection(request), loggedInUserID);
			
			if (loggedInUser == null)
				AuthManager.unsetSessionLoggedInUserID(request);
			else
				AuthManager.setRequestLoggedInUser(request, loggedInUser);
		}
		
	}
	
	@Override
	protected void afterRequests(HttpServletRequest request, HttpServletResponse response, HttpMethod method) throws Exception
	{
		Connection cn = getDatabaseConnection(request);
		
		// DEBUG: show database warnings
		try
		{
			if (cn != null)
			{
				for (SQLWarning warning = cn.getWarnings(); warning != null; warning = warning.getNextWarning())
					warning.printStackTrace();
			}
		}
		finally
		{
			// ALWAYS close the database connection!
			closeDatabaseConnection(request);
		}
	}
	
	
	@Override
	protected boolean onManagedServletException(HttpServletRequest request, HttpServletResponse response, HttpMethod method, ManagedServletException e) throws ServletException, IOException
	{
		if (e instanceof UnauthorizedException)
		{
			String path = request.getRequestURI();
			String queryString = request.getQueryString();
			
			String callbackURL = path;
			if (queryString != null)
				callbackURL += "?" + queryString;
			
			response.sendRedirect("/login?callbackURL=" + URLEncoder.encode(callbackURL, "UTF-8"));
			return true;
		}
		
		return super.onManagedServletException(request, response, method, e);
	}

	
	
	
	//================================================================================
    // Database Connection Management
	//================================================================================
	
	/**
	 * Connects to the database.
	 * 
	 * @return
	 * 	Database connection.
	 * 
	 * @throws SQLException
	 */
	public Connection getDatabaseConnection(HttpServletRequest request) throws SQLException
	{
		Connection databaseConnection = (Connection)request.getAttribute("databaseConnection");
		
		if (databaseConnection == null)
		{
			databaseConnection = Database.getConnection();
			request.setAttribute("databaseConnection", databaseConnection);
		}
		
		return databaseConnection;
	}
	
	/**
	 * Closes the database connection if one exists.
	 * 
	 * @throws SQLException
	 */
	public void closeDatabaseConnection(HttpServletRequest request) throws SQLException
	{
		Connection databaseConnection = (Connection)request.getAttribute("databaseConnection");
		if (databaseConnection != null)
		{
			Database.closeConnection(databaseConnection);
			request.setAttribute("databaseConnection", null);
		}
	}
}
