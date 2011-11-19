package net.luminis.research.collatz.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.luminis.research.collatz.calculator.impl.StandardCollatzCalculator;
import net.luminis.research.collatz.domain.CollatzDomain;
import net.luminis.research.collatz.domain.provider.impl.StandardCollatzDomainProvider;
import net.luminis.research.collatz.service.creation.CollatzCreationService;
import net.luminis.research.collatz.service.creation.impl.StandardCollatzCreationService;
import net.luminis.research.collatz.web.beans.CollatzPathBean;
import net.sf.json.JSONObject;

public class CollatzServlet extends HttpServlet {
	private static final long serialVersionUID = 37L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathOf = request.getParameter("pathOf");
		Integer start = null;
		try {
			start = Integer.valueOf(pathOf);
		} catch (NumberFormatException e) {
			start = 4;
		}

		CollatzCreationService service = new StandardCollatzCreationService(new StandardCollatzCalculator(),
			new StandardCollatzDomainProvider());
		CollatzDomain domain = service.createDomain(1, start);
		List<Integer> path = domain.pathOf(start);

		CollatzPathBean bean = new CollatzPathBean();
		bean.setPath(path);
		JSONObject jsonObject = JSONObject.fromBean(bean);

		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonObject.toString());
		out.flush();
		out.close();
	}

}
