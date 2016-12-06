package net.awesomebox.fwl.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.awesomebox.fwl.database.Database;

public class Suggestion
{
	public final int     id;
	public final Integer authorUserID;
	public final String  text;
	public final String  referringPage;
	
	private User author;
	
	private Suggestion(
		int     id,
		Integer authorUserID,
		String  text,
		String  referringPage
	)
	{
		this.id            = id;
		this.authorUserID  = authorUserID;
		this.text          = text;
		this.referringPage = referringPage;
	}
	
	private Suggestion(ResultSet rs) throws SQLException
	{
		this(
			rs.getInt(          "id"),
			Database.gnvInt(rs, "author_user_id"),
			rs.getString(       "text"),
			rs.getString(       "referring_page")
		);
	}
	
	
	
	public final User getAuthor(Connection cn) throws SQLException
	{
		if (authorUserID != null && author == null)
			author = User.findByID(cn, authorUserID);
		
		return author;
	}
	
	
	
	public static final Suggestion[] findAll(Connection cn) throws SQLException
	{
		ArrayList<Suggestion> suggestions = new ArrayList<Suggestion>();
		
		PreparedStatement st = cn.prepareStatement("SELECT * FROM suggestions ORDER BY created_timestamp DESC");
		ResultSet rs = st.executeQuery();
		
		while(rs.next())
			suggestions.add(new Suggestion(rs));
		
		rs.close();
		st.close();
		
		return suggestions.toArray(new Suggestion[suggestions.size()]);
	}
	
	
	
	public static int create(Connection cn, Integer authorUserID, String text, String referringPage) throws SQLException
	{
		String query =
			"INSERT INTO suggestions (" +
				"author_user_id, " +
				"text, " +
				"referring_page" +
			")" +
			"VALUES (?, ?, ?)" +
			"RETURNING id";
		
		PreparedStatement st = cn.prepareStatement(query);
		
		st.setInt   (1, authorUserID);
		st.setString(2, text);
		st.setString(3, referringPage);
		
		ResultSet rs = st.executeQuery();
		rs.next();
		
		int suggestionID = rs.getInt(1);
		
		rs.close();
		st.close();
		
		return suggestionID;
	}
}
