<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Welcome</title>
</head>
<body>
<h1>Benvenuto <%
	try{
	String ex = (String)(session.getAttribute("userID"));
	out.println(ex.substring(0,1).toUpperCase()+ex.substring(1));
	} catch (Exception e) {		
	}
	request.setAttribute("url", request.getRequestURL());
	session.setAttribute("indirizzo",(request.getRequestURL()));
	%></h1>
	<p>
	<a href="/Login/private/profile.jsp">Vai all'area privata</a>
	</p>
</body>
</html>