package net.awesomebox.fwl.controllers;

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
			// get the items the logged-in user has covered but not fulfilled
			Connection cn = getDatabaseConnection(request);
			
			WishListItem[] items = WishListItem.findListByUnfulfilledByUser(cn, loggedInUser.id);
			
			// group items into wish lists
			ArrayList<UnfulfilledItemWishListGroup> wishListGroups = new ArrayList<UnfulfilledItemWishListGroup>();
			
			for (int i = 0; i < items.length; ++i)
			{
				UnfulfilledItemWishListGroup wishListGroup = null;
				for (UnfulfilledItemWishListGroup _wishListGroup : wishListGroups)
				{
					if (items[i].wishListID == _wishListGroup.wishList.id)
					{
						wishListGroup = _wishListGroup;
						break;
					}
				}
				
				if (wishListGroup == null)
				{
					WishList wishList = WishList.findByID(cn, items[i].wishListID);
					wishListGroup = new UnfulfilledItemWishListGroup(wishList);
					
					wishListGroups.add(wishListGroup);
				}
				
				wishListGroup.items.add(items[i]);
			}
			
			
			// group wish lists into collections
			ArrayList<UnfulfilledItemCollectionGroup> collectionGroups = new ArrayList<UnfulfilledItemCollectionGroup>();
			
			for (UnfulfilledItemWishListGroup wishListGroup : wishListGroups)
			{
				UnfulfilledItemCollectionGroup collectionGroup = null;
				for (UnfulfilledItemCollectionGroup _collectionGroup : collectionGroups)
				{
					if (wishListGroup.wishList.wishListCollectionID == _collectionGroup.collection.id)
					{
						collectionGroup = _collectionGroup;
						break;
					}
				}
				
				if (collectionGroup == null)
				{
					WishListCollection collection = WishListCollection.findByID(cn, wishListGroup.wishList.wishListCollectionID);
					collectionGroup = new UnfulfilledItemCollectionGroup(collection);
					
					collectionGroups.add(collectionGroup);
				}
				
				collectionGroup.wishListGroups.add(wishListGroup);
			}
			
			
			request.setAttribute("cn", cn);
			request.setAttribute("unfulfilledItemCollectionGroups", collectionGroups.toArray(new UnfulfilledItemCollectionGroup[collectionGroups.size()]));
			request.getRequestDispatcher("/WEB-INF/jsp/user/profile.jsp").forward(request, response);
		}
	}
	
	
	
	
	public static final class UnfulfilledItemCollectionGroup
	{
		public final WishListCollection collection;
		public final ArrayList<UnfulfilledItemWishListGroup> wishListGroups;
		
		UnfulfilledItemCollectionGroup(WishListCollection collection)
		{
			this.collection = collection;
			this.wishListGroups = new ArrayList<UnfulfilledItemWishListGroup>();
		}
	}
	
	public static final class UnfulfilledItemWishListGroup
	{
		public final WishList wishList;
		public final ArrayList<WishListItem> items;
		
		UnfulfilledItemWishListGroup(WishList wishList)
		{
			this.wishList = wishList;
			this.items = new ArrayList<WishListItem>();
		}
	}
}
