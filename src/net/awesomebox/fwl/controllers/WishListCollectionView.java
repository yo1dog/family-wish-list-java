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
import net.awesomebox.fwl.models.WishListCollection;

@WebServlet("/collection")
public class WishListCollectionView extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		User loggedInUser = AuthManager.requireLoggedInUser(request);
		
		// get the collection
		WishListCollection collection = WishListCollection.getByID();
		
		request.setAttribute("loggedInUser", loggedInUser);
		
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/profile.jsp");
		rd.forward(request, response);
	}
}
