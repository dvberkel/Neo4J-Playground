package net.luminis.research.collatz.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.luminis.research.collatz.web.beans.CollatzPathBean;
import net.sf.json.JSONObject;

public class CollatzServlet extends HttpServlet {
	private static final long serialVersionUID = 37L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CollatzPathBean bean = new CollatzPathBean();
		bean.setPath(Arrays.asList(new Integer[] { 4, 2, 1 }));
		JSONObject jsonObject = JSONObject.fromBean(bean);

		PrintWriter out = response.getWriter();
		response.addHeader("Content-type", "application/json");
		out.println(jsonObject.toString());
		out.flush();
		out.close();
	}

}
