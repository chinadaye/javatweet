package jtweet.web;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class ShortURLProxy extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException {
		String url = req.getParameter("u");
		if(url != null)
		{
			String longurl = ShortURL.getLongURL(url);
			if(longurl != null) resp.sendRedirect(longurl);
			else resp.sendRedirect(url);
		}
		else
		{
			resp.sendError(500);
		}
	}
}
