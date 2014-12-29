package net.awesomebox.fwl;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.awesomebox.fwl.models.User;
import net.awesomebox.servletmanager.exceptions.UnauthorizedException;

public class AuthManager
{
	public static Integer getSessionLoggedInUserID(HttpServletRequest request) throws SQLException
	{
		HttpSession session = request.getSession(false);
		
		if (session == null)
			return null;
        
		return (Integer)session.getAttribute("userID");
	}
	
	public static void setSessionLoggedInUserID(HttpServletRequest request, int userID)
	{
		HttpSession session = request.getSession(true);
		session.setAttribute("userID", new Integer(userID));
	}
	
	public static void unsetSessionLoggedInUserID(HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		if (session != null)
			session.setAttribute("userID", null);
	}
	
	
	
	public static void setRequestLoggedInUser(HttpServletRequest request, User loggedInUser)
	{
		request.setAttribute("loggedInUser", loggedInUser);
	}
	public static void unsetRequestLoggedInUser(HttpServletRequest request)
	{
		request.setAttribute("loggedInUser", null);
	}
	
	
	
	public static void setLoggedInUser(HttpServletRequest request, User user)
	{
		setSessionLoggedInUserID(request, user.id);
		setRequestLoggedInUser(request, user);
	}
	
	public static void unsetLoggedInUser(HttpServletRequest request)
	{
		unsetSessionLoggedInUserID(request);
		unsetRequestLoggedInUser(request);
	}
	
	public static User getLoggedInUser(HttpServletRequest request)
	{
		return (User)request.getAttribute("loggedInUser");
	}
	
	
	public static User requireLoggedInUser(HttpServletRequest request) throws UnauthorizedException
	{
		User loggedInUser = getLoggedInUser(request);
		
		if (loggedInUser == null)
			throw new UnauthorizedException();
		
		return loggedInUser;
	}
}
