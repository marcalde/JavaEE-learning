package it.nextmind.esercitazione.servlet.disposed;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;




public class DownloadLogServlet extends HttpServlet{


	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		String filePath = "C:/Users/linda/Desktop/MARCO_WARN.log";
		File file = new File(filePath);
		try(BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
				OutputStream out = response.getOutputStream();){

			ServletContext context = getServletContext();
			String mimeType = context.getMimeType(filePath);

			
			
			response.setContentType(mimeType);
			response.setContentLength((int)file.length());
			response.setHeader("Content-Disposition", "attachment; filename=Log.log");

			int bytesRead;

			while ((bytesRead = in.read()) > 0) {
				out.write(bytesRead);
			}
		}
		catch(Exception e) {	}
	}
}
