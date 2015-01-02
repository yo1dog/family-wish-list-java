package net.awesomebox.fwl.controllers.auth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.awesomebox.fwl.AuthManager;
import net.awesomebox.fwl.FWLPageManagedHttpServlet;
import net.awesomebox.fwl.SecurityUtil;
import net.awesomebox.fwl.models.User;
import net.awesomebox.servletmanager.ServletHelper;


@WebServlet("/login")
public class Login extends FWLPageManagedHttpServlet
{
	private static final long serialVersionUID = 1L;
    
	@Override
	protected void _doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		// check if they are already logged in
		User loggedInUser = AuthManager.getLoggedInUser(request);
		
		if (loggedInUser != null)
		{
			response.sendRedirect("/home");
			return;
		}
		
		String callbackURL = ServletHelper.getParameter(request, "callbackURL");
		addCallbackURLQueryStringAttribute(request, callbackURL);
		
		request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp").forward(request, response);
	}
	
	@Override
	protected void _doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		// check if they are already logged in
		User loggedInUser = AuthManager.getLoggedInUser(request);
		
		if (loggedInUser != null)
		{
			response.sendRedirect("/home");
			return;
		}
		
		// get callback
		String callbackURL = ServletHelper.getParameter(request, "callbackURL");
		
		// get email and password
		String email    = ServletHelper.getParameterNotEmpty(request, "email");
		String password = ServletHelper.getParameterNotEmpty(request, "password");
		
		if (email == null)
		{
			returnFormErrorMessage("Please enter your email address.", email, callbackURL, request, response);
			return;
		}
		if (password == null)
		{
			returnFormErrorMessage("Please enter a password.", email, callbackURL, request, response);
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
		
		Connection cn = getDatabaseConnection(request);
		User user = User.findByLogin(cn, email, passwordHash);
		
		// check if the email/password combo was invalid
		if (user == null)
		{
			returnFormErrorMessage("Invalid email or password.", email, callbackURL, request, response);
			return;
		}
		
		// set session
		AuthManager.setSessionLoggedInUserID(request, user.id);
		
		response.sendRedirect(callbackURL != null? callbackURL : "/home");
	}
	
	
	private static void addCallbackURLQueryStringAttribute(HttpServletRequest request, String callbackURL) throws UnsupportedEncodingException
	{
		if (callbackURL != null)
			request.setAttribute("callbackURLQueryString", "?callbackURL=" + URLEncoder.encode(callbackURL, "UTF-8"));
	}
	
	private static void returnFormErrorMessage(String formErrorMessage, String email, String callbackURL, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("formErrorMessage", formErrorMessage);
		request.setAttribute("email", email);
		
		addCallbackURLQueryStringAttribute(request, callbackURL);
		
		request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp").forward(request, response);
	}
}
