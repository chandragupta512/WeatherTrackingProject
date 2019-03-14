package com.ibm.bpwt.core.servlets;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.bpwt.core.processor.ProcessWeatherInfo;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Iterator;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet that writes some sample content into the response. It is mounted for
 * all resources of a specific Sling resource type. The
 * {@link SlingSafeMethodsServlet} shall be used for HTTP methods that are
 * idempotent. For write operations use the {@link SlingAllMethodsServlet}.
 */
@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Simple Demo Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/searchWeatherInfo"

})
public class SearchWeatherReportServlet extends SlingSafeMethodsServlet {
	private static final Logger LOG = LoggerFactory.getLogger(SearchWeatherReportServlet.class);
	 /** Serialization ID for Servlet */
    private static final long serialVersionUID = 2L;
    /**
     * Location reference Injected through Constructor, from the corresponding JUnit Test Class.
     */
     private transient String testLocationParam;
     
   public SearchWeatherReportServlet(){
    	
    	
    }
    public SearchWeatherReportServlet(String loc){
    	 this.testLocationParam = loc;
    	
    }
	@Override
	public void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {
		String city=null, days=null;
		resp.setContentType("application/json");
		city = req.getParameter("city");
		days = req.getParameter("days");
		if (null == city) {
			city = this.testLocationParam;
		} else {
			city = city.trim();
		}
		ProcessWeatherInfo weatherProcessor = new ProcessWeatherInfo();
		resp.getWriter().println(weatherProcessor.getWeatherData(city,days).toString());

	}

}