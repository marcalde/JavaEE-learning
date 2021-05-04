package it.nextmind.esercitazione.servlet.disposed;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.tomcat.jakartaee.commons.io.IOUtils;



public class DownloadImage extends HttpServlet{


	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String filePath = "C:/Users/linda/Desktop/Screenshot(2).png";
		File file = new File(filePath);

		try(FileInputStream in = new FileInputStream(file);
				OutputStream out = response.getOutputStream();){

			ServletContext context = getServletContext();
			String mimeType = context.getMimeType(filePath);

			response.setContentType(mimeType);
			response.setContentLength((int)file.length());
			response.setHeader("Content-Disposition", "attachment; filename=Screen.png");

			IOUtils.copy(in, out);
		}
		catch(Exception e) {}

	}

}
