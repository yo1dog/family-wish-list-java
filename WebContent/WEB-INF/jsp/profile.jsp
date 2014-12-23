<%@page import="net.awesomebox.fwl.models.WishListCollection"%>
<%@page import="net.awesomebox.fwl.models.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
User loggedInUser = (User)request.getAttribute("loggedInUser");
WishListCollection[] collections = loggedInUser.getCollections();
%>

<t:template>
  <h1>${collection.name}</h1>
  
  <%
  if (collections.length == 0 )
  {
    // show message if there are no wish list collections
    %><p>You have no collections! Create one by clicking the <strong>Create Collection</strong> button above.<p><%
  }
  else
  {
    // show the collections
    %><ul><%
    
    for (int i = 0; i < collections.length; ++i)
    {
      %><li><a href="/collection?id=${collections[i].id}">${collections[i].name}</a></li><%
    }
    
    %></ul><%
  }
  %>
</t:template>