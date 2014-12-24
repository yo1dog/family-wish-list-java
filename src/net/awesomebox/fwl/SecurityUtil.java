package net.awesomebox.fwl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Manages the various security aspects of the server.
 */
public class SecurityUtil
{
	/**
	 * Hashes a password to be stored safely.
	 * 
	 * @param password
	 * 	Password to hash.
	 * 
	 * @return
	 * 	Hashed password.
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] hashPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		byte[] passwordBytes = password.getBytes("UTF-8");
		byte[] buffer = new byte[passwordBytes.length + Config.PASSWORD_PRIVATE_KEY.length];
		
		System.arraycopy(passwordBytes,               0, buffer, 0,                    passwordBytes.length);
		System.arraycopy(Config.PASSWORD_PRIVATE_KEY, 0, buffer, passwordBytes.length, Config.PASSWORD_PRIVATE_KEY.length);
		
		return MessageDigest.getInstance("SHA-256").digest(buffer);
		
	}
}