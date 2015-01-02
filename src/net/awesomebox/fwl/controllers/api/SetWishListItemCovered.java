package net.awesomebox.fwl.controllers.api;

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
import net.awesomebox.fwl.models.WishList;
import net.awesomebox.fwl.models.WishListItem;
import net.awesomebox.servletmanager.ManagedServletException;
import net.awesomebox.servletmanager.ServletHelper;
import net.awesomebox.servletmanager.exceptions.ForbiddenException;
import net.awesomebox.servletmanager.exceptions.ResourceNotFoundException;


@WebServlet("/api/setWishListItemCovered")
public class SetWishListItemCovered extends FWLManagedHttpServlet
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void _doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		User loggedInUser = AuthManager.requireLoggedInUser(request);
		
		// get the params
		int     itemID    = ServletHelper.getRequiredParameterInt(request, "itemID");
		Boolean fulfilled = ServletHelper.getParameterBoolean    (request, "fulfilled");
		
		// get the item
		Connection cn = getDatabaseConnection(request);
		WishListItem item = WishListItem.findByID(cn, itemID);
		
		if (item == null)
			throw new ResourceNotFoundException("Unable to find wish list item with ID \"" + itemID + "\".");
		
		// get the item's wish list
		WishList wishlist = item.getWishList(cn);
		
		// make sure the user is in the item's collection
		boolean isInCollection = User.lookupIsMemeberOfCollection(cn, loggedInUser.id, wishlist.wishListCollectionID);
		if (!isInCollection)
			throw new ForbiddenException();
		
		// make sure the item is not already covered by someone else
		if (item.coveredByUserID != null && (int)item.coveredByUserID != loggedInUser.id)
			throw new ManagedServletException(400, "The given wish list item is already covered by user with ID \"" + item.coveredByUserID + "\".");
		
		// make sure the person covering is not the owner of the item
		if (wishlist.ownerUserID == loggedInUser.id)
			throw new ManagedServletException(400, "The owner of a wish list item can not cover said item.");
		
		
		// update the item
		if (fulfilled != null)
			WishListItem.setCovered(cn, item.id, loggedInUser.id, fulfilled);
		else
			WishListItem.setUncovered(cn, item.id);
	}
}
