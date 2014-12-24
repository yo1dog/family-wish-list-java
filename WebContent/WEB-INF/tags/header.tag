<%@tag import="net.awesomebox.fwl.models.User"%>
<%@tag description="Header teamplate." pageEncoding="UTF-8" %>
<%@attribute name="head" fragment="true" %>
<%
User loggedInUser = (User)request.getAttribute("loggedInUser");
%>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:invoke fragment="head" />
	</head>
	
	<body>
		<header>
			<%
			if (loggedInUser == null)
			{
				%>
				<a href="/login">Login</a>
				<%
			}
			else
			{
				%>
				Welcome <strong><%=loggedInUser.firstName%></strong> - <a href="/logout">Logout</a>
				<%
			}
			%>
		</header>
		
		<hr />
		
		<main>