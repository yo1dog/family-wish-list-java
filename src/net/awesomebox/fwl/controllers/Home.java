package net.awesomebox.fwl.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.awesomebox.fwl.AuthManager;
import net.awesomebox.fwl.models.User;

@WebServlet("/home")
public class Home extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		User loggedInUser = AuthManager.getLoggedInUser(request);
		
		if (loggedInUser == null)
		{
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
			rd.forward(request, response);
		}
		else
		{
			request.setAttribute("loggedInUser", loggedInUser);
			
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/profile.jsp");
			rd.forward(request, response);
		}
	}
}
