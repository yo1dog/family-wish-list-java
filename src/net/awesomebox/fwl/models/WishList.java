package net.awesomebox.fwl.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WishList
{
	public final int id;
	public final int wishListCollectionID;
	public final int ownerUserID;
	
	private WishListCollection collection;
	private User owner;
	private WishListItem[] items;
	
	private WishList(
		int id,
		int wishListCollectionID,
		int ownerUserID
	)
	{
		this.id                   = id;
		this.wishListCollectionID = wishListCollectionID;
		this.ownerUserID          = ownerUserID;
	}
	
	private WishList(ResultSet rs) throws SQLException
	{
		this(
			rs.getInt("id"),
			rs.getInt("wish_list_collection_id"),
			rs.getInt("owner_user_id")
		);
	}
	
	
	
	public final WishListCollection getCollection(Connection cn) throws SQLException
	{
		if (collection == null)
			collection = WishListCollection.findByID(cn, wishListCollectionID);
		
		return collection;
	}
	
	public final User getOwner(Connection cn) throws SQLException
	{
		if (owner == null)
			owner = User.findByID(cn, ownerUserID);
		
		return owner;
	}
	
	public final WishListItem[] getItems(Connection cn) throws SQLException
	{
		if (items == null)
			items = WishListItem.findListByWishList(cn, id);
		
		return items;
	}
	
	
	
	public static final WishList findByID(Connection cn, int wishListID) throws SQLException
	{
		PreparedStatement st = cn.prepareStatement("SELECT * FROM wish_lists WHERE id = ?");
		st.setInt(1, wishListID);
		ResultSet rs = st.executeQuery();
		
		WishList wishList = rs.next()? new WishList(rs) : null;
		
		rs.close();
		st.close();
		
		return wishList;
	}
	
	public static final WishList[] findListByCollection(Connection cn, int collectionID) throws SQLException
	{
		ArrayList<WishList> wishLists = new ArrayList<WishList>();
		
		PreparedStatement st = cn.prepareStatement("SELECT * FROM wish_lists WHERE wish_list_collection_id = ?");
		st.setInt(1, collectionID);
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
			wishLists.add(new WishList(rs));
		
		rs.close();
		st.close();
		
		return wishLists.toArray(new WishList[wishLists.size()]);
	}
	
	public static final WishList findByOwnerAndCollection(Connection cn, int ownerID, int collectionID) throws SQLException
	{
		PreparedStatement st = cn.prepareStatement("SELECT * FROM wish_lists WHERE owner_id = ? AND collection_id = ?");
		st.setInt(1, ownerID);
		st.setInt(2, collectionID);
		
		ResultSet rs = st.executeQuery();
		
		if (rs.next() == false)
			return null;
		
		WishList wishList = new WishList(rs);
		
		rs.close();
		st.close();
		
		return wishList;
	}
	
	
	
	public static int create(Connection cn, int collectionID, int ownerUserID) throws SQLException
	{
		String query =
			"INSERT INTO wish_lists (" +
				"wish_list_collection_id, " +
				"owner_user_id" +
			")" +
			"VALUES (?, ?)" +
			"RETURNING id";
		
		PreparedStatement st = cn.prepareStatement(query);
		
		st.setInt(1, collectionID);
		st.setInt(2, ownerUserID);
		
		ResultSet rs = st.executeQuery();
		rs.next();
		
		int wishListID = rs.getInt(1);
		
		rs.close();
		st.close();
		
		return wishListID;
	}
}
