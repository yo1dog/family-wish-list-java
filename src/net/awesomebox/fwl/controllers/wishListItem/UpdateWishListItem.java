package net.awesomebox.fwl.controllers.wishListItem;

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
import net.awesomebox.fwl.models.User;
import net.awesomebox.fwl.models.WishListItem;
import net.awesomebox.servletmanager.ServletHelper;
import net.awesomebox.servletmanager.exceptions.ForbiddenException;
import net.awesomebox.servletmanager.exceptions.ResourceNotFoundException;


@WebServlet("/item/update")
public class UpdateWishListItem extends FWLPageManagedHttpServlet
{
	private static final long serialVersionUID = 1L;
    
	@Override
	protected void _doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		User loggedInUser = AuthManager.requireLoggedInUser(request);
		
		int wishListItemID = ServletHelper.getRequiredParameterInt(request, "wishListItemID");
		
		Connection cn = getDatabaseConnection(request);
		WishListItem wishListItem = WishListItem.findByID(cn, wishListItemID);
		
		if (wishListItem == null)
			throw new ResourceNotFoundException("Unable to find wish list item with id \"" + wishListItemID + "\".");
		
		checkPermissions(cn, loggedInUser, wishListItem);
		
		request.setAttribute("cn", cn);
		request.setAttribute("wishListItem", wishListItem);
		request.getRequestDispatcher("/WEB-INF/jsp/wishListItem/updateWishListItem.jsp").forward(request, response);
	}
	
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
		
		// get form data
		String name        = ServletHelper.getParameterNotEmpty(request, "name");
		String url         = ServletHelper.getParameter        (request, "url", "");
		String imageURL    = ServletHelper.getParameter        (request, "imageURL", "");
		String description = ServletHelper.getParameter        (request, "description", "");
		
		Map<String, String> formData = new HashMap<String, String>();
		formData.put("name"       , name);
		formData.put("url"        , url);
		formData.put("imageURL"   , imageURL);
		formData.put("description", description);
		
		if (name == null)
		{
			returnFormErrorMessage("Please enter a name.", wishListItem, formData, request, response);
			return;
		}
		
		WishListItem.update(cn, wishListItemID, name, url, imageURL, description);
		
		response.sendRedirect("/wishlist?id=" + wishListItem.wishListID);
	}
	
	
	private static void returnFormErrorMessage(String formErrorMessage, WishListItem wishListItem, Map<String, String> formData, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("formErrorMessage", formErrorMessage);
		request.setAttribute("formData", formData);
		request.setAttribute("wishListItem", wishListItem);
		
		request.getRequestDispatcher("/WEB-INF/jsp/wishListItem/updateWishListItem.jsp").forward(request, response);
	}
	
	
	private static void checkPermissions(Connection cn, User user, WishListItem wishListItem) throws SQLException, ForbiddenException
	{
		if (wishListItem.creatorUserID != user.id)
			throw new ForbiddenException();
	}
}
