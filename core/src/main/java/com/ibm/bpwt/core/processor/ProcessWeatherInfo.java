package com.ibm.bpwt.core.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.bpwt.core.servlets.SearchWeatherReportServlet;

public class ProcessWeatherInfo {
	
	 public JSONObject getWeatherData(String loc, String days) {
		String inline = "",url="";
		JSONObject obj = null;
		//private static final Logger LOG = LoggerFactory.getLogger(SearchWeatherReportServlet.class);
			try {
				// create HTTP Client
				HttpClient httpClient = HttpClientBuilder.create().build();
				url="http://api.apixu.com/v1/forecast.json?key=184b8f12dbf04553aca125012191203&q="+loc+"&days="+days;
		
				// Create new getRequest with below mentioned URL
			//	Logger.info("Setting up the url in bpwt "+url);
				HttpGet getRequest= new HttpGet(url);
				
				getRequest.addHeader("Content-Type", "application/json;charset=UTF-8");
				HttpResponse response = httpClient.execute(getRequest);

				// Check for HTTP response code: 200 = success
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
				}

				// Get-Capture Complete application/xml body response
				BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
				String output;
				while ((output = br.readLine()) != null) {

					inline += output;
					//LOG.info("weather data "+inline);
				}

				obj = new JSONObject(inline);

			} catch (ClientProtocolException e) {
				e.printStackTrace();

			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return obj;
	 
	 }

}
