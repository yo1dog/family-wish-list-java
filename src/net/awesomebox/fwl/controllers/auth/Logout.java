package net.awesomebox.fwl.controllers.auth;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.awesomebox.fwl.AuthManager;
import net.awesomebox.fwl.FWLManagedHttpServlet;

@WebServlet("/logout")
public class Logout extends FWLManagedHttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void _doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		AuthManager.unsetLoggedInUser(request);
		response.sendRedirect("/home");
	}
}
