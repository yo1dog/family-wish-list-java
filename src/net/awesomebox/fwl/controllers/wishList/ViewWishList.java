package net.awesomebox.fwl.controllers.wishList;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.awesomebox.fwl.AuthManager;
import net.awesomebox.fwl.FWLPageManagedHttpServlet;
import net.awesomebox.fwl.models.WishList;
import net.awesomebox.fwl.models.WishListItem;
import net.awesomebox.servletmanager.ServletHelper;
import net.awesomebox.servletmanager.exceptions.ResourceNotFoundException;

@WebServlet("/wishlist")
public class ViewWishList extends FWLPageManagedHttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void _doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		AuthManager.requireLoggedInUser(request);
		
		int wishListID = ServletHelper.getRequiredParameterInt(request, "id");
		
		Connection cn = getDatabaseConnection(request);
		WishList wishList = WishList.findByID(cn, wishListID);
		
		if (wishList == null)
			throw new ResourceNotFoundException("Unable to find wish list with id \"" + wishListID + "\".");
		
		// separate items
		WishListItem[] items = wishList.getItems(cn);
		
		// count number of items by the owner
		int numItemsByWishListOwner = 0;
		for (int i = 0; i < items.length; ++i)
		{
			if (items[i].creatorUserID == wishList.ownerUserID)
				++numItemsByWishListOwner;
		}
		
		WishListItem[] itemsByWishListOwner = new WishListItem[numItemsByWishListOwner];
		WishListItem[] itemsByOthers        = new WishListItem[items.length - numItemsByWishListOwner];
		
		int itemsByWishListOwnerIndex = 0;
		int itemsByOthersIndex = 0;
		
		for (int i = 0; i < items.length; ++i)
		{
			if (items[i].creatorUserID == wishList.ownerUserID)
			{
				itemsByWishListOwner[itemsByWishListOwnerIndex] = items[i];
				++itemsByWishListOwnerIndex;
			}
			else
			{
				itemsByOthers[itemsByOthersIndex] = items[i];
				++itemsByOthersIndex;
			}
		}
		
		request.setAttribute("cn"                  , cn);
		request.setAttribute("wishList"            , wishList);
		request.setAttribute("itemsByWishListOwner", itemsByWishListOwner);
		request.setAttribute("itemsByOthers"       , itemsByOthers);
		request.getRequestDispatcher("/WEB-INF/jsp/wishList/viewWishList.jsp").forward(request, response);
	}
}