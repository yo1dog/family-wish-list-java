<%@page import="net.awesomebox.servletmanager.ServletHelper"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
String email = (String)request.getAttribute("email");
String emailEscaped = email != null? ServletHelper.escapeHTML(email) : "";
%>

<t:header />

<p>
Code-named “Family Wish List” (FWL for short), this site aims to solve the problem of duplicate gifts and scattered wish list-related communication.
</p>

<h2>Login</h2>

<form name="loginForm" method="post" action="/login${callbackURLQueryString}">
 	<table>
		<tr>
			<td><label for="email">Email:</label></td>
			<td><input type="email" name="email" required="required" value="<%=emailEscaped%>" /></td>
		</tr>
		<tr>
			<td><label for="password">Password:</label></td>
			<td><input type="password" name="password" required="required" /></td>
		</tr>
	</table>
	
	<input type="submit" value="Login" /><br />
	<span id="formErrorMessage">${formErrorMessage}</span><br />
	<br />
	<a href="/register">Register</a>
</form>

<br />
<hr />
<br />

<p>
Every holiday or birthday we always have the same problems:
</p>

<ul>
	<li>It is difficult to manage and communicate who is getting what for whom.
	<li>Wish lists are scattered between emails, texts, etc.</li>
	<li>Notes/comments on gifts and gift ideas are scattered as well and get lost.</li>
	<li>It is difficult to keep track of what gifts you still need to buy.</li>
	<li>There is no good way to update your wish list after you send it.</li>
</ul>

<table>
	<tr><td>BJ:  </td><td>"Is anyone getting that book for Angie?"</td></tr>
	<tr><td>Mike:</td><td>"I thought Dad was getting it."</td></tr>
	<tr><td>Dad: </td><td>"No I was getting a book for Katie. I thought Mom was."</td></tr>
	<tr><td>Tom: </td><td>"I already got Angie a book. Which one are you talking about?."</td></tr>
	<tr><td>Mom: </td><td>"Angie said she didn't want that book anymore."</td></tr>
	<tr><td>BJ:  </td><td>"Does anyone have her wish list?"</td></tr>
	<tr><td>Dad: </td><td>"I think she sent it an email..."</td></tr>
	<tr><td>Mom: </td><td>"No I texted it to everyone. Katie's was in an email."</td></tr>
	<tr><td>Mike:</td><td>"Does anyone know what size shirts she wears?"</td></tr>
	<tr><td>Tom: </td><td>"Someone should get Mom some of those 'Good Kids' she keeps asking for; Do they sell them at Bed Bath and Beyond?"</td></tr>
	<tr><td>BJ:  </td><td>"I'm probably not going to get anyone anything anyway."</td></tr>
</table>

<p>
"Have I gotten anything for Angie yet?"<br />
"What did I say I was going to get for Katie?"<br />
"Where did I save her wish list?"<br />
"What size pants was Dad again? I guess I should call Mom."<br />
"Ugh. It is frustrating trying to do all this on my phone in the store."<br />
"If only there was some way to manage all this!"
</p>


<p>
FWL manages all this communication for you. When you view someone else's wish list you can mark the items you plan on getting and see which items others plan to get or have gotten. Don't worry, the owner of the wish list won't be able to see any of this so no surprises will be ruined.
In addition, all wish lists (including your own) will now be in one place making them easy to find, access, read, and edit.
You can make updates to your wish list and everyone will see your changes.
You can also add your gift ideas to other's with lists and add notes and comments to items on wish lists to help whomever will be getting that item (coming soon). Again, the owner of the wish list won't see a thing.
</p>
<p>
After marking some gifts that you plan to get, the home page displays your to-do list of gifts you need to get. After you get them you can mark them as gotten. This will remove it from your to-do list and let others know that you got that item.
</p>
<p>
Please note that this web site is still very early in development. So, yes, it's ugly. Please let me know of any bugs or suggestions by clicking the "Make Site Suggestion" link at the bottom of every page.
</p>

<t:footer />