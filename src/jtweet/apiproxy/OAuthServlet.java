package jtweet.apiproxy;

import java.io.IOException;
import java.net.URL;

import javax.servlet.http.*;
import com.google.appengine.api.urlfetch.*;


@SuppressWarnings("serial")
public class OAuthServlet extends HttpServlet {
	
	protected String twurl = "https://twitter.com/oauth/authorize";
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String query_string = req.getQueryString();

		URLFetchService urlFetch = URLFetchServiceFactory.getURLFetchService();
		HTTPRequest httpreq = new HTTPRequest(query_string == null ? new URL(twurl) : new URL(twurl + "?" + query_string), HTTPMethod.GET);
		
		try
		{
			HTTPResponse httpresp = urlFetch.fetch(httpreq);
			String respcon = new String(httpresp.getContent(), "UTF-8");
			respcon = respcon.replaceAll(twurl, "/oauth/authorize");
			resp.getOutputStream().print(respcon);
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
		catch(IOException e)
		{
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			//e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String query_string = req.getQueryString();

		URLFetchService urlFetch = URLFetchServiceFactory.getURLFetchService();
		HTTPRequest httpreq = new HTTPRequest(query_string == null ? new URL(twurl) : new URL(twurl + "?" + query_string), HTTPMethod.POST);
		
		byte[] postpayload = new byte[req.getContentLength()];
		req.getInputStream().read(postpayload);
		httpreq.setPayload(postpayload);
		
		try
		{
			HTTPResponse httpresp = urlFetch.fetch(httpreq);
			String respcon = new String(httpresp.getContent(), "UTF-8");
			respcon = respcon.replaceAll(twurl, "/oauth/authorize");
			resp.getOutputStream().print(respcon);
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
		catch(IOException e)
		{
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			//e.printStackTrace();
		}
	}
}

