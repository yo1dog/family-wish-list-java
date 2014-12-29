<%@page import="net.awesomebox.fwl.models.WishListItem"%>
<%@page import="java.sql.Connection"%>
<%@page import="net.awesomebox.fwl.models.User"%>
<%@page import="net.awesomebox.fwl.models.WishList"%>
<%@page import="net.awesomebox.fwl.models.WishListCollection"%>
<%@page import="net.awesomebox.servletmanager.ServletHelper"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
Connection cn = (Connection)request.getAttribute("cn");
User loggedInUser = (User)request.getAttribute("loggedInUser");

WishList           wishList      = (WishList)request.getAttribute("wishList");
WishListItem[]     items         = wishList.getItems(cn);
WishListCollection collection    = wishList.getCollection(cn);
User               wishListOwner = wishList.getOwner(cn);

boolean isLoggedInUsersWishList = wishListOwner.id == loggedInUser.id;

String listNameEscaped;
if (isLoggedInUsersWishList)
	listNameEscaped = "Your";
else
{
	char lastChar = wishListOwner.firstName.charAt(wishListOwner.firstName.length() - 1);
	listNameEscaped = wishListOwner.firstName + ((lastChar == 's' || lastChar == 'S')? "'" : "'s");
}
%>

<t:header />
<h2><a href="/collection?id=<%=collection.id%>"><%=ServletHelper.escapeHTML(collection.name)%></a> - <%=listNameEscaped%> Wish List</h2>

<%
if (isLoggedInUsersWishList)
{
	%><a href="/item/create?wishListID=<%=wishList.id%>">Add Item</a><br /><%
}

// show message if there are no items
if (items.length == 0)
{
	if (isLoggedInUsersWishList)
	{
		%><p>You have no items on your list. Add items by clicking the <strong>Add Item</strong> link above.<p><%
	}
	else
	{
		%><p>There are no items on this list. Tell <%=ServletHelper.escapeHTML(wishListOwner.firstName)%> to add some!<p><%
	}
}
// show the items
else
{
	%>
	<br />
	<table class="wishlist">
		<thead>
			<tr>
				<th>Image</th>
				<th>Name</th>
				<th>Description</th>
				<th>Status</th>
			</tr>
		</thead>
		<tbody>
		<%
		for (int i = 0; i < items.length; ++i)
		{
			WishListItem item = items[i];
			
			String urlEscaped = null;
			if (item.url != null)
				urlEscaped = ServletHelper.escapeHTML(item.url);
			
			// image
			String imageHTML = "";
			if (item.imageURL != null)
			{
				imageHTML = "<img src=\"" + ServletHelper.escapeHTML(item.imageURL) + "\" />";
				
				if (item.url != null)
					imageHTML = "<a href=\"" + urlEscaped + "\">" + imageHTML + "</a>";
			}
			
			// name
			String nameHTML = ServletHelper.escapeHTML(item.name);
			if (item.url != null)
				nameHTML = "<a href=\"" + urlEscaped + "\">" + nameHTML + "</a>";
			
			// description
			String descriptionHTML = "";
			if (item.description != null)
				descriptionHTML = ServletHelper.newlinesToBR(ServletHelper.escapeHTML(item.description));
			
			// covered
			String coveredHTML = "";
			
			// dont show if it is the logged-in user's wishlist
			if (!isLoggedInUsersWishList)
			{
				User coveredByUser = item.getCoveredByUser(cn);
				
				if (coveredByUser != null)
					coveredHTML = ServletHelper.escapeHTML(coveredByUser.firstName) + " " + (item.fulfilled? "got this." : "plans on getting this.");
			}
			%>
			<tr>
				<td><%=imageHTML%></td>
				<td><%=nameHTML%></td>
				<td><%=descriptionHTML%></td>
				<td><%=coveredHTML%></td>
			</tr>
			<%
		}
		%>
		</tbody>
	</table>
	<%
}
%>
<t:footer />