package net.awesomebox.fwl.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.awesomebox.fwl.database.Database;

public class WishListItem
{
	public final int     id;
	public final int     wishListID;
	public final int     creatorUserID;
	public final String  name;
	public final String  url;
	public final String  imageURL;
	public final String  description;
	public final Integer coveredByUserID;
	public final boolean fulfilled;
	public final int     priority;
	
	private User coveredByUser;
	private WishList wishList;
	
	private WishListItem(
		int     id,
		int     wishListID,
		int     creatorUserID,
		String  name,
		String  url,
		String  imageURL,
		String  description,
		Integer coveredByUserID,
		boolean fulfilled,
		int     priority
	)
	{
		this.id              = id;
		this.wishListID      = wishListID;
		this.creatorUserID   = creatorUserID;
		this.name            = name;
		this.url             = url;
        this.imageURL        = imageURL;
        this.description     = description;        this.coveredByUserID = coveredByUserID;
        this.fulfilled       = fulfilled;        this.priority        = priority;
	}
	
	private WishListItem(ResultSet rs) throws SQLException
	{
		this(
			rs.getInt      (    "id"),
			rs.getInt      (    "wish_list_id"),
			rs.getInt      (    "creator_user_id"),
			rs.getString   (    "name"),
			rs.getString   (    "url"),
			rs.getString   (    "image_url"),
			rs.getString   (    "description"),
			Database.gnvInt(rs, "covered_by_user_id"),
			rs.getBoolean  (    "fulfilled"),
			rs.getInt      (    "priority")
		);
	}
	
	
	
	public final User getCoveredByUser(Connection cn) throws SQLException
	{
		if (coveredByUserID == null)
			return null;
		
		if (coveredByUser == null)
			coveredByUser = User.findByID(cn, coveredByUserID);
		
		return coveredByUser;
	}
	
	public final WishList getWishList(Connection cn) throws SQLException
	{
		if (wishList == null)
			wishList = WishList.findByID(cn, wishListID);
		
		return wishList;
	}
	
	
	
	public static final WishListItem findByID(Connection cn, int itemID) throws SQLException
	{
		PreparedStatement st = cn.prepareStatement("SELECT * FROM wish_list_items WHERE id = ?");
		st.setInt(1, itemID);
		ResultSet rs = st.executeQuery();
		
		if (rs.next() == false)
			return null;
		
		WishListItem item = new WishListItem(rs);
		
		rs.close();
		st.close();
		
		return item;
	}
	
	public static final WishListItem[] findListByWishList(Connection cn, int wishListID) throws SQLException
	{
		ArrayList<WishListItem> items = new ArrayList<WishListItem>();
		
		PreparedStatement st = cn.prepareStatement(
			"SELECT * " +
			"FROM wish_list_items " +
			"WHERE wish_list_id = ? " +
			"ORDER BY priority ASC");
		st.setInt(1, wishListID);
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
			items.add(new WishListItem(rs));
		
		rs.close();
		st.close();
		
		return items.toArray(new WishListItem[items.size()]);
	}
	
	public static final WishListItem[] findListByUnfulfilledByUser(Connection cn, int userID) throws SQLException
	{
		ArrayList<WishListItem> items = new ArrayList<WishListItem>();
		
		PreparedStatement st = cn.prepareStatement(
			"SELECT wish_list_items.* " +
			"FROM wish_list_items " +
			"WHERE covered_by_user_id = ? AND fulfilled = FALSE");
		st.setInt(1, userID);
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
			items.add(new WishListItem(rs));
		
		rs.close();
		st.close();
		
		return items.toArray(new WishListItem[items.size()]);
	}
	
	
	
	public static final int create(Connection cn, int wishListID, int creatorUserID, String name, String url, String imageURL, String description) throws SQLException
	{
		String query =
			"INSERT INTO wish_list_items (" +
				"wish_list_id, " +
				"creator_user_id, " +
				"name, " +
				"url," +
				"image_url," +
				"description" +
			")" +
			"VALUES (?, ?, ?, ?, ?, ?)" +
			"RETURNING id";
		
		PreparedStatement st = cn.prepareStatement(query);
		
		st.setInt   (1, wishListID);
		st.setInt   (2, creatorUserID);
		st.setString(3, name);
		st.setString(4, url);
		st.setString(5, imageURL);
		st.setString(6, description);
		
		ResultSet rs = st.executeQuery();
		rs.next();
		
		int itemID = rs.getInt(1);
		
		rs.close();
		st.close();
		
		return itemID;
	}

	
	
	public static void setCovered(Connection cn, int itemID, int userID, boolean fulfilled) throws SQLException
	{
		String query =
			"UPDATE wish_list_items SET " +
				"covered_by_user_id = ?, " +
				"fulfilled = ? " +
			"WHERE id = ?";
		
		PreparedStatement st = cn.prepareStatement(query);
		
		st.setInt    (1, userID);
		st.setBoolean(2, fulfilled);
		st.setInt    (3, itemID);
		
		st.executeUpdate();
		st.close();
	}
	
	public static void setUncovered(Connection cn, int itemID) throws SQLException
	{
		String query =
			"UPDATE wish_list_items SET " +
				"covered_by_user_id = NULL, " +
				"fulfilled = false " +
			"WHERE id = ?";
		
		PreparedStatement st = cn.prepareStatement(query);
		st.setInt(1, itemID);
		
		st.executeUpdate();
		st.close();
	}
}