<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registrazione</title>
</head>
<body>

<h1>Registrazione Nuovo Utente</h1>
<form action="registrationServlet" method="POST">
			<input type="text" name="userID"> Username <br /> 
			<input type="email" name="email"> E-mail <br /> 
			<input type="password" name="password"> Password <br /> 
			
			<br /> <input type="submit" value="Registrati">
		</form>

</body>
</html>