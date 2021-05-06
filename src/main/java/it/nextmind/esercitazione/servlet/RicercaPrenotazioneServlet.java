package it.nextmind.esercitazione.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.JSONObject;



import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RicercaPrenotazioneServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		long id = Integer.parseInt(request.getParameter("id")) ;

		try(CloseableHttpClient httpClient = HttpClients.createDefault()){

			String url = "http://localhost:8084/API/prenot/"+id;

			HttpGet httpGet = new HttpGet(url);

			try(CloseableHttpResponse httpResponse = httpClient.execute(httpGet)){
				HttpEntity responseEntity = httpResponse.getEntity();
				try {
					String jsonString = EntityUtils.toString(responseEntity);
					PrintWriter out = response.getWriter();
					out.print(jsonString);
					out.print("\n");


					JSONObject jo = new JSONObject(jsonString);
					String name = jo.getString("name");
					String surname = jo.getString("surname");
					int age = jo.getInt("age");
					out.print(name+"\n");
					out.print(surname+"\n");
					out.print(age+"\n");
					out.close();
					
				} catch (ParseException | IOException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
}
