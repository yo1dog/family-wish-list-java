package net.awesomebox.fwl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.awesomebox.fwl.models.User;

public class AuthManager
{
	public static User getLoggedInUser(HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		
		if (session == null)
			return null;
        
		String userID = (String)session.getAttribute("userID");
		
		if (userID == null)
			return null;
		
		// lookup user in DB
		return null;
	}
	
	public static User requireLoggedInUser(HttpServletRequest request) throws ServletException
	{
		User loggedInUser = getLoggedInUser(request);
		
		if (loggedInUser == null)
			throw new ServletException("403 Forbidden");
		
		return loggedInUser;
	}
}
