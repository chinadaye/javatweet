package jtweet.web;

import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.gae.GCache;

import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

public class Untinyme extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6772863244694272622L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("application/x-javascript; charset=UTF-8");
		String shortUrl = req.getParameter("url");
		if (shortUrl != null) {
			String responContent = (String) GCache.get("untinyme_"
					+ shortUrl.hashCode());
			if (responContent == null) {
				String url = "http://untiny.me/api/1.0/extract?format=json&url="
						+ shortUrl;
				HTTPResponse respon = URLFetchServiceFactory
						.getURLFetchService().fetch(new URL(url));
				responContent = new String(respon.getContent());
				GCache.put("untinyme_"
					+ shortUrl.hashCode(), responContent);
			}
			resp.getWriter().write(responContent);
		}else{
			resp.getWriter().write("{}");
		}

	}
}
