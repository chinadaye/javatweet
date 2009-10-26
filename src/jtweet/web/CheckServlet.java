package jtweet.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.Exception.NeedParamterException;

import org.json.simple.JSONObject;

public class CheckServlet extends JTweetServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2770684133440766156L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doCheck(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doCheck(req, resp);
	}

	private void doCheck(HttpServletRequest req, HttpServletResponse resp) {
		resp.setContentType("application/x-javascript; charset=UTF-8");
		JSONObject json = new JSONObject();

		String type = req.getParameter("type");
		String sinceId = req.getParameter("since");
		try {
			if (null == type||null==sinceId) {
				throw new NeedParamterException();
			}else if(type.equalsIgnoreCase("home")){
				
			}
		} catch (NeedParamterException e) {
			this.log.warning(e.getMessage());
			e.printStackTrace();
		}
	}
}
