package jtweet.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.gae.GCache;
import jtweet.util.Utils;
import jtweet.web.template.GetBigPic;
import jtweet.web.template.TexttoHTML;

import twitter4j.PagableResponseList;
import twitter4j.TwitterException;
import twitter4j.User;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings("serial")
public class UserServlet extends BaseServlet {
	protected String user_screenname;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if(!isLogin(req))
		{
			redirectIndex(resp);
			return;
		}
		init_twitter(req, resp);
		
		resp.setContentType("text/html; charset=UTF-8");
		String uri = req.getRequestURI();
		String[] path = uri.split("/");
		
		if(path.length < 3)
		{
			resp.sendRedirect("/home");
			return;
		}
		user_screenname = path[2];
		String action;
		if(path.length == 3)
		{
			action = "status";
		}
		else
		{
			action = path[3];
		}
		if(action.equalsIgnoreCase("status"))
		{
			getTimeline(req, resp);	
		}
		else if(action.equals("favorites"))
		{
			getFaveorites(req, resp);
		}
		else if(action.equals("follower"))
		{
			getFollower(req, resp);
		}
		else if(action.equalsIgnoreCase("following"))
		{
			getFollowing(req, resp);
		}
		else
		{
			resp.sendRedirect("/home");
		}
		
			
	}

	public void getTimeline(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");
		
		try {
			root.put("title", "时间线");
			root.put("title_en", "Timeline");
			root.put("uri", "/home");
			root.put("getbigpic", new GetBigPic());
			root.put("texttohtml", new TexttoHTML());
			root.put("login_user", login_user);
			User user = (User) GCache.get("user:" + user_screenname);
			if (null == user) {
				user = twitter.showUser(user_screenname);
				GCache.put("user:" + user_screenname, user, 120);
			}
			root.put("user", user);
			root.put("blocked", twitter.existsBlock(user_screenname));
			if(!user.isProtected() || user_screenname.equalsIgnoreCase(twitter.getScreenName()) || twitter.existsFriendship(twitter.getScreenName(), user_screenname))
			{
				root.put("status", twitter.getUserTimeline(user_screenname));
			}
			String[] js = {"/js/user.js"};
			root.put("js", js);
			root.put("morefunction", "javascript:ongetmoreusertimeline('" + user_screenname + "')");
			Template t = config.getTemplate("user.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			if(e.getStatusCode() == 401)
			{
				redirectIndex(resp);
			}
			else if(e.getStatusCode() > 0)
			{
				resp.sendError(e.getStatusCode());
			}
			else
			{
				resp.getOutputStream().println("Error Message: " + e.getMessage());
			}
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getFaveorites(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");
		
		try {
			root.put("title", "收藏");
			root.put("title_en", "Faveorites");
			root.put("uri", "/home");
			root.put("getbigpic", new GetBigPic());
			root.put("texttohtml", new TexttoHTML());
			root.put("login_user", login_user);
			User user = (User) GCache.get("user:" + user_screenname);
			if (null == user) {
				user = twitter.showUser(user_screenname);
				GCache.put("user:" + user_screenname, user, 120);
			}
			root.put("user", user);
			root.put("blocked", twitter.existsBlock(user_screenname));
			if(!user.isProtected() || user_screenname.equalsIgnoreCase(twitter.getScreenName()) || twitter.existsFriendship(twitter.getScreenName(), user_screenname))
			{
				root.put("status", twitter.getFavorites(user_screenname));
			}
			String[] js = {"/js/user.js"};
			root.put("js", js);
			root.put("morefunction", "javascript:ongetmoreuserfav('" + user_screenname + "')");
			Template t = config.getTemplate("user.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			if(e.getStatusCode() == 401)
			{
				redirectIndex(resp);
			}
			else if(e.getStatusCode() > 0)
			{
				resp.sendError(e.getStatusCode());
			}
			else
			{
				resp.getOutputStream().println("Error Message: " + e.getMessage());
			}
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getFollowing(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");
		
		String s_c = req.getParameter("c");
		long c = -1;
		if(!Utils.isEmptyOrNull(s_c))
		{
			c = Long.parseLong(s_c);
		}
		
		try {
			root.put("title", "朋友");
			root.put("title_en", "Following");
			root.put("login_user", login_user);
			User user = (User) GCache.get("user:" + user_screenname);
			if (null == user) {
				user = twitter.showUser(user_screenname);
				GCache.put("user:" + user_screenname, user, 120);
			}
			root.put("user", user);
			PagableResponseList<User> follows = twitter.getFriendsStatuses(user_screenname,c);
			root.put("follows", follows);
			root.put("previouscursor", follows.getPreviousCursor());
			root.put("nextcursor", follows.getNextCursor());
			String[] js = {"/js/follow.js"};
			root.put("js", js);
			Template t = config.getTemplate("follow.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			if(e.getStatusCode() == 401)
			{
				redirectIndex(resp);
			}
			else if(e.getStatusCode() > 0)
			{
				resp.sendError(e.getStatusCode());
			}
			else
			{
				resp.getOutputStream().println("Error Message: " + e.getMessage());
			}
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getFollower(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");
		
		String s_c = req.getParameter("c");
		long c = -1;
		if(!Utils.isEmptyOrNull(s_c))
		{
			c = Long.parseLong(s_c);
		}
		
		try {
			root.put("title", "关注者");
			root.put("title_en", "Follower");
			root.put("login_user", login_user);
			User user = (User) GCache.get("user:" + user_screenname);
			if (null == user) {
				user = twitter.showUser(user_screenname);
				GCache.put("user:" + user_screenname, user, 120);
			}
			root.put("user", user);
			PagableResponseList<User> follows = twitter.getFollowersStatuses(user_screenname, c);
			root.put("follows", follows);
			root.put("previouscursor", follows.getPreviousCursor());
			root.put("nextcursor", follows.getNextCursor());
			String[] js = {"/js/follow.js"};
			root.put("js", js);
			Template t = config.getTemplate("follow.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			if(e.getStatusCode() == 401)
			{
				redirectIndex(resp);
			}
			else if(e.getStatusCode() > 0)
			{
				resp.sendError(e.getStatusCode());
			}
			else
			{
				resp.getOutputStream().println("Error Message: " + e.getMessage());
			}
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
