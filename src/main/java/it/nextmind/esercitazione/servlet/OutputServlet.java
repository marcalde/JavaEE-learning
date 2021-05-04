package it.nextmind.esercitazione.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

import it.nextmind.esercitazione.command.DownloadCommand;
import it.nextmind.esercitazione.command.EmailCommand;
import it.nextmind.esercitazione.command.LogOutput;
import it.nextmind.esercitazione.command.LogOutputInvoker;
import it.nextmind.esercitazione.command.ViewCommand;
import it.nextmind.esercitazione.observer.LogDownloadOption;
import it.nextmind.esercitazione.observer.handler.DownloadHandler;
import it.nextmind.esercitazione.observer.handler.EmailHandler;
import it.nextmind.esercitazione.observer.handler.RESTHandler;
import it.nextmind.esercitazione.observer.handler.RESTHandlerUpload;
import it.nextmind.esercitazione.observer.handler.ViewHandler;

public class OutputServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

//	OpzioneLog obsOption;
	LogDownloadOption obsOption = new LogDownloadOption();
//	DownloadHandler down = new DownloadHandler();
//	ViewHandler view = new ViewHandler();
//	EmailHandler email = new EmailHandler();
	
	
	@Override
	public void init() throws ServletException {
		//	obsOption = OpzioneLog.getOpzione();
			obsOption.registra(ViewHandler.getHandler(), "visualizza");
			obsOption.registra(EmailHandler.getHandler(), "email");
			obsOption.registra(DownloadHandler.getHandler(), "download");
			obsOption.registra(RESTHandler.getHandler(), "REST");
			obsOption.registra(RESTHandlerUpload.getHandler(), "REST-Upload");
			
	/*		obsOption.addObserver(ViewHandler.getHandler());
			obsOption.addObserver(EmailHandler.getHandler());
			obsOption.addObserver(DownloadHandler.getHandler());	*/
		}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ServletContext context = getServletContext();
		String filePath = "C:/Users/linda/Desktop/MARCO_WARN.log";
		String option = request.getParameter("option");
		
		/*
		// PATTERN: COMMAND
		LogOutput lg = new LogOutput(context, filePath, response);
		LogOutputInvoker li = new LogOutputInvoker(new DownloadCommand(lg), new EmailCommand(lg), new ViewCommand(lg));
		if(option.equals("visualizza")) li.view();
		else if(option.equals("download")) li.download();
		else li.email();
		*/
		
		// PATTERN: OBSERVER		
		obsOption.setOption(option, context, filePath, response);
		
		/*
		for(String x : OpzioneLog.getIscritti()) {
			System.out.println(x);
		}
		*/

		
	/*	if(option != null) {
			if(option.equals("download")) {
				DownloadHandler dh = new DownloadHandler();
				dh.sendOutput(context, filePath, response);
			}
			else if(option.equals("email")) {
				EmailHandler eh = new EmailHandler();
				eh.sendOutput(context, filePath, response);
			}
			else if(option.equals("visualizza")) {
				ViewHandler vh = new ViewHandler();
				vh.sendOutput(context, filePath, response);
			}	
		} 
		else {
			PrintWriter out = response.getWriter();
			RequestDispatcher rd = request.getRequestDispatcher("/private/profilo.jsp");
			out.print("<p style='color:red'>Seleziona un'opzione!</p>");
			rd.include(request, response);
		}
*/

	}

}
