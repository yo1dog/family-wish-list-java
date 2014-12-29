<%@page import="java.util.Map"%>
<%@page import="net.awesomebox.servletmanager.ServletHelper"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
Integer wishListID = (Integer)request.getAttribute("wishListID");

String name;
String url;
String imageURL;
String description;

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
	
	if (name        != null) nameEscaped        = ServletHelper.escapeHTML(name);
	if (url         != null) urlEscaped         = ServletHelper.escapeHTML(url);
	if (imageURL    != null) imageURLEscaped    = ServletHelper.escapeHTML(imageURL);
	if (description != null) descriptionEscaped = ServletHelper.escapeHTML(description);
}
%>

<t:header />
<h2>Add Item</h2>

<form name="createItemForm" method="post" action="/item/create?wishListID=<%=wishListID%>">
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
	
	<input type="submit" value="Add Item" /><br />
	<span id="formErrorMessage">${formErrorMessage}</span>
</form>
<t:footer />