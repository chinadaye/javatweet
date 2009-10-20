package jtweet.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import twitter4j.TwitterException;

import com.google.appengine.repackaged.com.google.common.util.Base64;


@SuppressWarnings("serial")
public class LoginServlet extends JTweetServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		String action= req.getRequestURI().substring(1);
		
		if(action.equalsIgnoreCase("login"))			
		{
			if(isLogin(req))
			{
				resp.sendRedirect("/home");
				return;
			}
		}
		else if(action.equalsIgnoreCase("logout"))
		{
			redirectLogin(req, resp);
			return;
		}
		else
		{
			redirectLogin(req, resp);
			return;
		}
		
		try {
			req.getRequestDispatcher("template/login.jsp").include(req, resp);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		resp.setContentType("text/html; charset=UTF-8");	
		String action= req.getRequestURI().substring(1);
		String username = req.getParameter("username");
		String passwd = req.getParameter("passwd");
		String passwd_en = Base64.encode(passwd.getBytes("UTF-8"));
		
		if(action.equalsIgnoreCase("login"))			
		{
			if(username != null && passwd != null)
			{
				HttpSession session = req.getSession(true);
				session.setMaxInactiveInterval(3600);
				init_twitter(username, passwd);
				try {
					twitter.verifyCredentials();
					session.setAttribute("username", username);
					session.setAttribute("passwd", passwd_en);
					
					resp.sendRedirect("/home");
					return;
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					redirectLogin(req, resp);
					e.printStackTrace();
					return;
				}
			}
		}
		else
		{
			redirectLogin(req, resp);
		}
	}
}