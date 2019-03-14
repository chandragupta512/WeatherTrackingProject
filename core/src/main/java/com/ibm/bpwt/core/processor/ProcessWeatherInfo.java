package com.ibm.bpwt.core.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONException;
import org.json.JSONObject;
import com.ibm.bpwt.core.utils.Constants;

public class ProcessWeatherInfo {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProcessWeatherInfo.class);
	
	 public JSONObject getWeatherData(String loc, String days) {
		String jsonData = "",url="";
		JSONObject obj = null;
		
			try {
				// create HTTP Client
				HttpClient httpClient = HttpClientBuilder.create().build();
			
				url=Constants.WT_URL+"?key="+Constants.WT_API_KEY+"&"+Constants.LOC+"="+loc+"&days="+days;
		
				// Create new getRequest with below mentioned URL
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

					jsonData += output;
					
				}

				obj = new JSONObject(jsonData);

			} catch (ClientProtocolException e) {
				LOG.error("ClientProtocolException occured in method getWeatherData()");

			} catch (IOException e) {
				LOG.error("IOException occured in method getWeatherData()");
			} catch (JSONException e) {
				LOG.error("JSONException occured in method getWeatherData()");
			}
			return obj;
	 
	 }

}
