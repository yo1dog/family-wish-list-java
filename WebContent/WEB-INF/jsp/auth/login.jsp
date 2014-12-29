<%@page import="net.awesomebox.servletmanager.ServletHelper"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
String email = (String)request.getAttribute("email");
String emailEscaped = email != null? ServletHelper.escapeHTML(email) : "";
%>

<t:header />
<h2>Login</h2>

<form name="loginForm" method="post" action="/login${callbackURLQueryString}">
 	<table>
		<tr>
			<td><label for="email">Email:</label></td>
			<td><input type="text" name="email" required="required" value="<%=emailEscaped%>" /></td>
		</tr>
		<tr>
			<td><label for="password">Password:</label></td>
			<td><input type="password" name="password" required="required" /></td>
		</tr>
	</table>
	
	<input type="submit" value="Login" /><br />
	<span id="formErrorMessage">${formErrorMessage}</span>
</form>
<t:footer />