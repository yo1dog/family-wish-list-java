package net.awesomebox.fwl.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WishListCollection
{
	public final int    id;
	public final int    ownerUserId;
	public final String name;
	
	private WishList[] wishLists;
	
	
	private WishListCollection(
		int    id,
		int    ownerUserId,
		String name
	)
	{
		this.id          = id;
		this.ownerUserId = ownerUserId;
		this.name        = name;
	}
	
	private WishListCollection(ResultSet rs) throws SQLException
	{
		this(
			rs.getInt   ("id"),
			rs.getInt   ("owner_user_id"),
			rs.getString("name")
		);
	}
	
	
	public final WishList[] getWishLists(Connection cn) throws SQLException
	{
		if (wishLists == null)
			wishLists = WishList.findListByCollection(cn, id);
		
		return wishLists;
	}
	
	
	
	public static WishListCollection findByID(Connection cn, int collectionID) throws SQLException
	{
		PreparedStatement st = cn.prepareStatement("SELECT * FROM wish_list_collections WHERE id = ?");
		st.setInt(1, collectionID);
		ResultSet rs = st.executeQuery();
		
		if (rs.next() == false)
			return null;
		
		WishListCollection collection = new WishListCollection(rs);
		
		rs.close();
		st.close();
		
		return collection;
	}
	
	public static WishListCollection[] findListByCreator(Connection cn, int creatorUserID) throws SQLException
	{
		ArrayList<WishListCollection> collections = new ArrayList<WishListCollection>();
		
		PreparedStatement st = cn.prepareStatement("SELECT * FROM wish_list_collections WHERE creator_user_id = ?");
		st.setInt(1, creatorUserID);
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
			collections.add(new WishListCollection(rs));
		
		rs.close();
		st.close();
		
		return collections.toArray(new WishListCollection[collections.size()]);
	}
	
	public static WishListCollection[] findListByMember(Connection cn, int userID) throws SQLException
	{
		ArrayList<WishListCollection> collections = new ArrayList<WishListCollection>();
		
		PreparedStatement st = cn.prepareStatement(
			"SELECT wish_list_collections.* " +
			"FROM wish_lists " +
			"LEFT JOIN wish_list_collections ON (wish_lists.wish_list_collection_id = wish_list_collections.id) " +
			"WHERE wish_lists.owner_user_id = ?");
		st.setInt(1, userID);
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
			collections.add(new WishListCollection(rs));
		
		rs.close();
		st.close();
		
		return collections.toArray(new WishListCollection[collections.size()]);
	}
	
	
	
	public static int create(Connection cn, int ownerUserID, String name) throws SQLException
	{
		String query =
			"INSERT INTO wish_list_collections (" +
				"owner_user_id, " +
				"name" +
			")" +
			"VALUES (?, ?)" +
			"RETURNING id";
		
		PreparedStatement st = cn.prepareStatement(query);
		
		st.setInt   (1, ownerUserID);
		st.setString(2, name);
		
		ResultSet rs = st.executeQuery();
		rs.next();
		
		int collectionID = rs.getInt(1);
		
		rs.close();
		st.close();
		
		return collectionID;
	}
}
