<%@page import="java.util.Map"%>
<%@page import="java.sql.Connection"%>
<%@page import="net.awesomebox.servletmanager.ServletHelper"%>
<%@page import="net.awesomebox.fwl.models.Suggestion"%>
<%@page import="net.awesomebox.fwl.models.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%
String  text;
String  referringPage;

String textEscaped = "";
String referringPageEscaped = "";

@SuppressWarnings("unchecked")
Map<String, String> formData = (Map<String, String>)request.getAttribute("formData");

if (formData != null)
{
	text          = formData.get("text");
	referringPage = formData.get("referringPage");
	
	if (text          != null) textEscaped          = ServletHelper.escapeHTML(text);
	if (referringPage != null) referringPageEscaped = ServletHelper.escapeHTML(referringPage);
}

Connection cn = (Connection)request.getAttribute("cn");
Suggestion[]       suggestions = (Suggestion[])request.getAttribute("suggestions");
Boolean hideForm = (Boolean)request.getAttribute("hideForm");

String formStyle = (hideForm == null || hideForm == false)? "" : "disaply: hidden;";
%>

<a href="javascript:window.close();">Close Window</a>

<h2>Suggestions</h2>
<form name="createSuggestionForm" method="post" action="/suggestion/create" style="<%=formStyle%>">
 	<textarea name="text" required="required"><%=textEscaped%></textarea><br />
	<input type="hidden" value="<%=referringPageEscaped%>" />
	<input type="submit" value="Submit" /><br />
	<span id="formErrorMessage">${formErrorMessage}</span>
</form>

<ul>
<%
for (int i = 0; i < suggestions.length; ++i)
{
	Suggestion suggestion = suggestions[i];
	
	User   author            = suggestion.getAuthor(cn);
	String authorNameEscaped = ServletHelper.escapeHTML(author.firstName);
	String suggestionHTML    = ServletHelper.newlinesToBR(ServletHelper.escapeHTML(suggestion.text));
	
	%><li><strong><%=authorNameEscaped%></strong>: <%=suggestionHTML%></li><%
}
%>
</ul>