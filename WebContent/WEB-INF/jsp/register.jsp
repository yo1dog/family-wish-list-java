<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
String firstNameValue = "";
String lastNameValue  = "";
String emailValue     = "";

@SuppressWarnings("unchecked")
Map<String, String> forData = (Map<String, String>)request.getAttribute("formData");

if (forData != null)
{
	if (forData.get("firstName") != null) firstNameValue = "value=\"" + forData.get("firstName") + "\"";
	if (forData.get("lastName" ) != null) lastNameValue  = "value=\"" + forData.get("lastName" ) + "\"";
	if (forData.get("email"    ) != null) emailValue     = "value=\"" + forData.get("email"    ) + "\"";
}
%>

<t:header />
<form name="loginForm" method="post" action="/register">
 	<table>
 			<tr>
			<td><label for="firstName">First Name:</label></td>
			<td><input type="text" name="firstName" <%=firstNameValue%> required="required" /></td>
		</tr>
		<tr>
			<td><label for="lastName">Last Name:</label></td>
			<td><input type="text" name="lastName" <%=lastNameValue%> /></td>
		</tr>
		<tr>
			<td><label for="email">Email:</label></td>
			<td><input type="text" name="email" <%=emailValue%> required="required" /></td>
		</tr>
		<tr>
			<td><label for="password">Password:</label></td>
			<td><input type="password" name="password" required="required" /></td>
		</tr>
	</table>
	
	<input type="submit" value="Register" /><br />
	<span id="formErrorMessage">${formErrorMessage}</span>
</form>
<t:footer />