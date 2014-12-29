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

import net.awesomebox.fwl.FWLManagedHttpServlet;
import net.awesomebox.fwl.models.WishList;
import net.awesomebox.fwl.models.WishListItem;
import net.awesomebox.servletmanager.ServletHelper;
import net.awesomebox.servletmanager.exceptions.ResourceNotFoundException;


@WebServlet("/item/create")
public class CreateWishListItem extends FWLManagedHttpServlet
{
	private static final long serialVersionUID = 1L;
    
	@Override
	protected void _doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		int wishListID = ServletHelper.getRequiredParameterInt(request, "wishListID");
		
		Connection cn = getDatabaseConnection(request);
		WishList wishList = WishList.getByID(cn, wishListID);
		
		if (wishList == null)
			throw new ResourceNotFoundException("Unable to find wish list with id \"" + wishListID + "\".");
		
		request.setAttribute("cn", cn);
		request.setAttribute("wishListID", wishList.id);
		request.getRequestDispatcher("/WEB-INF/jsp/wishListItem/createWishListItem.jsp").forward(request, response);
	}
	
	@Override
	protected void _doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		int wishListID = ServletHelper.getRequiredParameterInt(request, "wishListID");
		
		// get form data
		String name        = ServletHelper.getParameterNotEmpty(request, "name");
		String url         = ServletHelper.getParameter        (request, "url");
		String imageURL    = ServletHelper.getParameter        (request, "imageURL");
		String description = ServletHelper.getParameter        (request, "description");
		
		Map<String, String> formData = new HashMap<String, String>();
		formData.put("name"       , name);
		formData.put("url"        , url);
		formData.put("imageURL"   , imageURL);
		formData.put("description", description);
		
		if (name == null)
		{
			returnFormErrorMessage("Please enter a name.", wishListID, formData, request, response);
			return;
		}
		
		Connection cn = getDatabaseConnection(request);
		WishListItem.create(cn, wishListID, name, url, imageURL, description);
		
		response.sendRedirect("/wishlist?id=" + wishListID);
	}
	
	
	private static void returnFormErrorMessage(String formErrorMessage, int wishListID, Map<String, String> formData, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("formErrorMessage", formErrorMessage);
		request.setAttribute("formData", formData);
		request.setAttribute("wishListID", wishListID);
		
		request.getRequestDispatcher("/WEB-INF/jsp/wishListItem/createWishListItem.jsp").forward(request, response);
	}
}
