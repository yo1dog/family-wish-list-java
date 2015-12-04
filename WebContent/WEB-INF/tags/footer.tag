<%@tag description="Footer teamplate." pageEncoding="UTF-8" %>
<%@tag import="java.net.URLEncoder"%>

<%
String referringPage = (String)request.getAttribute("javax.servlet.forward.request_uri");
referringPage = referringPage.replace(request.getContextPath(), "");

String queryString = request.getQueryString();
if (queryString != null)
	referringPage += "?" + queryString;
%>
			
			<hr style="margin: 20px 0px 20px 0px;" />
			<small><a href="/suggestion/create?referringPage=<%=URLEncoder.encode(referringPage, "UTF-8")%>" target="_blank">Make Site Suggestion</a></small>
			
		</main>
	</body>
</html>