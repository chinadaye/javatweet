package jtweet.web;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.gae.GCache;


@SuppressWarnings("serial")
public class ShortURLProxy extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException {
		String url = req.getParameter("u");
		if(url != null)
		{
			String longurl;
			longurl = (String)GCache.get(url);
			if(longurl == null)
			{
				longurl = ShortURL.getLongURL(url);
				if(longurl != null) GCache.put(url, longurl, 7200);
			}
			if(longurl != null) resp.sendRedirect(longurl);
			else
			{
				resp.setContentType("text/html; charset=UTF-8");
				String err = "<html><head><title>短网址展开出错</title></head><body>Sorry!短网址 <a href=\"" + url +"\">" + url +"</a> 展开出错。</body></html>";
				resp.getWriter().println(err);
			}
		}
		else
		{
			resp.sendError(500);
		}
	}
}
