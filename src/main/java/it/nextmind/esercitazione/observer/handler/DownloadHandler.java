package it.nextmind.esercitazione.observer.handler;

import java.io.BufferedInputStream;
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

public class DownloadHandler extends ResponseHandler implements Observer {

	private static final DownloadHandler downloadHandler = new DownloadHandler();
	private DownloadHandler() {}
	
	public static DownloadHandler getHandler() {
		return DownloadHandler.downloadHandler;
	}

	@Override
	public void sendOutput(ServletContext context, String filePath, HttpServletResponse response) {
		
		this.filePath = filePath;
		
		try(BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(filePath)));
				OutputStream out = response.getOutputStream();){
			response.setContentType(context.getMimeType(filePath));
			response.setHeader("Content-Disposition", "attachment; filename=LogOnline.log");

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

		if(((LogDownloadOption) o).getInfo().getOption().equals("download")) {
		//	DownloadHandler dh = new DownloadHandler();
			sendOutput(context, filePath, response);

		}
	}

}
