package net.awesomebox.fwl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Config
{
	public static final String  DATA_DIRECTORY;
	
	// security
	/** cryptographic key used to encrypt password hashes. */
	public static final byte[] PASSWORD_PRIVATE_KEY;
	
	// other
	public static final boolean DEBUG;
	
	
	public static void init()
	{
		// calls static constructor
	}
	
	
	static
	{
		Properties properties = new Properties();
		
		// read the location of the property file
		InputStream dataDirectoryConfigInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("../../META-INF/datadir.conf"); // relative to webapps/munch/WEB-INF/classes/
		InputStreamReader is = new InputStreamReader(dataDirectoryConfigInputStream);
		BufferedReader br = new BufferedReader(is);
		
		String dataDirectory = null;
		
		try
		{
			dataDirectory = br.readLine();
			
			if (dataDirectory == null || dataDirectory.length() == 0)
				System.err.println("Data directory config file is empty.");
		}
		catch (IOException e)
		{
			System.err.println("Unable to read data directory config file.");
			e.printStackTrace();
		}
		
		if (dataDirectory != null)
		{
			String configFileLocation = dataDirectory + "config.properties";
			
			System.out.println("Data Directory: " + dataDirectory);
			System.out.println("Config File: " + configFileLocation);
			
			File configFile = new File(configFileLocation);
			
			if (!configFile.exists())
				System.err.println("Config file does not exist at " + configFileLocation);
			else
			{
				try
				{
					InputStream in = new FileInputStream(configFile);
					
					try
					{
						properties.load(in);
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
					
					try
					{
						in.close();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
				catch (FileNotFoundException e)
				{
					System.err.println("Unable to read config file at " + configFileLocation);
					e.printStackTrace();
				}
			}
		}
		
        
		DATA_DIRECTORY = dataDirectory;
        
		// password private key
		byte[] temp;
		try
		{
			temp = "6S87CzrP40MgqfvJzynBsthY0v8QajNQt/zub3r3GrE=".getBytes("UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			temp = new byte[] {101, 76, 106, 70, 43, 111, 53, 103, 88, 85, 113, 86, 117, 87, 69, 100, 73, 56, 118, 87, 67, 73, 88, 112, 66, 118, 119, 119, 75, 97, 67, 78, 77, 97, 108, 109, 78, 75, 78, 117, 116, 73, 77, 61};
		}
		PASSWORD_PRIVATE_KEY = temp;
		
		DEBUG = getProperty(properties, "debug", false);
		if (DEBUG)
			System.out.println("DEBUG MODE");
	}
	
	@SuppressWarnings("unused")
	private static String getProperty(Properties properties, String propertyName)
	{
		return getProperty(properties, propertyName, null);
	}
	private static String getProperty(Properties properties, String propertyName, String defaultValue)
	{
		return properties.getProperty(propertyName, defaultValue);
	}
	
	@SuppressWarnings("unused")
	private static int getProperty(Properties properties, String propertyName, int defaultValue)
	{
		String strValue = properties.getProperty(propertyName);
		
		if (strValue == null)
			return defaultValue;
		
		try
		{
			return Integer.parseInt(strValue);
		}
		catch (NumberFormatException e)
		{
			System.err.println("Invalid value in config for property \"" + propertyName + "\": Not a valid integer.");
			return defaultValue;
		}
	}
	
	private static boolean getProperty(Properties properties, String propertyName, boolean defaultValue)
	{
		String strValue = properties.getProperty(propertyName);
		
		if (strValue == null)
			return defaultValue;
		
		return strValue.toLowerCase().equals("true");
	}
	
	@SuppressWarnings("unused")
	private static String[] getPropertyArray(Properties properties, String propertyName, String seperator, String[] defaultValue)
	{
		String strValue = properties.getProperty(propertyName);
		
		if (strValue == null)
			return defaultValue;
		
		List<String> items = Arrays.asList(strValue.split(seperator));
		for (int i = 0; i < items.size(); ++i)
		{
			if (items.get(i).trim().length() == 0)
			{
				items.remove(i);
				--i;
			}
		}
		
		return (String[])items.toArray();
	}
	
	@SuppressWarnings("unused")
	private static String getPropertyPath(Properties properties, String propertyName, String defaultValue)
	{
		String value = properties.getProperty(propertyName, defaultValue);
		
		if (!value.endsWith(File.separator))
			value += File.separator;
		
		File file = new File(value);
		if (!file.exists())
			System.err.println("Location " + propertyName + " does not exist at " + value);
		
		return value;
	}
}
