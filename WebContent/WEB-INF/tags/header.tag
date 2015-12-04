<%@tag import="net.awesomebox.fwl.models.User"%>
<%@tag import="net.awesomebox.servletmanager.ServletHelper"%>
<%@tag description="Header teamplate." pageEncoding="UTF-8" %>
<%@attribute name="head" fragment="true" %>
<%
User loggedInUser = (User)request.getAttribute("loggedInUser");
%>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="viewport" content="width=device-width" />
		<link rel="stylesheet" type="text/css" href="/css/style.css" />
		
		<jsp:invoke fragment="head" />
	</head>
	
	<body>
		<header>
			<h1 id="logo">FWL</h1>
			
			<%
			if (loggedInUser != null)
			{
				%>Welcome <strong><%=ServletHelper.escapeHTML(loggedInUser.firstName)%></strong> - <a href="/logout">Logout</a><%
			}
			%>
		</header>
		
		<nav>
			<ul>
				<%
				if (loggedInUser != null)
				{
					%>
					<li><a href="/home">Profile</a></li>
					<li><a href="/collections">Collections</a></li>
					<%
				}
				%>
				
			</ul>
		</nav>
		
		<main>