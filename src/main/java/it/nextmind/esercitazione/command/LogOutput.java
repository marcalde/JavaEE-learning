package it.nextmind.esercitazione.command;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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

import org.apache.tomcat.jakartaee.commons.io.IOUtils;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;

public class LogOutput {

	 String filePath;
	 ServletContext context;
	 HttpServletResponse response;
	
	public LogOutput (ServletContext context, String filePath, HttpServletResponse response) {
		this.context = context;
		this.filePath = filePath;
		this.response = response;
	}
	
	void download(){
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
	
	void view(){
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
	
	void email() {
		try {

			PrintWriter out = response.getWriter();

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
			/*
			out.print("<h1>Invio e-mail non riuscito</h1>");
			out.print("<form action='/Login/private/profilo.jsp'><input type='submit' value='Torna indietro' /></form>");
					
			e.printStackTrace(out);
			*/
		}
	}
	
}
