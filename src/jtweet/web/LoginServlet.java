package jtweet.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import twitter4j.TwitterException;

import com.google.appengine.repackaged.com.google.common.util.Base64;

import freemarker.template.Template;
import freemarker.template.TemplateException;


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
		
		String UA = req.getHeader("User-Agent");
		if(UA == null)
		{
			browser = "other";
		}
		else if(UA.contains("MSIE 6.0"))
		{
			browser = "ie6";
		}
		else if(UA.contains("MSIE 7.0"))
		{
			browser = "ie7";
		}
		else
		{
			browser = "other";
		}
		
		
		HashMap<String,Object> root = new HashMap<String,Object>();
		freemarker.template.Configuration config=new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");
		root.put("browser", browser);
		root.put("server", req.getServerName());
		Template t = config.getTemplate("login.ftl");
		try {
			t.process(root, resp.getWriter());
		} catch (TemplateException e) {
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
				init_twitter(username, passwd, req);
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
