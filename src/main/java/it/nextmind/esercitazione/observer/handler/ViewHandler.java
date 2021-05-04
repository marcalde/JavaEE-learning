package it.nextmind.esercitazione.observer.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;

import org.apache.tomcat.jakartaee.commons.io.IOUtils;

import it.nextmind.esercitazione.observer.LogDownloadOption;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;

public class ViewHandler extends ResponseHandler implements Observer {
	
	private static ViewHandler viewHandler;
	private ViewHandler() {}
	
	public static ViewHandler getHandler() {
		if(viewHandler == null) {
			viewHandler = new ViewHandler();
		}
		return ViewHandler.viewHandler;
	}

	@Override
	public void sendOutput(ServletContext context, String filePath, HttpServletResponse response) {
		
		try(FileInputStream in = new FileInputStream(new File(filePath));
				OutputStream out = response.getOutputStream();){
			
			IOUtils.copy(in, out);	
		}
		catch(Exception e) {
			try {
				PrintWriter outW = response.getWriter();
				e.printStackTrace(outW);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		
		String filePath = ((LogDownloadOption.Info)arg).getFilePath();	// ((OpzioneLog) o).getInfo().getFilePath();
		ServletContext context = ((LogDownloadOption.Info)arg).getContext();	// ((OpzioneLog) o).getInfo().getContext();
		HttpServletResponse response = ((LogDownloadOption.Info)arg).getResponse();	// ((OpzioneLog) o).getInfo().getResponse();
		
		if(((LogDownloadOption) o).getInfo().getOption().equals("visualizza")) {
		//	ViewHandler vh = new ViewHandler();
			sendOutput(context, filePath, response);
		}
		
	}

}
