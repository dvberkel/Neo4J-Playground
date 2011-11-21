package net.luminis.research.collatz.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.luminis.research.collatz.domain.CollatzDomain;
import net.luminis.research.collatz.service.creation.CollatzCreationService;
import net.luminis.research.collatz.web.beans.CollatzPathBean;
import net.sf.json.JSONObject;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CollatzServlet extends HttpServlet {
	private static final long serialVersionUID = 37L;
	@Inject
	private CollatzCreationService service;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer start = determineStartFrom(request);
		List<Integer> path = determinePathOf(start);
		JSONObject jsonObject = createJsonObjectFrom(path);
		writeRespone(response, jsonObject);
	}

	private Integer determineStartFrom(HttpServletRequest request) {
		String pathOf = request.getParameter("pathOf");
		Integer start = null;
		try {
			start = Integer.valueOf(pathOf);
		} catch (NumberFormatException e) {
			start = 1;
		}
		return start;
	}

	private List<Integer> determinePathOf(Integer start) {
		CollatzDomain domain = service.createDomain(start, start);
		List<Integer> path = domain.pathOf(start);
		return path;
	}

	private JSONObject createJsonObjectFrom(List<Integer> path) {
		CollatzPathBean bean = new CollatzPathBean();
		bean.setPath(path);
		return JSONObject.fromBean(bean);
	}

	private void writeRespone(HttpServletResponse response, JSONObject jsonObject) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonObject.toString());
		out.flush();
		out.close();
	}

}
