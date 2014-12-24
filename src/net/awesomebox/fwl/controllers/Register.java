package net.awesomebox.fwl.controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.awesomebox.fwl.AuthManager;
import net.awesomebox.fwl.FWLManagedHttpServlet;
import net.awesomebox.fwl.SecurityUtil;
import net.awesomebox.fwl.models.User;
import net.awesomebox.servletmanager.ServletHelper;


@WebServlet("/register")
public class Register extends FWLManagedHttpServlet
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
		
		request.getRequestDispatcher("WEB-INF/jsp/register.jsp").forward(request, response);
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
		
		
		// get form data
		String firstName = ServletHelper.getParameterNotEmpty(request, "firstName");
		String lastName  = ServletHelper.getParameter        (request, "lastName", "");
		String email     = ServletHelper.getParameterNotEmpty(request, "email");
		String password  = ServletHelper.getParameterNotEmpty(request, "password");
		
		Map<String, String> formData = new HashMap<String, String>();
		formData.put("firstName", firstName);
		formData.put("lastName" , lastName);
		formData.put("email"    , email);
		formData.put("password" , password);
		
		if (firstName == null)
		{
			returnFormErrorMessage("Please enter your first name.", formData, request, response);
			return;
		}
		if (lastName == null)
		{
			returnFormErrorMessage("Please enter your last name.", formData, request, response);
			return;
		}
		if (email == null)
		{
			returnFormErrorMessage("Please enter your email.", formData, request, response);
			return;
		}
		if (password == null)
		{
			returnFormErrorMessage("Please enter a password.", formData, request, response);
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
		
		
		int userID = User.register(cn, firstName, lastName, email, passwordHash);
		
		// check if email was already in use
		if (userID == -1)
		{
			returnFormErrorMessage("Email already in use.", formData, request, response);
		}
		
		
		// set session
		AuthManager.setLoggedInUser(request, userID);
		
		response.sendRedirect("/home");
	}
	
	
	private static void returnFormErrorMessage(String formErrorMessage, Map<String, String> formData, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("formErrorMessage", formErrorMessage);
		request.setAttribute("formData", formData);
		
		request.getRequestDispatcher("WEB-INF/jsp/register.jsp").forward(request, response);
	}
}
