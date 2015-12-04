package net.awesomebox.fwl.controllers.suggestion;

import java.io.IOException;
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
import net.awesomebox.fwl.models.Suggestion;
import net.awesomebox.fwl.models.User;
import net.awesomebox.servletmanager.ServletHelper;

@WebServlet("/suggestion/create")
public class CreateSuggestion extends FWLPageManagedHttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void _doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		String referringPage = ServletHelper.getParameterNotEmpty(request, "referringPage");
		
		Map<String, String> formData = new HashMap<String, String>();
		formData.put("referringPage", referringPage);
		
		request.setAttribute("formData", formData);
		
		Connection cn = getDatabaseConnection(request);
		showPage(request, response, cn);
	}
	
	@Override
	protected void _doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		Connection cn = getDatabaseConnection(request);
		
		User loggedInUser = AuthManager.getLoggedInUser(request);
		Integer authorUserID = loggedInUser != null? loggedInUser.id : null;
		
		// get form data
		String  text          = ServletHelper.getParameterNotEmpty(request, "text");
		String  referringPage = ServletHelper.getParameterNotEmpty(request, "referringPage");
		
		Map<String, String> formData = new HashMap<String, String>();
		formData.put("text",          text);
		formData.put("referringPage", referringPage);
		
		if (text == null)
		{
			returnFormErrorMessage("Please enter a suggestion.", formData, request, response, cn);
			return;
		}
		
		// create the suggestion
		Suggestion.create(cn, authorUserID, text, referringPage);
		
		request.setAttribute("hideForm", true);
		
		showPage(request, response, cn);
	}
	
	private static void returnFormErrorMessage(String formErrorMessage, Map<String, String> formData, HttpServletRequest request, HttpServletResponse response, Connection cn) throws ServletException, IOException, SQLException
	{
		request.setAttribute("formErrorMessage", formErrorMessage);
		request.setAttribute("formData", formData);
		
		showPage(request, response, cn);
	}
	
	private static void showPage(HttpServletRequest request, HttpServletResponse response, Connection cn) throws SQLException, ServletException, IOException
	{
		// get all suggestions
		Suggestion[] suggestions = Suggestion.findAll(cn);
		
		request.setAttribute("cn",          cn);
		request.setAttribute("suggestions", suggestions);
		
		request.getRequestDispatcher("/WEB-INF/jsp/suggestion/createSuggestion.jsp").forward(request, response);
	}
}
