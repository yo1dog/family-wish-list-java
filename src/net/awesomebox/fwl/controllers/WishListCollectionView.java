package net.awesomebox.fwl.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.awesomebox.fwl.AuthManager;
import net.awesomebox.fwl.FWLManagedHttpServlet;
import net.awesomebox.fwl.models.User;
import net.awesomebox.fwl.models.WishListCollection;
import net.awesomebox.servletmanager.ServletHelper;

@WebServlet("/collection")
public class WishListCollectionView extends FWLManagedHttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void _doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		Connection cn = getDatabaseConnection();
		User loggedInUser = AuthManager.requireLoggedInUser(cn, request);
		
		int collectionID = ServletHelper.getRequiredParameterInt(request, "id");
		WishListCollection collection = WishListCollection.getByID(collectionID);
		
		
		request.setAttribute("loggedInUser", loggedInUser);
		request.setAttribute("collection", collection);
		
		request.getRequestDispatcher("WEB-INF/jsp/profile.jsp").forward(request, response);
	}
}
