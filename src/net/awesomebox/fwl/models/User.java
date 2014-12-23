package net.awesomebox.fwl.models;

public class User
{
	public final int    id;
	public final String username;
	public final String firstName;
	public final String lastName;
	public final String email;
	public final String avatarImageURL;
	public final String passwordHash;
	
	private WishListCollection collections[];
	
	
	User(
		int    id,
		String username,
		String firstName,
		String lastName,
		String email,
		String avatarImageURL,
		String passwordHash
	)
	{
		this.id             = id;
		this.username       = username;
		this.firstName      = firstName;
		this.lastName       = lastName;
		this.email          = email;
		this.avatarImageURL = avatarImageURL;
		this.passwordHash   = passwordHash; 
	}
	
	public final WishListCollection[] getCollections() { return collections; }
}
