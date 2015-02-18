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
		
		
		// check if this is an exclusive list
		if (collection.exclusiveWishListID != null) {
			// show the list
			WishList wishList = WishList.findByID(cn, collection.exclusiveWishListID);
			
			if (wishList == null)
				throw new ResourceNotFoundException("Unable to find wish list with id \"" + collection.exclusiveWishListID + "\".");
			
			request.setAttribute("cn"      , cn);
			request.setAttribute("wishList", wishList);
			request.getRequestDispatcher("/WEB-INF/jsp/wishList/viewWishList.jsp").forward(request, response);
			return;
		}
		
		// show the collection
		WishList[] wishLists = collection.getWishLists(cn);
		
		WishList loggedInUserWishList = null;
		for (int i = 0; i < wishLists.length; ++i)
		{
			if (wishLists[i].ownerUserID == loggedInUser.id)
			{
				loggedInUserWishList = wishLists[i];
				break;
			}
		}
		
		request.setAttribute("cn", cn);
		request.setAttribute("collection", collection);
		request.setAttribute("loggedInUserWishList", loggedInUserWishList);
		
		request.getRequestDispatcher("/WEB-INF/jsp/wishListCollection/viewWishListCollection.jsp").forward(request, response);
	}
}
