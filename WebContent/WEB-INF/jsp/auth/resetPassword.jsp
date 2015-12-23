<%@page import="java.util.Map"%>
<%@page import="net.awesomebox.servletmanager.ServletHelper"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
String email;
String emailEscaped = "";

@SuppressWarnings("unchecked")
Map<String, String> formData = (Map<String, String>)request.getAttribute("formData");

if (formData != null)
{
	email = formData.get("email");
	
	if (email!= null) emailEscaped = ServletHelper.escapeHTML(email);
}
%>

<t:header />
<h2>Reset Password</h2>

<form name="resetPasswordForm" method="post" action="/resetPassword">
 	<table>
		<tr>
			<td><label for="email">Email:</label></td>
			<td><input type="email" name="email" value="<%=emailEscaped%>" required="required" /></td>
		</tr>
		<tr>
			<td><label for="password">Password:</label></td>
			<td><input type="password" name="password" required="required" /></td>
		</tr>
		<tr>
			<td><label for="password">Password Again:</label></td>
			<td><input type="password" name="passwordConfirm" required="required" /></td>
		</tr>
	</table>
	
	<input type="submit" value="Reset Password" /><br />
	<span id="formErrorMessage">${formErrorMessage}</span>
</form>
<t:footer />