package it.nextmind.esercitazione.servlet;

import java.io.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import jakarta.servlet.http.*;
import jakarta.servlet.*;

public class LoginAltServlet extends HttpServlet {

	private static final Logger logger = LogManager.getLogger("database");
	
	private static final long serialVersionUID = 1L;

	public void init() {
	}

	public void destroy() {
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userID = request.getParameter("userID");
		String password = request.getParameter("password");
		boolean logged = false;

		try {
			
			Context initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/utenti");
			Connection conn = ds.getConnection();
			logger.warn("Database connected");
			
			Statement stmt = conn.createStatement();
			


			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM login WHERE username ='" + userID + "' AND password ='" + password + "'");
			while (rs.next()) {
				logged = true;
			}

		} catch (SQLException | NamingException e) {
			logger.fatal(e.getMessage());
			throw new IllegalStateException("Cannot connect!", e);
		}



		if (logged) {
			HttpSession session = request.getSession();
			session.setAttribute("userID", userID);
			session.setAttribute("password", password);
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/private/benvenuto.jsp"));
		}

		else {
			PrintWriter out = response.getWriter();
			RequestDispatcher rd = request.getRequestDispatcher("login.html");
			out.print("<p style='color:red'>Credenziali sbagliate!</p>");
			rd.include(request, response);
			out.close();
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}