package jtweet.web;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class OAuthLogoutServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		HttpSession session = req.getSession(true);
		session.invalidate();
		Cookie cookie = new Cookie(BaseServlet.ACCESS_COOKIE_NAME, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		resp.addCookie(cookie);
		resp.sendRedirect("/");
	}
}
