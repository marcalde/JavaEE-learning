package it.nextmind.esercitazione.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;

public class LogDownloadOption extends Observable {
	
	/*
	private static final OpzioneLog opzioneLog = new OpzioneLog();
	private OpzioneLog() {}
	
	public static OpzioneLog getOpzione() {
		return OpzioneLog.opzioneLog;
	}
	*/

	private Info info;
	private static List<String> iscritti = new ArrayList<String>();

	public class Info{
		private String option;
		private ServletContext context;
		private String filePath;
		private HttpServletResponse response;
		Info(String option, ServletContext context, String filePath, HttpServletResponse response){
			this.option = option;
			this.context = context;
			this.filePath = filePath;
			this.response = response;
		}
		public String getOption() {
			return option;
		}
		public ServletContext getContext() {
			return context;
		}
		public String getFilePath() {
			return filePath;
		}
		public HttpServletResponse getResponse() {
			return response;
		}		
	}

	public Info getInfo() {
		return this.info;
	}
	
	public void registra(Observer o, String name) {
		this.addObserver(o);
		iscritti.add(name);
	}
	
	public void rimuovi(Observer o, String name) {
		this.deleteObserver(o);
		iscritti.remove(iscritti.indexOf(name));
	}
	
	public static List<String> getIscritti(){
		return iscritti;
	}
	
	public void setOption(String option, ServletContext context, String filePath, HttpServletResponse response) {
		info = new Info(option, context, filePath, response);
		this.setChanged();
		this.notifyObservers(info);
		System.out.println();
	}
}
