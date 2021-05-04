package it.nextmind.esercitazione.observer.handler;

import java.io.PrintWriter;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;

public abstract class ResponseHandler {
	
	protected String filePath;
	protected PrintWriter out;
	
	public String getFilePath() {
		return filePath;
	}
	
	public abstract void sendOutput(ServletContext context, String filePath, HttpServletResponse response);

}
