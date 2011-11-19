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
	private ServletUnitClient client;

	@Before
	public void setupServletRunnerAndClient() {
		ServletRunner sr = new ServletRunner();
		sr.registerServlet("collatz", CollatzServlet.class.getName());
		client = sr.newClient();
	}

	@Test
	public void servletUnitClientShouldBePresent() {
		assertNotNull(client);
	}

	@Test
	public void CollatzServletShouldReturnJson() throws MalformedURLException, IOException, SAXException {
		WebRequest request = new GetMethodWebRequest("http://localhost/collatz?pathOf=4");

		WebResponse response = client.getResponse(request);

		assertEquals("application/json", response.getContentType());
	}

	@Test
	public void CollatzServletShouldReturnJsonRepresentationOfAPath4() throws MalformedURLException, IOException,
		SAXException {
		WebRequest request = new GetMethodWebRequest("http://localhost/collatz?pathOf=4");

		JSONObject jsonObject = retrieveResponseFrom(request);

		int[] expected = new int[] { 4, 2, 1 };
		checkPath(jsonObject, expected);
	}

	@Test
	public void CollatzServletShouldReturnJsonRepresentationOfAPath5() throws MalformedURLException, IOException,
		SAXException {
		WebRequest request = new GetMethodWebRequest("http://localhost/collatz?pathOf=5");

		JSONObject jsonObject = retrieveResponseFrom(request);

		int[] expected = new int[] { 5, 16, 8, 4, 2, 1 };
		checkPath(jsonObject, expected);
	}

	@Test
	public void CollatzServletShouldReturnJsonRepresentationOfAPathWithQuery() throws MalformedURLException,
		IOException, SAXException {
		WebRequest request = new GetMethodWebRequest("http://localhost/collatz");

		JSONObject jsonObject = retrieveResponseFrom(request);

		int[] expected = new int[] { 1 };
		checkPath(jsonObject, expected);
	}

	private JSONObject retrieveResponseFrom(WebRequest request) throws IOException, SAXException {
		WebResponse response = client.getResponse(request);
		return JSONObject.fromString(response.getText());
	}

	private void checkPath(JSONObject jsonObject, int[] expected) {
		JSONArray jsonArray = jsonObject.getJSONArray("path");
		for (int index = 0; index < expected.length; index++) {
			assertEquals(Integer.valueOf(expected[index]), jsonArray.get(index));
		}
	}

}
