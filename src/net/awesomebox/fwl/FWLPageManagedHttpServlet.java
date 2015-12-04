package net.awesomebox.fwl;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.awesomebox.servletmanager.ManagedServletException;
import net.awesomebox.servletmanager.exceptions.UnauthorizedException;

public class FWLPageManagedHttpServlet extends FWLManagedHttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected boolean onManagedServletException(HttpServletRequest request, HttpServletResponse response, HttpMethod method, ManagedServletException e) throws ServletException, IOException
	{
		if (e instanceof UnauthorizedException)
		{
			String path = request.getRequestURI();
			path = path.replace(request.getContextPath(), "");
			
			String queryString = request.getQueryString();
			
			String callbackURL = path;
			if (queryString != null)
				callbackURL += "?" + queryString;
			
			response.sendRedirect("/login?callbackURL=" + URLEncoder.encode(callbackURL, "UTF-8"));
			return true;
		}
		
		return super.onManagedServletException(request, response, method, e);
	}
}
