<%@page import="net.awesomebox.fwl.models.User"%>
<%@page import="net.awesomebox.fwl.models.WishList"%>
<%@page import="net.awesomebox.fwl.models.WishListCollection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
WishListCollection collection = (WishListCollection)request.getAttribute("collection");
int loggedInUserWishListIndex = (int)request.getAttribute("loggedInUserWishListIndex");

WishList[] wishLists = collection.getWishLists();
%>

<t:template>
  <h1>${collection.name}</h1>
  
  <%
  // show the logged-in user's wish list
  if (loggedInUserWishListIndex > -1)
  {
	  %><a href="/wishList?id=${wishLists[loggedInUserWishListIndex].id}">Your List</a><%
  }
  
  // show message if there are no other wish lists
  if (wishLists.length == 0 || (wishLists.length == 1 && loggedInUserWishListIndex > -1))
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
    	User wishListOwner = wishList.getOwner();
    	
    	%><li><a href="/wishList?id=${wishList.id}">${wishListOwner.firstName}</a></li><%
    }
    
    %></ul><%
  }
  %>
</t:template>