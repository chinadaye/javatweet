package jtweet.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import jtweet.Encrypt;
import jtweet.Exception.NotLoginException;
import jtweet.gae.GCache;

import twitter4j.TwitterException;
import twitter4j.User;

import com.google.appengine.repackaged.com.google.common.util.Base64;


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
			HttpSession session = req.getSession(true);
			session.invalidate();
			resp.addCookie(new Cookie(JTweetServlet.ACCOUNT_COOKIE_NAME, null));
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
		String passwd_en = Base64.encode(passwd.getBytes("UTF-8"));
		
		if(action.equalsIgnoreCase("login"))			
		{
			if(username != null && passwd != null)
			{
				HttpSession session = req.getSession(true);
				session.setMaxInactiveInterval(3600);
				init_twitter(username, passwd);
				try {
					User user = twitter.verifyCredentials();
					if(user!=null){
						GCache.put("user:"+username, user,3600*24);
					}
					session.setAttribute("username", username);
					session.setAttribute("passwd", passwd_en);
					
					//在cookie中存储加密账户信息
					if(null!=stayin&&stayin.equals("1")){
						Cookie cookie = new Cookie(JTweetServlet.ACCOUNT_COOKIE_NAME,Encrypt.encodeAccount(username, passwd));
						cookie.setMaxAge(7*24*3600);
						cookie.setPath("/");
						resp.addCookie(cookie);
					}
					JTweetServlet.logger.info("redirect home");
					String rdt = req.getParameter("rdt");//redirect to
					if(rdt!=null&&!rdt.trim().equals("")){
						resp.sendRedirect(rdt);
					}else{
						resp.sendRedirect("/home");
					}
					return;
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
