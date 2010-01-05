package jtweet.web;

import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import jtweet.Exception.NotLoginException;


import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;


@SuppressWarnings("serial")
public class TwicliServlet extends JTweetServlet {


	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("application/x-javascript; charset=UTF-8");
		String uri = req.getRequestURI();
		JSONObject json = new JSONObject();
		String msg = null;
		String imgurl = null;
		try {
			this.revertAccount(req);
			

			URLFetchService urlFetch = URLFetchServiceFactory
					.getURLFetchService();
			String url;
			url = "http://twic.li/api/uploadPhoto";
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

			httpreq.addHeader(new HTTPHeader("Content-Length", String
					.valueOf(post.length)));
			httpreq.setPayload(post);
			HTTPResponse httpresp = urlFetch.fetch(httpreq);

			if (httpresp.getResponseCode() == 200) {
				Twicli twitpic = new Twicli(httpresp.getContent());
				if (twitpic.isok()) {
					msg = "Your Pic have beed Tweeted Successfully!";
					imgurl  = "http://twic.li/"+twitpic.getMediaid();
				} else {
					msg = "Failed, Error Message：" + twitpic.getErrmsg();
				}
			} else {
				msg = "Failed, Error Code：" + httpresp.getResponseCode() + "。";
			}
			

			
			json.put("imgurl", imgurl);
			json.put("uri", uri);
			

		} catch (NotLoginException e1) {
			msg= "未登录";
		} catch (Exception e) {
			JTweetServlet.logger.warning(e.getMessage());
			this.showException(req, resp, e);
		}
		json.put("msg", msg);
		resp.getWriter().print(json.toJSONString());
		return;
	}
}
