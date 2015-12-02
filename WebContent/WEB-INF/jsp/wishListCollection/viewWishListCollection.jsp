<%@page import="java.sql.Connection"%>
<%@page import="net.awesomebox.fwl.models.User"%>
<%@page import="net.awesomebox.fwl.models.WishList"%>
<%@page import="net.awesomebox.fwl.models.WishListCollection"%>
<%@page import="net.awesomebox.servletmanager.ServletHelper"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
Connection         cn                   = (Connection)        request.getAttribute("cn");
WishListCollection collection           = (WishListCollection)request.getAttribute("collection");
WishList           loggedInUserWishList = (WishList)          request.getAttribute("loggedInUserWishList");
User               loggedInUser         = (User)              request.getAttribute("loggedInUser");

WishList[] wishLists = collection.getWishLists(cn);

// check if the logged in user is the owner of the list
boolean loggedInUserIsOwner = collection.ownerUserID == loggedInUser.id;
%>

<t:header />
<h2><%=ServletHelper.escapeHTML(collection.name)%></h2>


<%
// show the logged-in user's wish list
if (loggedInUserWishList != null)
{
	%><a href="/wishlist?id=<%=loggedInUserWishList.id%>">Your Wish List</a><%
}
else
{
	%>
	<form name="createCollectionForm" method="post" action="/wishlist/create">
		<input type="hidden" name="collectionID" value="<%=collection.id%>" />
		<input type="submit" value="Create Your Wish List" />
	</form>
	<%
}

// show message if there are no other wish lists
if (wishLists.length == 0 || (wishLists.length == 1 && loggedInUserWishList != null))
{
	%><p>There is no one else in this collection! <%
	
	if (loggedInUserIsOwner)
	{
	  %>Add people by clicking the <strong>Invite</strong> button above.<%
	}
	else
	{
		%>Tell the collection owner to invite people.<%
	}
	
	%></p><%
}

// show the wish lists
else
{
	%><ul><%
	
	for (int i = 0; i < wishLists.length; ++i)
	{
		if (wishLists[i] == loggedInUserWishList)
			continue;
		
		WishList wishList = wishLists[i];
		User wishListOwner = wishList.getOwner(cn);
		
		%><li><a href="/wishlist?id=<%=wishList.id%>"><%=ServletHelper.escapeHTML(wishListOwner.firstName)%></a></li><%
	}
	
	%></ul><%
}
%>
<t:footer />