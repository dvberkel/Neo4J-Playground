package net.luminis.research.collatz.web;

import java.io.IOException;
import java.net.MalformedURLException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CollatzServletTest {

	private ServletRunner sr;

	@Before
	public void setupServletRunner() {
		sr = new ServletRunner();
		sr.registerServlet("collatz", CollatzServlet.class.getName());
	}

	@Test
	public void servletRunnerShouldBePresent() {
		assertNotNull(sr);
	}

	@Test
	public void CollatzServletShouldReturnJson() throws MalformedURLException, IOException, SAXException {
		ServletUnitClient client = sr.newClient();
		WebRequest request = new GetMethodWebRequest("http://localhost/collatz?pathOf=4");

		WebResponse response = sr.getResponse(request);

		assertEquals("application/json", response.getContentType());
	}

	@Test
	public void CollatzServletShouldReturnJsonRepresentationOfAPath() throws MalformedURLException, IOException,
		SAXException {
		ServletUnitClient client = sr.newClient();
		WebRequest request = new GetMethodWebRequest("http://localhost/collatz?pathOf=4");

		WebResponse response = sr.getResponse(request);
		JSONObject jsonObject = JSONObject.fromString(response.getText());
		JSONArray jsonArray = jsonObject.getJSONArray("path");

		assertEquals(Integer.valueOf(4), jsonArray.get(0));
		assertEquals(Integer.valueOf(2), jsonArray.get(1));
		assertEquals(Integer.valueOf(1), jsonArray.get(2));
	}
}
