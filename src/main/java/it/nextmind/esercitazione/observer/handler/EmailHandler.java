package it.nextmind.esercitazione.observer.handler;

import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import it.nextmind.esercitazione.observer.LogDownloadOption;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;

public class EmailHandler extends ResponseHandler implements Observer {
	
	private static final EmailHandler emailHandler = new EmailHandler();
	private EmailHandler() {}
	
	public static EmailHandler getHandler() {
		return EmailHandler.emailHandler;
	}

	@Override
	public void sendOutput(ServletContext context, String filePath, HttpServletResponse response) {

		try {

			String ciao;
			this.out = response.getWriter();

			Properties  prop = new Properties();
			prop.put("mail.smtp.auth", true);
		//	prop.put("mail.smtp.starttls.enable", "true");
			prop.put("mail.smtp.host", "smtp.mailtrap.io");
			prop.put("mail.smtp.port", "2525");
		//	prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");

			String user = "fbde9a6f5821ea";
			String pass = "f4c3d3f1309e12";


			Session session = Session.getInstance(prop,new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication()
				{
					return new PasswordAuthentication(user,pass); 
				}
			});

			Message mess = new MimeMessage(session);
			mess.setFrom(new InternetAddress("prova@prova.com"));
			mess.addRecipient(Message.RecipientType.TO, new InternetAddress("alderighimarco@gmail.com"));
			mess.setSubject("Log Prova");

			String msg = "In allegato trovi il Log";

			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setText(msg);

			MimeBodyPart attachBodyPart = new MimeBodyPart();
			attachBodyPart.attachFile(new File(filePath));
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			multipart.addBodyPart(attachBodyPart);

			mess.setContent(multipart);

			Transport.send(mess);

			out.write("E-mail inviata!");
			out.close();
		}
		catch (MessagingException | IOException e) {
			out.print("<h1>Invio e-mail non riuscito</h1>");
			out.print("<form action='/Login/private/profilo.jsp'><input type='submit' value='Torna indietro' /></form>");
					
			e.printStackTrace(out);
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		String filePath = ((LogDownloadOption.Info)arg).getFilePath();	// ((OpzioneLog) o).getInfo().getFilePath();
		ServletContext context = ((LogDownloadOption.Info)arg).getContext();	// ((OpzioneLog) o).getInfo().getContext();
		HttpServletResponse response = ((LogDownloadOption.Info)arg).getResponse();	// ((OpzioneLog) o).getInfo().getResponse();
		
		if(((LogDownloadOption) o).getInfo().getOption().equals("email")) {
		//	EmailHandler eh = new EmailHandler();
			sendOutput(context, filePath, response);
		}
	}
}