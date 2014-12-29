<%@page import="java.sql.Connection"%>
<%@page import="net.awesomebox.fwl.models.User"%>
<%@page import="net.awesomebox.fwl.models.WishList"%>
<%@page import="net.awesomebox.fwl.models.WishListCollection"%>
<%@page import="net.awesomebox.servletmanager.ServletHelper"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
Connection cn = (Connection)request.getAttribute("cn");
WishListCollection collection = (WishListCollection)request.getAttribute("collection");
Integer loggedInUserWishListIndex = (Integer)request.getAttribute("loggedInUserWishListIndex");

WishList[] wishLists = collection.getWishLists(cn);
%>

<t:header />
<h2><%=ServletHelper.escapeHTML(collection.name)%></h2>

<%
// show the logged-in user's wish list
if (loggedInUserWishListIndex != null)
{
	%><a href="/wishlist?id=<%=wishLists[loggedInUserWishListIndex].id%>">Your List</a><%
}

// show message if there are no other wish lists
if (wishLists.length == 0 || (wishLists.length == 1 && loggedInUserWishListIndex != null))
{
	%><p>There is no one else in this collection! Add people by clicking the <strong>Invite</strong> button above.<p><%
}
// show the wish lists
else
{
	%><ul><%
	
	for (int i = 0; i < wishLists.length; ++i)
	{
		if (i == loggedInUserWishListIndex)
			continue;
		
		WishList wishList = wishLists[i];
		User wishListOwner = wishList.getOwner(cn);
		
		%><li><a href="/wishlist?id=<%=wishList.id%>"><%=ServletHelper.escapeHTML(wishListOwner.firstName)%></a></li><%
	}
	
	%></ul><%
}
%>
<t:footer />