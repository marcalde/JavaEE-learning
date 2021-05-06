<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<form action="servletricerca" method="GET">

		<table>
			<tr>
				<td><label>Nome</label></td>
				<td><input type="text" name="name" value="<% %>" /></td>
			</tr>
			<tr>
				<td><label>Cognome</label></td>
				<td><input type="text" name="surname" /></td>
			</tr>
			<tr>
				<td><label>E-mail</label></td>
				<td><input type="email" name="email" /></td>
			</tr>
			<tr>
				<td><label>Età</label></td>
				<td><input type="number" name="age" /></td>
			</tr>
		</table>

</body>
</html>