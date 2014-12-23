package net.awesomebox.fwl.models;

public class WishList
{
	public final int id;
	public final int ownerUserID;
	
	private User owner;
	private WishListItem[] items;
	
	private WishList(
		int id,
		int ownerUserID
	)
	{
		this.id          = id;
		this.ownerUserID = ownerUserID;
	}
	
	public final User getOwner() { return owner; }
	public final WishListItem[] getItems() { return items; }
}
