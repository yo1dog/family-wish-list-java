<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
String email = (String)request.getAttribute("email");
String emailValue = email == null? "" : "value=\"" + email + "\"";
%>

<t:header />
<form name="loginForm" method="post" action="/login">
 	<table>
		<tr>
			<td><label for="email">Email:</label></td>
			<td><input type="text" name="email" required="required" <%=emailValue%> /></td>
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