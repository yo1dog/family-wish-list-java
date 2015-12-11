<%@page import="java.util.Map"%>
<%@page import="net.awesomebox.fwl.models.WishListItem"%>
<%@page import="net.awesomebox.servletmanager.ServletHelper"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
WishListItem wishListItem = (WishListItem)request.getAttribute("wishListItem");

String name        = wishListItem.name;
String url         = wishListItem.url;
String imageURL    = wishListItem.imageURL;
String description = wishListItem.description;

String nameEscaped        = "";
String urlEscaped         = "";
String imageURLEscaped    = "";
String descriptionEscaped = "";

@SuppressWarnings("unchecked")
Map<String, String> formData = (Map<String, String>)request.getAttribute("formData");

if (formData != null)
{
	name        = formData.get("name");
	url         = formData.get("url");
	imageURL    = formData.get("imageURL");
	description = formData.get("description");
}

if (name        != null) nameEscaped        = ServletHelper.escapeHTML(name);
if (url         != null) urlEscaped         = ServletHelper.escapeHTML(url);
if (imageURL    != null) imageURLEscaped    = ServletHelper.escapeHTML(imageURL);
if (description != null) descriptionEscaped = ServletHelper.escapeHTML(description);
%>

<t:header>
  <jsp:attribute name="head">
  	<script type="text/javascript">
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

<h2>Add Item</h2>

<form name="updateItemForm" method="post" action="/item/update">
	<input type="hidden" name="wishListItemID" value="<%=wishListItem.id%>"/>
	
 	<table>
 		<tr>
			<td><label for="name">Name:</label></td>
			<td><input type="text" name="name" value="<%=nameEscaped%>" required="required" /></td>
		</tr>
		<tr>
			<td><label for="url">URL:</label></td>
			<td><input type="text" name="url" value="<%=urlEscaped%>" /></td>
		</tr>
		<tr>
			<td><label for="imageURL">Image URL:</label></td>
			<td><input type="text" name="imageURL" value="<%=imageURLEscaped%>" /></td>
		</tr>
		<tr>
			<td><label for="description">Description:</label></td>
			<td><textarea name="description"><%=descriptionEscaped%></textarea></td>
		</tr>
	</table>
	
	<input type="submit" value="Update Item" /><br />
	<span id="formErrorMessage">${formErrorMessage}</span>
</form>
<br />
<form name="updateItemForm" method="post" action="/item/delete" onsubmit="return submitDeleteItem(event);">
	<input type="hidden" name="wishListItemID" value="<%=wishListItem.id%>"/>
	<input type="submit" value="Delete Item" />
</form>
<t:footer />