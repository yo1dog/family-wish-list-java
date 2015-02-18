package net.awesomebox.fwl.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.awesomebox.fwl.database.Database;

public class WishListCollection
{
	public final int     id;
	public final int     ownerUserID;
	public final String  name;
	public final Integer exclusiveWishListID;
	
	private WishList[] wishLists;
	
	
	private WishListCollection(
		int     id,
		int     ownerUserID,
		String  name,
		Integer exclusiveWishListID
	)
	{
		this.id                  = id;
		this.ownerUserID         = ownerUserID;
		this.name                = name;
		this.exclusiveWishListID = exclusiveWishListID;
	}
	
	private WishListCollection(ResultSet rs) throws SQLException
	{
		this(
			rs.getInt   (       "id"),
			rs.getInt   (       "owner_user_id"),
			rs.getString(       "name"),
			Database.gnvInt(rs, "exclusive_wish_list_id")
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
		
		PreparedStatement st = cn.prepareStatement("SELECT * FROM wish_list_collections ORDER BY id DESC");
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
