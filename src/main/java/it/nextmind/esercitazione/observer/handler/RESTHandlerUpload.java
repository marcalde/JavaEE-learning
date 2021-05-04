package it.nextmind.esercitazione.observer.handler;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Observable;
import java.util.Observer;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.tomcat.jakartaee.commons.io.IOUtils;

import it.nextmind.esercitazione.observer.LogDownloadOption;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;

public class RESTHandlerUpload extends ResponseHandler implements Observer {

	private static final RESTHandlerUpload restHandlerUpload = new RESTHandlerUpload();
	private RESTHandlerUpload() {}

	public static RESTHandlerUpload getHandler() {
		return RESTHandlerUpload.restHandlerUpload;
	}

	@Override
	public void sendOutput(ServletContext context, String filePath, HttpServletResponse response) {

		
		
		
		
		

		try(CloseableHttpClient httpClient = HttpClients.createDefault()){

			File file = new File(filePath);
			Path p = Paths.get(filePath);
			String filename = p.getFileName().toString();
			byte[] fileContent = Files.readAllBytes(file.toPath());

			HttpPost httpPost = new HttpPost("http://localhost:8082/API/caricafile");
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.addBinaryBody( "multipartFile", Base64.getEncoder().encode(fileContent), ContentType.DEFAULT_BINARY, filename);

			HttpEntity multipart = builder.build();
			httpPost.setEntity(multipart);


			try(CloseableHttpResponse httpResponse = httpClient.execute(httpPost)){
				PrintWriter out = response.getWriter();

				HttpEntity responseEntity = httpResponse.getEntity();

				out.print(EntityUtils.toString(responseEntity));

				EntityUtils.consume(responseEntity);
				
			}

		} catch (Exception e) {

		}


	}

	@Override
	public void update(Observable o, Object arg) {
		String filePath = ((LogDownloadOption.Info)arg).getFilePath();	// ((OpzioneLog) o).getInfo().getFilePath();
		ServletContext context = ((LogDownloadOption.Info)arg).getContext();	// ((OpzioneLog) o).getInfo().getContext();
		HttpServletResponse response = ((LogDownloadOption.Info)arg).getResponse();	// ((OpzioneLog) o).getInfo().getResponse();

		if(((LogDownloadOption) o).getInfo().getOption().equals("REST-Upload")) {
			sendOutput(context, filePath, response);

		}
	}

}
