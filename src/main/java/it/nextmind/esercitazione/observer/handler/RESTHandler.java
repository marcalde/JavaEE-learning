package it.nextmind.esercitazione.observer.handler;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Observable;
import java.util.Observer;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import it.nextmind.esercitazione.observer.LogDownloadOption;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;

public class RESTHandler extends ResponseHandler implements Observer {

	private static final RESTHandler restHandler = new RESTHandler();
	private RESTHandler() {}

	public static RESTHandler getHandler() {
		return RESTHandler.restHandler;
	}

	@Override
	public void sendOutput(ServletContext context, String filePath, HttpServletResponse response) {



		try(CloseableHttpClient httpClient = HttpClients.createDefault()){

			File file = new File(filePath);
			byte[] fileContent = Files.readAllBytes(file.toPath());
			String encoded = Base64.getEncoder().encodeToString(fileContent);

			HttpPost httpPost = new HttpPost("http://localhost:8081/API/provafile");
			httpPost.setEntity(new StringEntity(encoded));

			try(CloseableHttpResponse httpResponse = httpClient.execute(httpPost)){
				PrintWriter out = response.getWriter();
				out.print("CODICE RISPOSTA: "+httpResponse.getCode()+"\n");

				HttpEntity responseEntity = httpResponse.getEntity();

				if(responseEntity != null) {
					byte[] decoded = Base64.getDecoder().decode(EntityUtils.toString(responseEntity));
					out.print(new String(decoded, StandardCharsets.UTF_8));
				}

				EntityUtils.consume(responseEntity);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	@Override
	public void update(Observable o, Object arg) {
		String filePath = ((LogDownloadOption.Info)arg).getFilePath();	
		ServletContext context = ((LogDownloadOption.Info)arg).getContext();	
		HttpServletResponse response = ((LogDownloadOption.Info)arg).getResponse();

		if(((LogDownloadOption) o).getInfo().getOption().equals("REST")) {
			sendOutput(context, filePath, response);

		}
	}

}
