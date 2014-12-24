package net.awesomebox.fwl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.awesomebox.fwl.models.User;
import net.awesomebox.servletmanager.exceptions.UnauthorizedException;

public class AuthManager
{
	public static void setLoggedInUser(HttpServletRequest request, int userID)
	{
		HttpSession session = request.getSession(true);
		session.setAttribute("userID", new Integer(userID));
	}
	
	public static void unsetLoggedInUser(HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		if (session != null)
			session.setAttribute("userID", null);
	}
	
	public static User getLoggedInUser(Connection cn, HttpServletRequest request) throws SQLException
	{
		HttpSession session = request.getSession(false);
		
		if (session == null)
			return null;
        
		Integer userID = (Integer)session.getAttribute("userID");
		
		if (userID == null)
			return null;
		
		// lookup user in DB
		User user = User.getByID(cn, userID);
		
		if (user == null)
		{
			session.setAttribute("userID", null);
			return null;
			
		}
		
		return user;
	}
	
	public static User requireLoggedInUser(Connection cn, HttpServletRequest request) throws ServletException, SQLException
	{
		User loggedInUser = getLoggedInUser(cn, request);
		
		if (loggedInUser == null)
			throw new UnauthorizedException();
		
		return loggedInUser;
	}
}
