package jtweet.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import jtweet.Encrypt;
import jtweet.Exception.NotLoginException;
import jtweet.gae.GCache;

import twitter4j.TwitterException;
import twitter4j.User;


import freemarker.template.Template;


@SuppressWarnings("serial")
public class LoginServlet extends JTweetServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		String action= req.getRequestURI().substring(1);
		
		if(action.equalsIgnoreCase("login"))			
		{
			try {
				this.revertAccount(req);
				JTweetServlet.logger.info("redirect home");
				resp.sendRedirect("/home");
			} catch (NotLoginException e) {
				String rdt = req.getParameter("rdt");//redirect to
				if(rdt==null||rdt.trim().equals("")){
					rdt = req.getRequestURI();
				}
				req.setAttribute("rdt", rdt);
				//进行登录
				try {
					req.setAttribute("trends", this.getTrend());
					req.getRequestDispatcher("template/login.jsp").forward(req, resp);
				} catch (ServletException e1) {
					JTweetServlet.logger.warning(e1.getMessage());
					this.showError(req, resp, e1.getMessage());
				}
				
			} catch (Exception e) {
				JTweetServlet.logger.warning(e.getMessage());
				this.showError(req, resp, e.getMessage());
			}
		}
		else if(action.equalsIgnoreCase("logout"))
		{
//			HttpSession session = req.getSession(true);
//			session.invalidate();
			resp.addCookie(new Cookie(JTweetServlet.ACCOUNT_COOKIE_NAME, ""));
			redirectLogin(req, resp);
			return;
		}
		else
		{
			resp.sendRedirect("/home");
			return;
		}
		
		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		resp.setContentType("text/html; charset=UTF-8");	
		String action= req.getRequestURI().substring(1);
		String username = req.getParameter("username");
		String passwd = req.getParameter("passwd");
		String stayin = req.getParameter("stayin");
		
		if(action.equalsIgnoreCase("login"))			
		{
			if(username != null && passwd != null)
			{
				init_twitter(username, passwd);
				try {
					User user = twitter.verifyCredentials();
					if(user!=null){
						GCache.put("user:"+username, user,3600*24);
					}
					HashMap<String, Object> root = new HashMap<String, Object>();
					freemarker.template.Configuration config = new freemarker.template.Configuration();
					config.setDirectoryForTemplateLoading(new File("template"));
					config.setDefaultEncoding("UTF-8");
					
					//在cookie中存储加密账户信息
					if(null!=stayin&&stayin.equals("1")){
						String cookieValue=Encrypt.encodeAccount(username, passwd);
						Cookie cookie = new Cookie(JTweetServlet.ACCOUNT_COOKIE_NAME,cookieValue);
						cookie.setMaxAge(7*24*3600);
						cookie.setPath("/");
						resp.addCookie(cookie);
					}else{
						String cookieValue=Encrypt.encodeAccount(username, passwd);
						Cookie cookie = new Cookie(JTweetServlet.ACCOUNT_COOKIE_NAME,cookieValue);
						cookie.setMaxAge(24*3600);
						cookie.setPath("/");
						resp.addCookie(cookie);
					}
					
					String rdt = req.getParameter("rdt");//redirect to
					
					if(rdt!=null&&!rdt.trim().equals("")){
						root.put("to", rdt);
					}else{
						root.put("to", "/");
					}
					
					Template t = config.getTemplate("redirect.ftl");
					t.process(root, resp.getWriter());
				} catch (TwitterException e) {
					JTweetServlet.logger.warning(e.getMessage());
					req.setAttribute("error", e.getMessage());
					try {
						getServletContext().getRequestDispatcher("/template/login.jsp").forward(req, resp);
					} catch (ServletException e1) {
						JTweetServlet.logger.warning(e1.getMessage());
						e1.printStackTrace();
					}
				} catch(Exception e){
					this.showError(req, resp, e.getMessage());
				}
			}
		}
		else
		{
			resp.sendRedirect("/home");
		}
	}
}
