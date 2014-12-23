package net.awesomebox.fwl.models;

public class WishListItem
{
	public final int     id;
	public final int     wishListID;
	public final String  url;
	public final String  imageURL;
	public final Integer coveredByUserID;
	public final int     priority;
	
	private WishList wishList;
	private User coveredBy;
	
	private WishListItem(
		int     id,
		int     wishListID,
		String  url,
		String  imageURL,
		Integer coveredByUserID,
		int     priority
	)
	{
		this.id              = id;
		this.wishListID      = wishListID;
		this.url             = url;
        this.imageURL        = imageURL;        this.coveredByUserID = coveredByUserID;        this.priority        = priority;
	}
	
	public final WishList getWishList() { return wishList; }
	public final User getCoveredBy() { return coveredBy; }
}