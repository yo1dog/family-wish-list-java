package net.awesomebox.fwl.controllers.wishListCollection;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.awesomebox.fwl.AuthManager;
import net.awesomebox.fwl.FWLPageManagedHttpServlet;

@WebServlet("/collections")
public class ViewWishListCollectionList extends FWLPageManagedHttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void _doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		AuthManager.requireLoggedInUser(request);
		
		request.setAttribute("cn", getDatabaseConnection(request));
		request.getRequestDispatcher("/WEB-INF/jsp/wishListCollection/viewWishListCollectionList.jsp").forward(request, response);
	}
}
