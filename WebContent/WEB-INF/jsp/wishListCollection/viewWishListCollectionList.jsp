<%@page import="java.sql.Connection"%>
<%@page import="net.awesomebox.fwl.models.WishListCollection"%>
<%@page import="net.awesomebox.fwl.models.User"%>
<%@page import="net.awesomebox.servletmanager.ServletHelper"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
User loggedInUser = (User)request.getAttribute("loggedInUser");
Connection cn = (Connection)request.getAttribute("cn");

WishListCollection[] collections = loggedInUser.getCollections(cn);
%>

<t:header />
<h2>Collections</h2>

<%
if (collections.length == 0 )
{
	// show message if there are no wish list collections
	%><p>You have no collections! Create one by clicking the <strong>Create Collection</strong> link above.<p><%
}
else
{
	// show the collections
	%><ul><%
	
	for (int i = 0; i < collections.length; ++i)
	{
		%><li><a href="/collection?id=<%=collections[i].id%>"><%=ServletHelper.escapeHTML(collections[i].name)%></a></li><%
	}
	
	%></ul><%
}
%>
<t:footer />