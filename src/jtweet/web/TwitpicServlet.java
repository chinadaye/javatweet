package jtweet.web;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.Exception.NotLoginException;


import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

import freemarker.template.Template;

@SuppressWarnings("serial")
public class TwitpicServlet extends JTweetServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		String uri = req.getRequestURI();

		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		try {
			this.revertAccount(req);
			root.put("user", this.getCachedUser());
			root.put("uri", uri);
			Template t = config.getTemplate("twitpic.ftl");
			t.process(root, resp.getWriter());
		} catch (NotLoginException e) {
			this.redirectLogin(req, resp);
		} catch (Exception e) {
			JTweetServlet.logger.warning(e.getMessage());
			this.showException(req, resp, e);
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		String uri = req.getRequestURI();
		try {
			this.revertAccount(req);
			String msg = null;
			String imgurl = null;

			URLFetchService urlFetch = URLFetchServiceFactory
					.getURLFetchService();
			String url;
			if (uri.equalsIgnoreCase("/twitgoo")) {
				url = "http://twitgoo.com/api/uploadAndPost";
			} else {
				url = "http://twitpic.com/api/uploadAndPost";
			}
			HTTPRequest httpreq = new HTTPRequest(new URL(url), HTTPMethod.POST);
			httpreq.addHeader(new HTTPHeader("Connection", "Keep-Alive"));
			String contenttype = req.getContentType();
			httpreq.addHeader(new HTTPHeader("Content-Type", contenttype));

			String boundary = contenttype.substring(contenttype
					.lastIndexOf("boundary=") + 9);
			String auth = "--"
					+ boundary
					+ "\r\nContent-Disposition: form-data; name=\"username\"\r\n\r\n"
					+ getUsername()
					+ "\r\n"
					+ "--"
					+ boundary
					+ "\r\nContent-Disposition: form-data; name=\"password\"\r\n\r\n"
					+ getPasswd() + "\r\n";
			byte[] buf = new byte[req.getContentLength()];
			req.getInputStream().read(buf);

			byte[] authbyte = auth.getBytes();
			byte[] post = new byte[buf.length + authbyte.length];

			System.arraycopy(authbyte, 0, post, 0, authbyte.length);
			System.arraycopy(buf, 0, post, authbyte.length, buf.length);

			// System.out.write(post);
			httpreq.addHeader(new HTTPHeader("Content-Length", String
					.valueOf(post.length)));
			httpreq.setPayload(post);
			HTTPResponse httpresp = urlFetch.fetch(httpreq);

			if (httpresp.getResponseCode() == 200) {
				Twitpic twitpic = new Twitpic(httpresp.getContent());
				if (twitpic.isok()) {
					msg = "Your Pic have beed Tweeted Successfully!";
					if (uri.equalsIgnoreCase("/twitgoo")) {
						imgurl = "http://twitgoo.com/" + twitpic.getMediaid()
								+ "/thumb";
					} else {
						imgurl = "/picthumb?id=" + twitpic.getMediaid();
					}
				} else {
					msg = "Failed, Error Message：" + twitpic.getErrmsg();
				}
			} else {
				msg = "Failed, Error Code：" + httpresp.getResponseCode() + "。";
			}

			HashMap<String, Object> root = new HashMap<String, Object>();
			freemarker.template.Configuration config = new freemarker.template.Configuration();
			config.setDirectoryForTemplateLoading(new File("template"));
			config.setDefaultEncoding("UTF-8");

			root.put("user", this.getCachedUser());
			root.put("msg", msg);
			root.put("uri", uri);
			if (imgurl != null)
				root.put("imgurl", imgurl);
			Template t = config.getTemplate("twitpic.ftl");
			t.process(root, resp.getWriter());

		} catch (NotLoginException e1) {
			this.redirectLogin(req, resp);
		} catch (Exception e) {
			this.showException(req, resp, e);
		}
	}
}
