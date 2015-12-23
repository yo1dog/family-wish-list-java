package net.awesomebox.fwl.controllers.auth;

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
import net.awesomebox.fwl.FWLPageManagedHttpServlet;
import net.awesomebox.fwl.SecurityUtil;
import net.awesomebox.fwl.models.User;
import net.awesomebox.servletmanager.ServletHelper;


@WebServlet("/resetPassword")
public class ResetPassword extends FWLPageManagedHttpServlet
{
	private static final long serialVersionUID = 1L;
    
	@Override
	protected void _doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		request.getRequestDispatcher("/WEB-INF/jsp/auth/resetPassword.jsp").forward(request, response);
	}
	
	@Override
	protected void _doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		// get form data
		String email           = ServletHelper.getParameterNotEmpty(request, "email");
		String password        = ServletHelper.getParameterNotEmpty(request, "password");
		String passwordConfirm = ServletHelper.getParameterNotEmpty(request, "passwordConfirm");
		
		Map<String, String> formData = new HashMap<String, String>();
		formData.put("email", email);
		
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
		if (passwordConfirm == null || !passwordConfirm.equals(password))
		{
			returnFormErrorMessage("Passwords must match.", formData, request, response);
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
		int userID = User.updatePassword(cn, email, passwordHash);
		
		// check if a user was found and updated
		if (userID == -1)
		{
			returnFormErrorMessage("Email not found.", formData, request, response);
			return;
		}
		
		
		// set session
		AuthManager.setSessionLoggedInUserID(request, userID);
		
		response.sendRedirect("/home");
	}
	
	
	private static void returnFormErrorMessage(String formErrorMessage, Map<String, String> formData, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("formErrorMessage", formErrorMessage);
		request.setAttribute("formData", formData);
		
		request.getRequestDispatcher("/WEB-INF/jsp/auth/resetPassword.jsp").forward(request, response);
	}
}
