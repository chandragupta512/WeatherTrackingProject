package com.ibm.bpwt.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import com.ibm.bpwt.core.processor.ProcessWeatherInfo;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

/**
 * This Servlet is acts controler to take reuest for wether information and
 * responds with the expected json data to the caller
 */
@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Weather info servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/searchWeatherInfo"

})
public class SearchWeatherReportServlet extends SlingSafeMethodsServlet {

	/** Serialization ID for Servlet */
	private static final long serialVersionUID = 2L;

	/**
	 * Location reference Injected through Constructor, from the corresponding JUnit
	 * Test Class.
	 */
	private transient String testLocationParam;

	/** Default Constructors to be utilized for mockito test coverage */

	public SearchWeatherReportServlet() {

	}

	public SearchWeatherReportServlet(String loc) {
		this.testLocationParam = loc;

	}

	@Override
	public void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {
		String city = null;
		String days = null;
		resp.setContentType("application/json");
		city = req.getParameter("city");
		days = req.getParameter("days");
		if (null == city) {
			city = this.testLocationParam;
		} else {
			city = city.trim();
		}
		//API processor to process data and return in contracted format
		ProcessWeatherInfo weatherProcessor = new ProcessWeatherInfo();
		resp.getWriter().println(weatherProcessor.getWeatherData(city, days).toString());

	}

}