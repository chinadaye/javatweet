package jtweet.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.Exception.NotLoginException;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings("serial")
public class UserServlet extends JTweetServlet {
	protected String uri;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
			resp.setContentType("text/html; charset=UTF-8");
			uri = req.getRequestURI();
			String uid = req.getParameter("id");
			String page = req.getParameter("page");
			String show = req.getParameter("show");
			
			if(isLogin(req) && uid!= null)
			{
				
				if(page != null)
				{
					try
					{
						init_twitter(getUsername(), getPasswd());
						int p = Integer.parseInt(page);
						if(p > 0) paging.setPage(p);
						else
						{
							resp.sendRedirect(uri);
							return;
						}
					}
					catch (NumberFormatException e)
					{
						resp.sendRedirect(uri);
						return;
					} catch (NotLoginException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(show == null)
				{
					getUserTimeline(uid, resp);
				}
				else if(show.equalsIgnoreCase("favor"))
				{
					getUserFavor(uid, resp);
				}
				else
				{
					resp.sendRedirect("/home");
					return;
				}
				
			}
			else
			{
				redirectLogin(req, resp);
			}

	}
	protected void getUserTimeline(String uid, HttpServletResponse resp) throws IOException
	{
		HashMap<String,Object> root = new HashMap<String,Object>();
		freemarker.template.Configuration config=new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");
		
		try {
			User user = twitter.showUser(uid);
			root.put("user", this.getCachedUser());
			root.put("rate", twitter.rateLimitStatus());
			root.put("user_show", user);
			root.put("title", "时间线");
			root.put("uri", uri + "?id=" + uid);
			root.put("page", paging.getPage());
			
			if(user.getScreenName().equalsIgnoreCase(getUsername()) || (!user.isProtected()))
			{
				List<Status> status = twitter.getUserTimeline(uid, paging);
				root.put("status", status);
			}
			
			Template t = config.getTemplate("user.ftl");
			t.process(root, resp.getWriter());
			
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			resp.sendError(e.getStatusCode());
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotLoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void getUserFavor(String uid, HttpServletResponse resp) throws IOException
	{
		HashMap<String,Object> root = new HashMap<String,Object>();
		freemarker.template.Configuration config=new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");
		
		try {
			User user = twitter.showUser(uid);
			root.put("user", this.getCachedUser());
			root.put("rate", twitter.rateLimitStatus());
			root.put("user_show", user);
			root.put("title", "收藏");
			root.put("uri", uri + "?id=" + uid + "&show=favor");
			root.put("page", paging.getPage());
			
			if(!user.isProtected())
			{
				List<Status> status = twitter.getFavorites(uid, paging.getPage());
				root.put("status", status);
			}
			
			Template t = config.getTemplate("user.ftl");
			t.process(root, resp.getWriter());
			
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			resp.sendError(e.getStatusCode());
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotLoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}