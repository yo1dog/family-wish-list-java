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
	public final String  name;
	public final String  url;
	public final String  imageURL;
	public final String  description;
	public final Integer coveredByUserID;
	public final boolean fulfilled;
	public final int     priority;
	
	private User coveredByUser;
	
	private WishListItem(
		int     id,
		int     wishListID,
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
			coveredByUser = User.getByID(cn, coveredByUserID);
		
		return coveredByUser;
	}
	
	
	
	public static final WishListItem[] getListByWishList(Connection cn, int wishListID) throws SQLException
	{
		ArrayList<WishListItem> items = new ArrayList<WishListItem>();
		
		PreparedStatement st = cn.prepareStatement(
			"SELECT * " +
			"FROM wish_list_items " +
			"WHERE wish_list_id = ? " +
			"ORDER BY priority DESC");
		st.setInt(1, wishListID);
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
			items.add(new WishListItem(rs));
		
		rs.close();
		st.close();
		
		return items.toArray(new WishListItem[items.size()]);
	}
	
	
	
	public static int create(Connection cn, int wishListID, String name, String url, String imageURL, String description) throws SQLException
	{
		String query =
			"INSERT INTO wish_list_items (" +
				"wish_list_id, " +
				"name, " +
				"url," +
				"image_url," +
				"description" +
			")" +
			"VALUES (?, ?, ?, ?, ?)" +
			"RETURNING id";
		
		PreparedStatement st = cn.prepareStatement(query);
		
		st.setInt   (1, wishListID);
		st.setString(2, name);
		st.setString(3, url);
		st.setString(4, imageURL);
		st.setString(5, description);
		
		ResultSet rs = st.executeQuery();
		rs.next();
		
		int itemID = rs.getInt(1);
		
		rs.close();
		st.close();
		
		return itemID;
	}
}