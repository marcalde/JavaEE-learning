<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="it.nextmind.esercitazione.observer.LogDownloadOption"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Area Privata</title>
</head>
<body>
	<script>
		//alert("Potresti non avere accesso alla pagina");
	</script>
	<h1>
		Area personale di
		<%
	try {
		String ex = (String) (session.getAttribute("userID"));
		out.println(ex.substring(0, 1).toUpperCase() + ex.substring(1));
	} catch (Exception e) {
	}
	%>
	</h1>
	<h3>
		Funzioni di:
		<%
	if (session.getAttribute("password").equals("paolo"))
		out.println("Publisher");
	else if (session.getAttribute("password").equals("pippo"))
		out.println("Manager");
	%>
	</h3>
	<br />
	<%
	// indirizzo è l'url della pagina di provenienza
	//	out.print(session.getAttribute("indirizzo"));
	%>
	<div>
		<form action="outputservlet" method="GET">
			<%
			for (String iscritto : LogDownloadOption.getIscritti()) {
				out.print("<input type='radio' name='option' value='" + iscritto + "'");
				out.print(
				"><label for='option'>" + iscritto.substring(0, 1).toUpperCase() + iscritto.substring(1) + "</label><br>");
			}
			out.print("<p><input type='submit' value='Apri il log'></p></form>");
			%>
		</form>
	</div>
	<hr />
	<h1>Caricamento File</h1>
	<form action="http://localhost:8082/API/upload" method="POST"
		enctype="multipart/form-data">
		<table>
			<tr>
				<td><label>Scegli il file da caricare</label></td>
				<td><input type="file" name="file" /></td>
			</tr>
			<tr>
				<td><label>Scegli il nome del file</label></td>
				<td><input type="text" name="filename" /></td>
			</tr>
			<tr></tr>
			<tr>
				<td><input type="submit" value="Upload" /></td>
			</tr>
		</table>
	</form>
	<hr />
	<div style="display: flex; justify-content: space-around;">
	<div>
	<h1>Prenotazione</h1>
	<form action="http://localhost:8084/API/prenot" method="POST">
		<table>
			<tr>
				<td><label>Nome</label></td>
				<td><input type="text" name="name" /></td>
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

			<tr>
				<td><input type="submit" value="Prenota" /></td>
				<td><input type="reset" value="Pulisci" /></td>
			</tr>
		</table>
	</form>
	</div>
	<div style="display: flex;">
	<div>
	<h1>Ricerca prenotazione</h1>
	<form action="servletricerca" method="GET">
		<table>
			<tr>
				<td><input type="number" name="id"></td>
				<td><input type="submit" /></td>
			</tr>
		</table>

	</div>
	<div>
	<h1 style="visibility: hidden;">d</h1>
<!--  
		<table>
			<tr>
				<td><label>Nome</label></td>
				<td><input type="text" name="name" /></td>
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
		-->
	</form>
	</div>
	</div>
	</div>
	<hr />
	<p>
		<a href="downloadImg">Scarica l'immagine...</a>
	<form action="downloadlog" method="GET">
		<input type="submit" value="Scarica il log">
	</form>
	</p>
	<div style="width: 100%; text-align: center">
		<a href="downloadOnLog">Scarica l'altro log...</a>
	</div>

</body>
</html>