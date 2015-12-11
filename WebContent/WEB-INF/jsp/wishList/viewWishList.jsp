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
WishListCollection collection    = wishList.getCollection(cn);
User               wishListOwner = wishList.getOwner(cn);

// separate items
WishListItem[] items = wishList.getItems(cn);

// count number of items by the owner
int numItemsByWishListOwner = 0;
for (int i = 0; i < items.length; ++i)
{
  if (items[i].creatorUserID == wishList.ownerUserID)
    ++numItemsByWishListOwner;
}

WishListItem[] itemsByWishListOwner = new WishListItem[numItemsByWishListOwner];
WishListItem[] itemsByOthers        = new WishListItem[items.length - numItemsByWishListOwner];

int itemsByWishListOwnerIndex = 0;
int itemsByOthersIndex = 0;

for (int i = 0; i < items.length; ++i)
{
  if (items[i].creatorUserID == wishList.ownerUserID)
  {
    itemsByWishListOwner[itemsByWishListOwnerIndex] = items[i];
    ++itemsByWishListOwnerIndex;
  }
  else
  {
    itemsByOthers[itemsByOthersIndex] = items[i];
    ++itemsByOthersIndex;
  }
}

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

<t:header>
  <jsp:attribute name="head">
    <script type="text/javascript" src="/js/ajax.js"></script>
    <script type="text/javascript" src="/js/script.js"></script>
    
    <script type="text/javascript">
      function setItemCovered(itemID, fulfilled) {
        setItemStatusHTML(itemID, "...");
        
        ItemUpdater.setCovered(itemID, fulfilled, function(err) {
          if (err) {
            console.error(err);
            
            setItemStatusHTML(itemID, "Oops, something went wrong. Please try again latter.");
            return;
          }
          
          var statusHTML = "<strong>You</strong> ";
          var rowClass = "";
          
          if (fulfilled) {
            statusHTML += "got this. - <a href=\"javascript:setItemCovered(" + itemID + ", false);\">Actually, I haven't got it yet.</a>";
            rowClass = "item-user-covered-fulfilled";
          }
          else {
            statusHTML += "plan on getting this. - <a href=\"javascript:setItemCovered(" + itemID + ", true);\">Got it.</a> - <a href=\"javascript:setItemUncovered(" + itemID + ");\">I'm not going to get this.</a>";
            rowClass = "item-user-covered-unfulfilled";
          }
          
          setItemStatusHTML(itemID, statusHTML);
          setItemRowClass(itemID, rowClass);
        });
      }
      
      function setItemUncovered(itemID) {
        setItemStatusHTML(itemID, "...");
        
        ItemUpdater.setCovered(itemID, null, function(err) {
          if (err) {
            console.error(err);
            
            setItemStatusHTML(itemID, "Oops, something went wrong. Please try again latter.");
            return;
          }
          
          
          var statusHTML = "No one is getting this. <a href=\"javascript:setItemCovered(" + itemID + ", false);\">I'll get this.</a>";
          setItemStatusHTML(itemID, statusHTML);
          setItemRowClass(itemID, "");
        });
      }
      
      function setItemStatusHTML(itemID, html) {
        var itemStatusCell = document.getElementById("itemStatusCell" + itemID);
        itemStatusCell.innerHTML = html;
      }
      
      function setItemRowClass(itemID, klass) {
        var itemRow = document.getElementById("item" + itemID);
        itemRow.className = klass;
      }
      
  	  function submitDeleteItem(event) {
  	    if (!confirm('Are you sure you want to delete this item?')) {
  	      event.preventDefault();
  	      return false;
  	    }
  	    
  	    return true;
  	  }
    </script>
  </jsp:attribute>
</t:header>
<h2><a href="/collection?id=<%=collection.id%>"><%=ServletHelper.escapeHTML(collection.name)%></a> - <%=listNameEscaped%> Wish List</h2>

<a href="/item/create?wishListID=<%=wishList.id%>">Add Item</a><br />
<br />

