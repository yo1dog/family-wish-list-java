package net.awesomebox.fwl.controllers;

import java.io.Console;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.awesomebox.fwl.AuthManager;
import net.awesomebox.fwl.FWLPageManagedHttpServlet;
import net.awesomebox.fwl.models.User;
import net.awesomebox.fwl.models.WishList;
import net.awesomebox.fwl.models.WishListCollection;
import net.awesomebox.fwl.models.WishListItem;

@WebServlet("/home")
public class Home extends FWLPageManagedHttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void _doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		User loggedInUser = AuthManager.getLoggedInUser(request);
		
		if (loggedInUser == null)
			request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp").forward(request, response);
		else
		{
			// get the items the logged-in user has covered
			Connection cn = getDatabaseConnection(request);
			
			WishListItem[] covereditems = WishListItem.findListByCoveredByUser(cn, loggedInUser.id);
			
			// group items into wish lists
			ArrayList<CoveredWishList> coveredWishLists = new ArrayList<CoveredWishList>();
			
			for (int i = 0; i < covereditems.length; ++i)
			{
				CoveredWishList coveredWishList = null;
				for (CoveredWishList _coveredWishList : coveredWishLists)
				{
					if (covereditems[i].wishListID == _coveredWishList.wishList.id)
					{
						coveredWishList = _coveredWishList;
						break;
					}
				}
				
				if (coveredWishList == null)
				{
					WishList wishList = WishList.findByID(cn, covereditems[i].wishListID);
					coveredWishList = new CoveredWishList(wishList);
					
					coveredWishLists.add(coveredWishList);
				}
				
				coveredWishList.coveredItems.add(covereditems[i]);
			}
			
			
			// group wish lists into collections
			ArrayList<CoveredCollection> coveredCollections = new ArrayList<CoveredCollection>();
			
			for (CoveredWishList coveredWishList : coveredWishLists)
			{
				CoveredCollection coveredCollection = null;
				for (CoveredCollection _coveredCollection : coveredCollections)
				{
					if (coveredWishList.wishList.wishListCollectionID == _coveredCollection.collection.id)
					{
						coveredCollection = _coveredCollection;
						break;
					}
				}
				
				if (coveredCollection == null)
				{
					WishListCollection collection = WishListCollection.findByID(cn, coveredWishList.wishList.wishListCollectionID);
					coveredCollection = new CoveredCollection(collection);
					
					coveredCollections.add(coveredCollection);
				}
				
				coveredCollection.coveredWishLists.add(coveredWishList);
			}
			
			// remove hidden collections
			coveredCollections.removeIf(coveredCollection -> coveredCollection.collection.isHidden);
			
			// sort the covered collections
			coveredCollections.sort((a, b) -> a.collection.id - b.collection.id);
			
			for (CoveredCollection coveredCollection : coveredCollections)
			{
				// sort the covered wish lists
				coveredCollection.coveredWishLists.sort((a, b) -> a.wishList.id - b.wishList.id);
				
				for (CoveredWishList coveredWishList : coveredCollection.coveredWishLists)
				{
					// sort the covered items
					coveredWishList.coveredItems.sort((a, b) -> a.priority - b.priority);
				}
			}
			
			
			request.setAttribute("cn", cn);
			request.setAttribute("coveredCollections", coveredCollections.toArray(new CoveredCollection[coveredCollections.size()]));
			request.getRequestDispatcher("/WEB-INF/jsp/user/profile.jsp").forward(request, response);
		}
	}
	
	
	
	
	public static final class CoveredCollection
	{
		public final WishListCollection collection;
		public final ArrayList<CoveredWishList> coveredWishLists;
		
		CoveredCollection(WishListCollection collection)
		{
			this.collection = collection;
			this.coveredWishLists = new ArrayList<CoveredWishList>();
		}
	}
	
	public static final class CoveredWishList
	{
		public final WishList wishList;
		public final ArrayList<WishListItem> coveredItems;
		
		CoveredWishList(WishList wishList)
		{
			this.wishList = wishList;
			this.coveredItems = new ArrayList<WishListItem>();
		}
	}
}
