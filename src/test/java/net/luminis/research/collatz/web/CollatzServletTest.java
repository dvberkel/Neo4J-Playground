package net.luminis.research.collatz.web;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class CollatzServletTest {
	private ServletUnitClient client;
	private final String query;
	private final int[] expected;

	public CollatzServletTest(Integer start, int[] expected) {
		this.query = determineQuery(start);
		this.expected = expected;
	}

	private String determineQuery(Integer start) {
		if (start != null) {
			return String.format("?pathOf=%d", start);
		}
		return "";
	}

	@Before
	public void setupServletRunnerAndClient() throws IOException, SAXException {
		ServletRunner sr = new ServletRunner(new File("src/main/webapp/WEB-INF/web.xml"));
		client = sr.newClient();
	}

	@Test
	public void servletUnitClientShouldBePresent() {
		assertNotNull(client);
	}

	@Test
	public void CollatzServletShouldReturnJson() throws MalformedURLException, IOException, SAXException {
		WebRequest request = new GetMethodWebRequest("http://localhost/collatz" + query);

		WebResponse response = client.getResponse(request);

		assertEquals("application/json", response.getContentType());
	}

	@Test
	public void CollatzServletShouldReturnJsonRepresentationOfAPath4() throws MalformedURLException, IOException,
		SAXException {
		WebRequest request = new GetMethodWebRequest("http://localhost/collatz" + query);

		JSONObject jsonObject = retrieveResponseFrom(request);

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

	@Parameters
	public static Collection<Object[]> data() {
		List<Object[]> data = new ArrayList<Object[]>();
		data.add(new Object[] { Integer.valueOf(4), new int[] { 4, 2, 1 } });
		data.add(new Object[] { Integer.valueOf(5), new int[] { 5, 16, 8, 4, 2, 1 } });
		data.add(new Object[] { null, new int[] { 1 } });
		return data;
	}

}
