package net.awesomebox.fwl.controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.awesomebox.fwl.AuthManager;
import net.awesomebox.fwl.FWLManagedHttpServlet;
import net.awesomebox.fwl.SecurityUtil;
import net.awesomebox.fwl.models.User;
import net.awesomebox.servletmanager.ServletHelper;


@WebServlet("/login")
public class Login extends FWLManagedHttpServlet
{
	private static final long serialVersionUID = 1L;
    
	@Override
	protected void _doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		// check if they are already logged in
		User loggedInUser = AuthManager.getLoggedInUser(getDatabaseConnection(), request);
		
		if (loggedInUser != null)
		{
			response.sendRedirect("/home");
			return;
		}
		
		request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request, response);
	}
	
	@Override
	protected void _doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		// check if they are already logged in
		Connection cn = getDatabaseConnection();
		User loggedInUser = AuthManager.getLoggedInUser(cn, request);
		
		if (loggedInUser != null)
		{
			response.sendRedirect("/home");
			return;
		}
		
		// get email and password
		String email    = ServletHelper.getParameterNotEmpty(request, "email");
		String password = ServletHelper.getParameterNotEmpty(request, "password");
		
		if (email == null)
		{
			returnFormErrorMessage("Please enter your email address.", email, request, response);
			return;
		}
		if (password == null)
		{
			returnFormErrorMessage("Please enter a password.", email, request, response);
			return;
		}
		
		// hash password
		byte[] passwordHash;
		try
		{
			passwordHash = SecurityUtil.hashPassword(password);
		}
		catch (NoSuchAlgorithmException e)
		{
			throw new ServletException(e);
		}
		
		User user = User.getByLogin(cn, email, passwordHash);
		
		// check if the email/password combo was invalid
		if (user == null)
		{
			returnFormErrorMessage("Invalid email or password.", email, request, response);
			return;
		}
		
		// set session
		AuthManager.setLoggedInUser(request, user.id);
		
		response.sendRedirect("/home");
	}
	
	
	private static void returnFormErrorMessage(String formErrorMessage, String email, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("formErrorMessage", formErrorMessage);
		request.setAttribute("email", email);
		
		request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request, response);
	}
}
