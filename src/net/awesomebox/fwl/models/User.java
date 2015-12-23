package net.awesomebox.fwl.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.awesomebox.fwl.database.PostgreSQLErrorCodes;

public class User
{
	public final int    id;
	public final String firstName;
	public final String lastName;
	public final String email;
	public final String avatarImageURL;
	
	private WishListCollection collections[];
	
	
	private User(
		int    id,
		String firstName,
		String lastName,
		String email,
		String avatarImageURL
	)
	{
		this.id             = id;
		this.firstName      = firstName;
		this.lastName       = lastName;
		this.email          = email;
		this.avatarImageURL = avatarImageURL;
	}
	
	private User(ResultSet rs) throws SQLException
	{
		this(
			rs.getInt   ("id"),
			rs.getString("first_name"),
			rs.getString("last_name"),
			rs.getString("email"),
			rs.getString("avatar_image_url")
		);
	}
	
	
	public final WishListCollection[] getCollections(Connection cn) throws SQLException {
		if (collections == null) {
			collections = WishListCollection.findListByMember(cn, id);
		}
		
		return collections;
	}
	
	
	public static User findByID(Connection cn, int userID) throws SQLException
	{
		PreparedStatement st = cn.prepareStatement("SELECT * FROM users WHERE id = ?");
		st.setInt(1, userID);
		ResultSet rs = st.executeQuery();
		
		User user = rs.next()? new User(rs) : null;
		
		rs.close();
		st.close();
		
		return user;
	}
	
	public static User findByLogin(Connection cn, String email, byte[] passwordHash) throws SQLException
	{
		PreparedStatement st = cn.prepareStatement("SELECT * FROM users WHERE email = ? AND password_hash = ?");
		st.setString(1, email);
		st.setBytes (2, passwordHash);
		
		ResultSet rs = st.executeQuery();
		
		User user = rs.next()? new User(rs) : null;
		
		rs.close();
		st.close();
		
		return user;
	}
	
	
	/**
	 * Returns the ID of the new user or -1 if the email is already in use.
	 * 
	 * @param cn
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param passwordHash
	 * @return
	 * @throws SQLException
	 */
	public static int register(Connection cn, String firstName, String lastName, String email, byte[] passwordHash) throws SQLException
	{
		String query =
			"INSERT INTO users (" +
				"first_name, " +
				"last_name, " +
				"email, " +
				"password_hash" +
			")" +
			"VALUES (?, ?, ?, ?)" +
			"RETURNING id";
		
		PreparedStatement st = cn.prepareStatement(query);
		
		st.setString(1, firstName);
		st.setString(2, lastName);
		st.setString(3, email);
		st.setBytes (4, passwordHash);
		
		try
		{
			ResultSet rs = st.executeQuery();
			rs.next();
			
			int userID = rs.getInt(1);
			
			rs.close();
			st.close();
			
			return userID;
		}
		catch (SQLException e)
		{
			// if there was a unique key violation then the email is already in use
			if (e.getSQLState().equals(PostgreSQLErrorCodes.UNIQUE_VIOLATION))
				return -1;
			
			throw e;
		}
	}
	
	/**
	 * Updates the user's password and returns the ID of the user.
	 * 
	 * @param cn
	 * @param email
	 * @param passwordHash
	 * @return
	 * @throws SQLException
	 */
	public static int updatePassword(Connection cn, String email, byte[] passwordHash) throws SQLException
	{
		String query = "UPDATE users SET password_hash = ? WHERE email = ? RETURNING id";
		PreparedStatement st = cn.prepareStatement(query);
		
		st.setBytes (1, passwordHash);
		st.setString(2, email);
		
		ResultSet rs = st.executeQuery();
		int userID = rs.next()? rs.getInt(1) : -1;
		
		rs.close();
		st.close();
		
		return userID;
	}
	
	
	public static boolean lookupIsMemeberOfCollection(Connection cn, int userID, int collectionID) throws SQLException
	{
	    return true;
	    /*
		PreparedStatement st = cn.prepareStatement(
			"SELECT 1 " +
			"FROM wish_lists " + 
			"WHERE owner_user_id = ? AND wish_list_collection_id = ?");
		st.setInt(1, userID);
		st.setInt(2, collectionID);
		
		ResultSet rs = st.executeQuery();
		boolean exists = rs.next();
		
		rs.close();
		st.close();
		
		return exists;
		*/
	}
}
