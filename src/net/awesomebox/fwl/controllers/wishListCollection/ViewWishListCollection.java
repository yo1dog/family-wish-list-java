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
import net.awesomebox.fwl.models.WishList;
import net.awesomebox.fwl.models.WishListCollection;
import net.awesomebox.servletmanager.ServletHelper;
import net.awesomebox.servletmanager.exceptions.ResourceNotFoundException;

@WebServlet("/collection")
public class ViewWishListCollection extends FWLPageManagedHttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void _doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		User loggedInUser = AuthManager.requireLoggedInUser(request);
		
		int collectionID = ServletHelper.getRequiredParameterInt(request, "id");
		
		Connection cn = getDatabaseConnection(request);
		WishListCollection collection = WishListCollection.findByID(cn, collectionID);
		
		if (collection == null)
			throw new ResourceNotFoundException("Unable to find collection with id \"" + collectionID + "\".");
		
		WishList[] wishLists = collection.getWishLists(cn);
		
		Integer loggedInUserWishListIndex = null;
		for (int i = 0; i < wishLists.length; ++i)
		{
			if (wishLists[i].ownerUserID == loggedInUser.id)
			{
				loggedInUserWishListIndex = i;
				break;
			}
		}
		
		request.setAttribute("cn", cn);
		request.setAttribute("collection", collection);
		request.setAttribute("loggedInUserWishListIndex", loggedInUserWishListIndex);
		
		request.getRequestDispatcher("/WEB-INF/jsp/wishListCollection/viewWishListCollection.jsp").forward(request, response);
	}
}
