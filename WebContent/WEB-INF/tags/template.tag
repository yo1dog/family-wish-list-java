<%@tag description="Main teamplate." pageEncoding="UTF-8"%>
<%
String loggedInUser = (String)request.getAttribute("loggedInUser");
%>

<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  </head>
  <body>
    <header>
      <%
      if (loggedInUser == null)
      {
        %>
        <a href="#">Login</a>
        <%
      }
      else
      {
        %>
        Welcome <strong>${loggedInUser}</strong> - <a href="#">Logout</a>
        <%
      }
      %>
    </header>
    <div id="body">
      <jsp:doBody/>
    </div>
    <footer>
      links - links - links
    </footer>
  </body>
</html>