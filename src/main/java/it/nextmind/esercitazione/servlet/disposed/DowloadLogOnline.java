package it.nextmind.esercitazione.servlet.disposed;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.tomcat.jakartaee.commons.io.IOUtils;

public class DowloadLogOnline extends HttpServlet{


	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServletContext context = getServletContext();
		String filePath = "/private/res/MARCO_LOG.log";
		try(InputStream in = context.getResourceAsStream(filePath);
				OutputStream out = response.getOutputStream();){

			response.setContentType(context.getMimeType(filePath));
			response.setHeader("Content-Disposition", "attachment; filename=LogOnline.log");

			IOUtils.copy(in, out);
		}
		catch(Exception e) {}

	}
}







