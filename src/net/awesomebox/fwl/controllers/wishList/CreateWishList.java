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
import net.awesomebox.fwl.models.User;
import net.awesomebox.fwl.models.WishList;
import net.awesomebox.servletmanager.ServletHelper;

@WebServlet("/wishlist/create")
public class CreateWishList extends FWLPageManagedHttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void _doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		User loggedInUser = AuthManager.requireLoggedInUser(request);
		
		int collectionID = ServletHelper.getRequiredParameterInt(request, "collectionID");
		int ownerID = loggedInUser.id;
		
		// create the wish list
		Connection cn = getDatabaseConnection(request);
		int wishListID = WishList.create(cn, collectionID, ownerID);
		
		response.sendRedirect("/wishlist?id=" + wishListID);
	}
}
