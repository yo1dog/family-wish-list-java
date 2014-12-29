package net.awesomebox.fwl.controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.awesomebox.fwl.AuthManager;
import net.awesomebox.fwl.FWLManagedHttpServlet;
import net.awesomebox.fwl.models.User;

@WebServlet("/home")
public class Home extends FWLManagedHttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void _doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		User loggedInUser = AuthManager.getLoggedInUser(request);
		
		if (loggedInUser == null)
			request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp").forward(request, response);
		else
		{
			request.setAttribute("cn", getDatabaseConnection(request));
			request.getRequestDispatcher("/WEB-INF/jsp/user/profile.jsp").forward(request, response);
		}
	}
}
