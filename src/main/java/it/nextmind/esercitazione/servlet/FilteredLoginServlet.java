package it.nextmind.esercitazione.servlet;

import java.io.*;

import java.sql.*;
import jakarta.servlet.http.*;
import jakarta.servlet.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.LogManager;

public class FilteredLoginServlet extends HttpServlet {

	private static final Logger logger = LogManager.getLogger("debug");
	private static final Marker PROVA = MarkerManager.getMarker("PROVA");

	String url;
	String username;
	String pass;
	
	private static final long serialVersionUID = 1L;

	public void init() {
		url = "jdbc:mysql://localhost:3306/utenti";
		username = "test";
		pass = "Test8888";
	}

	public void destroy() {
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userID = request.getParameter("userID");
		String password = request.getParameter("password");
		String email = null;
		boolean logged = false;

		try (Connection conn = DriverManager.getConnection(url, username, pass);
				Statement stmt = conn.createStatement()) {
			// System.out.println("Database connected! ----------");
			logger.debug(PROVA, "Prova del marker");
			logger.fatal("Test senza marker");
			logger.warn(request.getParameter("password"));

			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM login WHERE username ='" + userID + "' AND password ='" + password + "'");
			while (rs.next()) {
				email = rs.getString("email");
				logged = true;
			}

		} catch (SQLException e) {
			logger.fatal(PROVA, e.getMessage());
			throw new IllegalStateException("Cannot connect!", e);
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		/*
		 * System.out.println("Remote user: "+request.getRemoteUser());
		 * System.out.println("Remote port: "+request.getRemotePort());
		 * System.out.println("Remote ip: "+request.getRemoteAddr() );
		 * System.out.println("Req url: "+request.getRequestURL() );
		 * 
		 * System.out.println("Servlet Req Path info: "+request.getPathInfo() );
		 * System.out.println("Servlet Req Servlet Path: "+request.getServletPath() );
		 * System.out.println("Servlet Req Context Path: "+request.getContextPath() );
		 * 
		 * Enumeration ne = request.getParameterNames(); while (ne.hasMoreElements()) {
		 * String attrName = (String) ne.nextElement(); String attrValue =
		 * request.getParameter(attrName); System.out.println(attrName + ": " +
		 * attrValue); }
		 * 
		 */
		if (logged) {
			HttpSession session = request.getSession();
			session.setAttribute("userID", userID);
			session.setAttribute("password", password);
			session.setAttribute("email", email);
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/private/benvenuto.jsp"));
		}

		/*
		 * if(password.equals("pippo") || password.equals("paolo")) {
		 * 
		 * HttpSession session = request.getSession(); session.setAttribute("userID",
		 * userID); session.setAttribute("password", password); //RequestDispatcher rd =
		 * this.getServletContext().getRequestDispatcher("/private/benvenuto.jsp");
		 * //rd.forward(request, response);
		 * 
		 * 
		 * response.sendRedirect(response.encodeRedirectURL(request.getContextPath()+
		 * "/private/benvenuto.jsp")); }
		 */
		else {
			RequestDispatcher rd = request.getRequestDispatcher("login.html");
			out.print("<p style='color:red'>Credenziali sbagliate!</p>");
			rd.include(request, response);

			// response.sendRedirect(request.getContextPath());
		}
		out.close();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}