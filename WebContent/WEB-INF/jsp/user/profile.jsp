<%@page import="net.awesomebox.fwl.controllers.Home.UnfulfilledItemCollectionGroup"%>
<%@page import="net.awesomebox.fwl.controllers.Home.UnfulfilledItemWishListGroup"%>
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

UnfulfilledItemCollectionGroup[] unfulfilledItemCollectionGroups = (UnfulfilledItemCollectionGroup[])request.getAttribute("unfulfilledItemCollectionGroups");
%>

<t:header>
  <jsp:attribute name="head">
	  <script type="text/javascript" src="/js/ajax.js"></script>
	  <script type="text/javascript" src="/js/script.js"></script>
	  
	  <script type="text/javascript">
      function setItemCovered(itemID) {
        setItemStatusHTML(itemID, "...");
        
        removeItemListItem(itemID);
        ItemUpdater.setCovered(itemID, true, function(err) {
          if (err) {
            console.error(err);
            
            setItemStatusHTML(itemID, "Oops, something went wrong. Please try again latter.");
            return;
          }
          
          removeItemListItem(itemID);
        });
      }
      
      function setItemStatusHTML(itemID, html) {
        var itemStatusSpan = document.getElementById("itemStatusSpan" + itemID);
        itemStatusSpan.innerHTML = html;
      }
      
      function removeItemListItem(itemID) {
        // remove item
        var itemLI = document.getElementById("itemListItem" + itemID);
        var itemsUL = itemLI.parentNode;
        
        itemsUL.removeChild(itemLI);
        
        // remove wish list
        if (itemsUL.getElementsByTagName("li").length > 0) return;
        
        var wishListLI = itemsUL.parentNode;
        var wishListsUL = wishListLI.parentNode;
        
        wishListsUL.removeChild(wishListLI);
        
        // remove collection
        if (wishListsUL.getElementsByTagName("li").length > 0) return;
        
        var collectionLI = wishListsUL.parentNode;
        var collectionsUL = collectionLI.parentNode;
        
        collectionsUL.removeChild(collectionLI);
      }
      
      function removeWishListListItem(wishListID) {
        var wishListListItem = document.getElementById("wishListListItem" + wishListID);
      }
    </script>
  </jsp:attribute>
</t:header>

<h2>Profile</h2>

<h3>Gifts You Need to Get</h3>
<p>These are items that you said you are going to get but you have not gotten yet. If you have gotten the item, click the <strong>I got it</strong> link to the right of the item.</p>

<ul>
	<%
	for (int i = 0; i < unfulfilledItemCollectionGroups.length; ++i)
	{
		UnfulfilledItemCollectionGroup collectionGroup = unfulfilledItemCollectionGroups[i];
		
		%>
		<li>
		  <a href="/collection?id=<%=collectionGroup.collection.id%>"><%=ServletHelper.escapeHTML(collectionGroup.collection.name)%></a>
		  
		  <ul>
			  <%
			  for (UnfulfilledItemWishListGroup wishListGroup : collectionGroup.wishListGroups)
			  {
				  User user = wishListGroup.wishList.getOwner(cn);
				  
				  %>
				  <li>
				    <span><a href="/wishlist?id=<%=wishListGroup.wishList.id%>"><%=ServletHelper.escapeHTML(user.firstName)%></a></span>
				    
				    <ul>
				      <%
				      for (WishListItem item : wishListGroup.items)
				      {
				    	  %>
				    	  <li id="itemListItem<%=item.id%>">
				    	    <a href="/wishlist?id=<%=wishListGroup.wishList.id%>#item<%=item.id%>"><%=ServletHelper.escapeHTML(item.name)%></a>
				    	    - <span id="itemStatusSpan<%=item.id%>"><a href="javascript:setItemCovered(<%=item.id%>);">Got it.</a></span>
				    	  </li><%
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