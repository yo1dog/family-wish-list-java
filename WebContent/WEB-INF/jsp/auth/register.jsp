<%@page import="java.util.Map"%>
<%@page import="net.awesomebox.servletmanager.ServletHelper"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
String firstName;
String lastName;
String email;

String firstNameEscaped = "";
String lastNameEscaped  = "";
String emailEscaped     = "";

@SuppressWarnings("unchecked")
Map<String, String> formData = (Map<String, String>)request.getAttribute("formData");

if (formData != null)
{
	firstName = formData.get("firstName");
	lastName  = formData.get("lastName");
	email     = formData.get("email");
	
	if (firstName != null) firstNameEscaped = ServletHelper.escapeHTML(firstName);
	if (lastName  != null) lastNameEscaped  = ServletHelper.escapeHTML(lastName);
	if (email     != null) emailEscaped     = ServletHelper.escapeHTML(email);
}
%>

<t:header />
<h2>Register</h2>

<form name="registerForm" method="post" action="/register">
 	<table>
 		<tr>
			<td><label for="firstName">First Name:</label></td>
			<td><input type="text" name="firstName" value="<%=firstNameEscaped%>" required="required" /></td>
		</tr>
		<tr>
			<td><label for="lastName">Last Name:</label></td>
			<td><input type="text" name="lastName" value="<%=lastNameEscaped%>" /></td>
		</tr>
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
	
	<input type="submit" value="Register" /><br />
	<span id="formErrorMessage">${formErrorMessage}</span>
</form>
<t:footer />