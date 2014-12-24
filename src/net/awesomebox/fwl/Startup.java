package net.awesomebox.fwl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.awesomebox.fwl.database.Database;
import net.awesomebox.servletmanager.ConfigManager;
import net.awesomebox.servletmanager.ConfigTemplate;

public class Startup implements ServletContextListener
{
	@Override
	public void contextInitialized(ServletContextEvent sce)
	{
		System.out.println("Starting in context listener...");
		
		Config.init();
		Database.init();
		
		ConfigTemplate configTemplate = new ConfigTemplate();
		configTemplate.debug = Config.DEBUG;
		
		ConfigManager.setConfig(configTemplate);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce)
	{
	}
}
