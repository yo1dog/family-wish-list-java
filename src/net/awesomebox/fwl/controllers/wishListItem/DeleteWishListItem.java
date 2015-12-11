package net.awesomebox.fwl.controllers.wishListItem;

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
import net.awesomebox.fwl.models.WishListItem;
import net.awesomebox.servletmanager.ServletHelper;
import net.awesomebox.servletmanager.exceptions.ForbiddenException;
import net.awesomebox.servletmanager.exceptions.ResourceNotFoundException;


@WebServlet("/item/delete")
public class DeleteWishListItem extends FWLPageManagedHttpServlet
{
	private static final long serialVersionUID = 1L;
    
	@Override
	protected void _doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		User loggedInUser = AuthManager.requireLoggedInUser(request);
		
		int wishListItemID = ServletHelper.getRequiredParameterInt(request, "wishListItemID");
		
		Connection cn = getDatabaseConnection(request);
		WishListItem wishListItem = WishListItem.findByID(cn, wishListItemID);
		
		if (wishListItem == null)
			throw new ResourceNotFoundException("Unable to find wish list item with id \"" + wishListItemID + "\".");
		
		checkPermissions(cn, loggedInUser, wishListItem);
		
		WishListItem.delete(cn, wishListItemID);
		
		response.sendRedirect("/wishlist?id=" + wishListItem.wishListID);
	}
	
	
	private static void checkPermissions(Connection cn, User user, WishListItem wishListItem) throws SQLException, ForbiddenException
	{
		if (wishListItem.creatorUserID != user.id)
			throw new ForbiddenException();
	}
}
