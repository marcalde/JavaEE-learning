package it.nextmind.esercitazione.servlet.disposed;


import java.io.*;
import java.util.Enumeration;

import jakarta.servlet.http.*;
import jakarta.servlet.*;


public class ServletHeader extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public void init() {}

	public void destroy() {}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		String userID = request.getParameter("userID");
		String password = request.getParameter("password");

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
			
			 Enumeration en = request.getHeaderNames();
		     while (en.hasMoreElements()) {
		        String headerName = (String) en.nextElement();
		        String headerValue = request.getHeader(headerName);
		        out.println(headerName + ": " + headerValue+" <br/>");
		     }	
		     
		     out.print("<hr/>");
		     
		     Enumeration ne = session.getAttributeNames();
		     while (ne.hasMoreElements()) {
			        String attrName = (String) ne.nextElement();
			        String attrValue = (String) session.getAttribute(attrName);
			        out.println(attrName + ": " + attrValue+" <br/>");
			     }	
		     
		     out.print(this.getServletContext().getServerInfo());
		     
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost( request, response);
	}

}