package jtweet;

import java.io.IOException;
import java.net.URL;
import javax.servlet.http.*;
import org.json.simple.*;
import com.google.appengine.api.urlfetch.*;


@SuppressWarnings("serial")
public class JtweetServlet extends HttpServlet {
	
	protected String twurl = "http://twitter.com";
	protected String twsearch = "http://search.twitter.com";
	//protected String twurl = "http://t.yulei666.com/api";
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String req_url = req.getRequestURI().substring(4);
		String query_string = req.getQueryString();
		String web_root = "http://" + req.getServerName();

		if(req_url.isEmpty() || req_url.equalsIgnoreCase("/"))
		{
			resp.sendRedirect("/api.jsp");
		}
		else if(TRequest.getRequestType(req_url) == TRequest.NOTSUPPORT)
			resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
		else
		{
			String twbase;
			if(TRequest.isSearch(req_url)) twbase = twsearch;
			else twbase = twurl;
			
			URLFetchService urlFetch = URLFetchServiceFactory.getURLFetchService();
			HTTPRequest httpreq = new HTTPRequest(query_string == null ? new URL(twbase + req_url) : new URL(twbase + req_url + "?" + query_string), HTTPMethod.GET);
			
			String UA = req.getHeader("User-Agent");
			if(UA != null) httpreq.addHeader(new HTTPHeader("User-Agent", UA));
			String Conn = req.getHeader("Connection");
			if(Conn != null)httpreq.addHeader(new HTTPHeader("Connection", Conn));
			String contenttype = req.getContentType();
			if(contenttype != null) httpreq.addHeader(new HTTPHeader("Content-Type", contenttype));
			String auth = req.getHeader("Authorization");
			if(auth != null) httpreq.addHeader(new HTTPHeader("Authorization", auth));
			HTTPResponse httpresp = urlFetch.fetch(httpreq);
			
			if(httpresp.getResponseCode() == 401)
			{
				resp.setHeader("WWW-Authenticate","BASIC realm=\"Twitter.com realm\"");
				resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
			else
			{
				String resp_content = new String(httpresp.getContent(), "UTF-8");
				if(TRequest.getRequestType(req_url) == TRequest.JSON)
				{
					resp_content.replaceAll(JSONObject.escape("http://static.twitter.com/images/default_profile_normal.png"), JSONObject.escape(web_root + "/img/default_profile_normal.png"));
				}
				else if(TRequest.getRequestType(req_url) == TRequest.XML)
				{
					resp_content.replaceAll("http://static.twitter.com/images/default_profile_normal.png", web_root + "/img/default_profile_normal.png");
				}
				resp.getOutputStream().print(resp_content);
				for (HTTPHeader h : httpresp.getHeaders())
				{
					if(h.getName().equalsIgnoreCase("Set-Cookie"))
					{
						resp.setHeader("Set-Cookie", h.getValue().replaceAll(".twitter.com", req.getServerName()));
					}
					else if(!h.getName().equalsIgnoreCase("Content-length"))
					{
						resp.setHeader(h.getName(), h.getValue());
					}
				}
				//return err code
				if(httpresp.getResponseCode() != 200) resp.sendError(httpresp.getResponseCode());
			}
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String req_url = req.getRequestURI().substring(4);
		String web_root = "http://" + req.getServerName();

		if(TRequest.getRequestType(req_url) == TRequest.NOTSUPPORT)
			resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
		else
		{
			URLFetchService urlFetch = URLFetchServiceFactory.getURLFetchService();
			HTTPRequest httpreq = new HTTPRequest(new URL(twurl + req_url), HTTPMethod.POST);
			
			String UA = req.getHeader("User-Agent");
			if(UA != null) httpreq.addHeader(new HTTPHeader("User-Agent", UA));
			String Conn = req.getHeader("Connection");
			if(Conn != null) httpreq.addHeader(new HTTPHeader("Connection", Conn));
			String contenttype = req.getContentType();
			if(contenttype != null) httpreq.addHeader(new HTTPHeader("Content-Type", contenttype));
			String auth = req.getHeader("Authorization");
			if(auth != null) httpreq.addHeader(new HTTPHeader("Authorization", auth));
			byte[] buf = new byte[req.getContentLength()];
			req.getInputStream().read(buf);
			httpreq.setPayload(buf);
			HTTPResponse httpresp = urlFetch.fetch(httpreq);
			
			if(httpresp.getResponseCode() == 401)
			{
				resp.setHeader("WWW-Authenticate","BASIC realm=\"Twitter.com realm\"");
				resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
			else
			{
				String resp_content = new String(httpresp.getContent(), "UTF-8");
				if(TRequest.getRequestType(req_url) == TRequest.JSON)
				{
					resp_content.replaceAll(JSONObject.escape("http://static.twitter.com/images/default_profile_normal.png"), JSONObject.escape(web_root + "/img/default_profile_normal.png"));
				}
				else if(TRequest.getRequestType(req_url) == TRequest.XML)
				{
					resp_content.replaceAll("http://static.twitter.com/images/default_profile_normal.png", web_root + "/img/default_profile_normal.png");
				}
				resp.getOutputStream().print(resp_content);
				for (HTTPHeader h : httpresp.getHeaders())
				{
					if(h.getName().equalsIgnoreCase("Set-Cookie"))
					{
						resp.setHeader("Set-Cookie", h.getValue().replaceAll(".twitter.com", req.getServerName()));
					}
					else if(!h.getName().equalsIgnoreCase("Content-length"))
					{
						resp.setHeader(h.getName(), h.getValue());
					}
				}
				//return err code
				if(httpresp.getResponseCode() != 200) resp.sendError(httpresp.getResponseCode());
			}
		}
	}
}
