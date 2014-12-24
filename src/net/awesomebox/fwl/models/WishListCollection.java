package net.awesomebox.fwl.models;

public class WishListCollection
{
	public final int    id;
	public final int    creatorUserId;
	public final String name;
	
	private User creator;
	private WishList[] wishLists;
	
	
	private WishListCollection(
		int    id,
		int    creatorUserId,
		String name
	)
	{
		this.id            = id;
		this.creatorUserId = id;
		this.name          = name;
	}
	
	public final User getCreator() { return creator; }
	public final WishList[] getWishLists() { return wishLists; }
	
	
	
	public static WishListCollection getByID(int id)
	{
		return null;
	}
	
	public static WishListCollection[] getByCreator(int creatorUserId)
	{
		return null;
	}
}
