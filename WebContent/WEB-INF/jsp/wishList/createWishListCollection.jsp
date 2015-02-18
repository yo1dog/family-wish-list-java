<%@page import="net.awesomebox.servletmanager.ServletHelper"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
String collectionName = (String)request.getAttribute("collectionName");
String collectionNameEscaped = collectionName != null? ServletHelper.escapeHTML(collectionName) : "";
%>

<t:header />
<h2>Create Collection</h2>

<form name="createCollectionForm" method="post" action="/collection/create">
 	<table>
 			<tr>
			<td><label for="collectionName">Name:</label></td>
			<td><input type="text" name="collectionName" value="<%=collectionNameEscaped%>" required="required" /></td>
		</tr>
	</table>
	
	<input type="submit" value="Create" /><br />
	<span id="formErrorMessage">${formErrorMessage}</span>
</form>
<t:footer />