package it.nextmind.esercitazione.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegistrationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	String url;
	String username;
	String password;
	String insert;

	public void init() {
		url = "jdbc:mysql://localhost:3306/utenti";
		username = "test";
		password = "Test8888";
		insert = "INSERT INTO login (username, password, email) VALUES (?,?,?)";
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userID = request.getParameter("userID");
		String pass = request.getParameter("password");
		String email = request.getParameter("email");

		try (Connection conn = DriverManager.getConnection(url, username, password);
				PreparedStatement stmt = conn.prepareStatement(insert);
				Statement sel = conn.createStatement()) {
			System.out.println("Database connected! ----------");

			ResultSet rs = sel.executeQuery("SELECT * FROM login WHERE username='" + userID + "'");

			if (rs.next()) {
				RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
				PrintWriter out = response.getWriter();
				out.print("<p style='color:red'>Nome utente non disponibile</p>");
				rd.include(request, response);
				out.close();
			} else {
				stmt.setString(1, userID);
				stmt.setString(2, pass);
				stmt.setString(3, email);
				int n = stmt.executeUpdate();

				if (n == 1) {
					System.out.println("Inserito con successo!");
					PrintWriter out = response.getWriter();

					out.print("<h1>Utente registrato! </h1>");
					out.print("<p><a href='/Login'>Torna alla schermata di login</a></p>");
					out.close();
				}
			}
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect!", e);
		}

	}

}
