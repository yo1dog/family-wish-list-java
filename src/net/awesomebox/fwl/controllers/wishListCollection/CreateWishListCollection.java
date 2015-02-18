package net.awesomebox.fwl.controllers.wishListCollection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.awesomebox.fwl.AuthManager;
import net.awesomebox.fwl.FWLPageManagedHttpServlet;
import net.awesomebox.fwl.models.User;
import net.awesomebox.fwl.models.WishListCollection;
import net.awesomebox.servletmanager.ServletHelper;

@WebServlet("/collection/create")
public class CreateWishListCollection extends FWLPageManagedHttpServlet
{
	private static final long serialVersionUID = 1L;
    
	@Override
	protected void _doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		AuthManager.requireLoggedInUser(request);
		
		request.getRequestDispatcher("/WEB-INF/jsp/wishListCollection/createWishListCollection.jsp").forward(request, response);
	}
	
	@Override
	protected void _doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		User loggedInUser = AuthManager.requireLoggedInUser(request);
		int ownerID = loggedInUser.id;
		
		// get name
		String collectionName = ServletHelper.getParameterNotEmpty(request, "collectionName");
		
		if (collectionName == null)
		{
			returnFormErrorMessage("Please enter a name for the collection.", collectionName, request, response);
			return;
		}
		
		// create the collection
		Connection cn = getDatabaseConnection(request);
		int collectionID = WishListCollection.create(cn, ownerID, collectionName);
		
		response.sendRedirect("/collection?id=" + collectionID);
	}
	
	
	private static void returnFormErrorMessage(String formErrorMessage, String collectionName, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("formErrorMessage", formErrorMessage);
		request.setAttribute("collectionName", collectionName);
		
		request.getRequestDispatcher("/WEB-INF/jsp/wishListCollection/createWishListCollection.jsp").forward(request, response);
	}
}
