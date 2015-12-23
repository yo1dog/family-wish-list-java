<%@page import="net.awesomebox.fwl.controllers.Home.CoveredCollection"%>
<%@page import="net.awesomebox.fwl.controllers.Home.CoveredWishList"%>
<%@page import="java.sql.Connection"%>
<%@page import="net.awesomebox.fwl.models.WishListItem"%>
<%@page import="net.awesomebox.fwl.models.WishListCollection"%>
<%@page import="net.awesomebox.fwl.models.User"%>
<%@page import="net.awesomebox.servletmanager.ServletHelper"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
User loggedInUser = (User)request.getAttribute("loggedInUser");
Connection cn = (Connection)request.getAttribute("cn");

CoveredCollection[] coveredCollections = (CoveredCollection[])request.getAttribute("coveredCollections");
%>

<t:header>
  <jsp:attribute name="head">
	  <script type="text/javascript" src="/js/ajax.js"></script>
	  <script type="text/javascript" src="/js/script.js"></script>
	  
	  <script type="text/javascript">
	  function setItemFullfilled(itemID) {
	    var itemCheckboxElem = document.getElementById("itemCheckbox" + itemID);
	    
		var fulfilled =itemCheckboxElem .checked;
		
        setItemStatusHTML(itemID, "...");
        
        ItemUpdater.setCovered(itemID, fulfilled, function(err) {
          if (err) {
            console.error(err);
            
            setItemStatusHTML(itemID, "Oops, something went wrong. Please try again latter.");
            return;
          }
          
          setItemStatusHTML(itemID, "");
        });
      }
  	  
      function setItemStatusHTML(itemID, html) {
        var itemStatusSpan = document.getElementById("itemStatusSpan" + itemID);
        itemStatusSpan.innerHTML = html;
      }
    </script>
  </jsp:attribute>
</t:header>

<h2>Profile</h2>

<h3>Gifts Checklist</h3>
<p>These are the gifts that you said you are going to get. Once you get the item, you can check it off.</p>

<ul>
	<%
	for (int i = 0; i < coveredCollections.length; ++i)
	{
		CoveredCollection coveredCollection = coveredCollections[i];
		
		%>
		<li>
		  <a href="/collection?id=<%=coveredCollection.collection.id%>"><%=ServletHelper.escapeHTML(coveredCollection.collection.name)%></a>
		  
		  <ul>
			  <%
			  for (CoveredWishList coveredWishList : coveredCollection.coveredWishLists)
			  {
				  User user = coveredWishList.wishList.getOwner(cn);
				  
				  %>
				  <li style="margin-top: 10px;">
				    <span><a href="/wishlist?id=<%=coveredWishList.wishList.id%>"><%=ServletHelper.escapeHTML(user.firstName)%></a></span>
				    
				    <ul>
				      <%
				      for (WishListItem coveredItem : coveredWishList.coveredItems)
				      {
				    	  %>
				    	  <li>
				    	    <input type="checkbox" <%=coveredItem.fulfilled? "checked=\"checked\"": ""%> id="itemCheckbox<%=coveredItem.id%>" onChange="setItemFullfilled(<%=coveredItem.id%>);" />
				    	    <a href="/wishlist?id=<%=coveredItem.wishListID%>#item<%=coveredItem.id%>"><%=ServletHelper.escapeHTML(coveredItem.name)%></a> <span id="itemStatusSpan<%=coveredItem.id%>"></span>
				    	  </li>
				    	  <%
                      }
					  %>
				    </ul>
				  </li>
				  <%
			  }
			  %>
		  </ul>
    </li>
    <%
	}
	%>
</ul>
<t:footer />