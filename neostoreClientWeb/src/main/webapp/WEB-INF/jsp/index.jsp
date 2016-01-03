<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Petstore REST Client Application</title>
</head>
<body>

<h2>Petstore REST Client Application</h2>
<hr>


${msg}

	<form method="post" action ="login">
	<table border="0" cellpadding="8" cellspacing="0">
		<tr>
			<td>Login</td>
			<td><input type="text" name="username"></td>
			<td>
				<input type="text" name="password">
			</td>

		<tr>
			<td>register</td>
			<td><input type="checkbox" name="register"></td>
		</tr>

		<tr>
			<td><input type="submit" name="login_submit" value="Submit"></td>
			<td></td>
		</tr>
    </table>

</form>

${error}

<hr>



${checkedname}

<form method="get" action="checkname?name=newname">
    <table border="0" cellpadding="8" cellspacing="0">
		<tr>
			<td>Check name</td>
			<td>
				<input type="text" name="newname">
			</td>
		</tr>
		<tr>
			<td><input type="submit" name="checkname_submit" value="Submit">
		</tr>
	</table>
</form>
<hr>
<p>При разработке программы использован следующий стек технологий</p>
<ul>
	<li>Java8 Core</li>
	<li>Spring</li>
	<li>Spring Security</li>
	<li>Servlet/JSP</li>
	<li>Maven</li>
	<li>Tomcat</li>
	<li>JSon</li>
	<li>Basic Auth</li>
</ul>

</body>
</html>