<table class="wishlist">
  <thead>
    <tr>
      <th>Image</th>
      <th>Name</th>
      <th>Description</th>
      <th>Status</th>
      <th></th>
    </tr>
  </thead>
  <tbody>
    <%
    for (int r = 0; r < 2; ++r)
    {
      boolean firstPass = r == 0;
      WishListItem[] currentItems;
      
      if (firstPass)
      {
        // show items from the wish list owner first
        currentItems = itemsByWishListOwner;
        
        // show message if there are none
        if (currentItems.length == 0)
        {
        	String messageHTML;
        	
	        if (isLoggedInUsersWishList)
	        	messageHTML = "You have no items on your list. Add items by clicking the <strong>Add Item</strong> link above.";
	        else
	          messageHTML = ServletHelper.escapeHTML(wishListOwner.firstName) + " has not added any items.";
	        
	        %>
	        <tr>
	          <td colspan="5"><%=messageHTML%></td>
	        </tr>
	        <%
	        
	        continue;
        }
      }
      else
      {
        // show items from others next
        // don't show if it is the logged-in user's list
        if (isLoggedInUsersWishList)
        	continue;
        
        currentItems = itemsByOthers;
        
        // skip if there are none
        if (currentItems.length == 0)
          continue;
        
        %>
        <tr>
          <td colspan="5">Added by Others</td>
        </tr>
        <%
      }
      
      for (int i = 0; i < currentItems.length; ++i)
      {
        WishListItem item = currentItems[i];
        
        String urlEscaped = null;
        if (item.url != null)
          urlEscaped = ServletHelper.escapeHTML(item.url);
        
        // image
        String imageHTML = "";
        if (item.imageURL.length() > 0)
        {
          imageHTML = "<img src=\"" + ServletHelper.escapeHTML(item.imageURL) + "\" />";
          
          if (item.url.length() > 0)
            imageHTML = "<a href=\"" + urlEscaped + "\">" + imageHTML + "</a>";
        }
        
        // name
        String nameHTML = ServletHelper.escapeHTML(item.name);
        if (item.url.length() > 0)
          nameHTML = "<a href=\"" + urlEscaped + "\">" + nameHTML + "</a>";
        
        // description
        String descriptionHTML = "";
        if (item.description.length() > 0)
          descriptionHTML = ServletHelper.newlinesToBR(ServletHelper.escapeHTML(item.description));
        
        // status
        String rowClass = "item";
        String statusHTML = "";
        
        // dont show if it is the logged-in user's wishlist
        if (!isLoggedInUsersWishList)
        {
          User coveredByUser = item.getCoveredByUser(cn);
          
          if (coveredByUser == null)
            statusHTML = "No one is getting this. <a href=\"javascript:setItemCovered(" + item.id + ", false);\">I'll get this.</a>";
          else
          {
            boolean isCoveredByCurrentUser = coveredByUser.id == loggedInUser.id;
            
            if (isCoveredByCurrentUser)
            {
              statusHTML = "<strong>You</strong> ";
              
              if (item.fulfilled)
                statusHTML += "got this. - <a href=\"javascript:setItemCovered(" + item.id + ", false);\">Actually, I haven't got it yet.</a>";
              else
                statusHTML += "plan on getting this. - <a href=\"javascript:setItemCovered(" + item.id + ", true);\">Got it.</a> - <a href=\"javascript:setItemUncovered(" + item.id + ");\">I'm not going to get this.</a>";
            }
            else
            {
              statusHTML = "<strong>" + ServletHelper.escapeHTML(coveredByUser.firstName) + "</strong> ";
              
              if (item.fulfilled)
                statusHTML += "got this.";
              else
                statusHTML += "plans on getting this.";
            }
            
            if (item.fulfilled)
              rowClass += " " + (isCoveredByCurrentUser? "item-user-covered-fulfilled" : "item-covered-fulfilled");
            else
              rowClass += " " + (isCoveredByCurrentUser? "item-user-covered-unfulfilled" : "item-covered-unfulfilled");
          }
        }
        
        // edit
        String editHTML = "";
        
        %>
        <tr id="item<%=item.id%>" class="<%=rowClass%>">
          <td><%=imageHTML%></td>
          <td><%=nameHTML%></td>
          <td><%=descriptionHTML%></td>
          <td id="itemStatusCell<%=item.id%>"><%=statusHTML%></td>
          <td>
	          <%
	          	if (isLoggedInUsersWishList) {
	          	  %>
	          	  <a href="/item/update?wishListItemID=<%=item.id%>">Edit</a> -
	          	  <form name="updateItemForm" method="post" action="/item/delete"  onsubmit="return submitDeleteItem(event);" style="display: inline;">
					<input type="hidden" name="wishListItemID" value="<%=item.id%>"/>
				    <input type="submit" value="Delete Item" />
				  </form>
	          	  <%
	          	}
	          %>
          </td>
        </tr>
        <%
      }
    }
    %>
  </tbody>
</table>
<t:footer />