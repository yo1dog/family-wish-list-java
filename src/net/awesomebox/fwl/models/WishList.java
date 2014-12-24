package net.awesomebox.fwl.models;

public class WishList
{
	public final int wishListCollectionID;
	public final int ownerUserID;
	
	private WishListCollection collection;
	private User owner;
	private WishListItem[] items;
	
	private WishList(
		int wishListCollectionID,
		int ownerUserID
	)
	{
		this.wishListCollectionID = wishListCollectionID;
		this.ownerUserID          = ownerUserID;
	}
	
	public final WishListCollection getCollection() { return collection; }
	public final User getOwner() { return owner; }
	public final WishListItem[] getItems() { return items; }
}
