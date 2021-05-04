package it.nextmind.esercitazione.filter;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class MaintenanceFilter implements Filter {
	
	private static final Logger logger = LogManager.getLogger();

	public MaintenanceFilter() {
		// TODO Auto-generated constructor stub
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest)request;
		HttpSession session = req.getSession();
		String id = (String)session.getAttribute("userID");

		logger.debug("Session attr: "+session.getAttribute("indirizzo"));
		System.out.println("Session attr: "+session.getAttribute("indirizzo"));
		
		try {
		if(id.equals("sauro")) {
			chain.doFilter(request, response);
		}
		else {
			PrintWriter out= ((HttpServletResponse)response).getWriter();  

			out.print("<br/><h2>Sezione non accessibile</h2>");  
			out.close();  
		}
		} catch (Exception e) {
			
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
