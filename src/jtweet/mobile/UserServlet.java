package jtweet.mobile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.gae.GCache;
import jtweet.mobile.template.GetMiniPic;
import jtweet.mobile.template.TexttoHTML;
import jtweet.util.Utils;
import twitter4j.PagableResponseList;
import twitter4j.Paging;
import twitter4j.TwitterException;
import twitter4j.User;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings("serial")
public class UserServlet extends BaseServlet {
	protected String user_screenname;
	protected Paging page = new Paging();
	
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
		
		if(path.length < 4)
		{
			resp.sendRedirect("/home");
			return;
		}
		user_screenname = path[3];
		
		String p = req.getParameter("p");
		page.setCount(20);
		page.setPage(1);
		if(!Utils.isEmptyOrNull(p))
		{
			page.setPage(Integer.parseInt(p));
		}
		
		String action;
		if(path.length == 4)
		{
			action = "status";
		}
		else
		{
			action = path[4];
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
		config.setDirectoryForTemplateLoading(new File("mobile"));
		config.setDefaultEncoding("UTF-8");
		
		try {
			root.put("title", "时间线");
			root.put("GetMiniPic", new GetMiniPic());
			root.put("TexttoHTML", new TexttoHTML());
			root.put("login_user", login_user);
			User user = (User) GCache.get("user:" + login_screenname + ":" + user_screenname);
			if (null == user) {
				user = twitter.showUser(user_screenname);
				GCache.put("user:" + login_screenname + ":" + user_screenname, user, 120);
			}
			root.put("user", user);
			root.put("blocked", twitter.existsBlock(user_screenname));
			root.put("status", twitter.getUserTimeline(user_screenname, page));
			root.put("page", page.getPage());
			Template t = config.getTemplate("user.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			if(e.getStatusCode() == 401)
			{
				Template t = config.getTemplate("user.ftl");
				try {
					t.process(root, resp.getWriter());
				} catch (TemplateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
		config.setDirectoryForTemplateLoading(new File("mobile"));
		config.setDefaultEncoding("UTF-8");
		
		try {
			root.put("title", "收藏");
			root.put("GetMiniPic", new GetMiniPic());
			root.put("TexttoHTML", new TexttoHTML());
			root.put("login_user", login_user);
			User user = (User) GCache.get("user:" + login_screenname + ":" + user_screenname);
			if (null == user) {
				user = twitter.showUser(user_screenname);
				GCache.put("user:" + login_screenname + ":" + user_screenname, user, 120);
			}
			root.put("user", user);
			root.put("blocked", twitter.existsBlock(user_screenname));
			root.put("status", twitter.getFavorites(user_screenname, page.getPage()));
			root.put("page", page.getPage());
			Template t = config.getTemplate("user.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			if(e.getStatusCode() == 401)
			{
				Template t = config.getTemplate("user.ftl");
				try {
					t.process(root, resp.getWriter());
				} catch (TemplateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
		config.setDirectoryForTemplateLoading(new File("mobile"));
		config.setDefaultEncoding("UTF-8");
		
		String s_c = req.getParameter("c");
		long c = -1;
		if(!Utils.isEmptyOrNull(s_c))
		{
			c = Long.parseLong(s_c);
		}
		
		try {
			root.put("title", "朋友");
			root.put("GetMiniPic", new GetMiniPic());
			root.put("login_user", login_user);
			User user = (User) GCache.get("user:" + login_screenname + ":" + user_screenname);
			if (null == user) {
				user = twitter.showUser(user_screenname);
				GCache.put("user:" + login_screenname + ":" + user_screenname, user, 120);
			}
			root.put("user", user);
			PagableResponseList<User> follows = twitter.getFriendsStatuses(user_screenname,c);
			root.put("follows", follows);
			root.put("previouscursor", follows.getPreviousCursor());
			root.put("nextcursor", follows.getNextCursor());
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
		config.setDirectoryForTemplateLoading(new File("mobile"));
		config.setDefaultEncoding("UTF-8");
		
		String s_c = req.getParameter("c");
		long c = -1;
		if(!Utils.isEmptyOrNull(s_c))
		{
			c = Long.parseLong(s_c);
		}
		
		try {
			root.put("title", "关注者");
			root.put("GetMiniPic", new GetMiniPic());
			root.put("login_user", login_user);
			User user = (User) GCache.get("user:" + login_screenname + ":" + user_screenname);
			if (null == user) {
				user = twitter.showUser(user_screenname);
				GCache.put("user:" + login_screenname + ":" + user_screenname, user, 120);
			}
			root.put("user", user);
			PagableResponseList<User> follows = twitter.getFollowersStatuses(user_screenname, c);
			root.put("follows", follows);
			root.put("previouscursor", follows.getPreviousCursor());
			root.put("nextcursor", follows.getNextCursor());
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
