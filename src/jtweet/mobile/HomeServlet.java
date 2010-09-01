package jtweet.mobile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.mobile.template.GetMiniPic;
import jtweet.mobile.template.TexttoHTML;
import jtweet.util.ShortURL;
import jtweet.util.Utils;
import twitter4j.PagableResponseList;
import twitter4j.Paging;
import twitter4j.TwitterException;
import twitter4j.User;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings("serial")
public class HomeServlet extends BaseServlet {
	protected String uri;
	protected Long in_reply_to = (long) 0;
	protected Paging page = new Paging();
	
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if(!isLogin(req))
		{
			redirectIndex(resp);
			return;
		}
		init_twitter(req, resp);
		
		getTimeline(req, resp);		
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		if(!isLogin(req))
		{
			redirectIndex(resp);
			return;
		}
		init_twitter(req, resp);
		
		String s_in_reply_to = req.getParameter("in_reply_to_id");
		String content = req.getParameter("status");
		try{
			in_reply_to = Long.parseLong(s_in_reply_to);
		} catch(NumberFormatException e){
			in_reply_to = (long)0;
		}
		if(Utils.isEmptyOrNull(content))
		{
			showError(resp, "请勿发送空消息");
			return;
		}
		else if(in_reply_to > 0)
		{
			try {
				content = ShortURL.ShortURL_isgd(content);
				if(content.length() > 140)
				{
					content = content.substring(0, 138) + "…";
				}
				twitter.updateStatus(content, in_reply_to);
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				showError(resp, "发推失败，错误：" + e.getStatusCode());
				return;
			}
		}
		else
		{
			try {
				content = ShortURL.ShortURL_isgd(content);
				if(content.length() > 140)
				{
					content = content.substring(0, 138) + "…";
				}
				twitter.updateStatus(content);
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				showError(resp, "发推失败，错误：" + e.getStatusCode());
				return;
			}
		}
		in_reply_to = (long) 0;
		resp.sendRedirect("/m/home");
	}

	public void getTimeline(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		uri = req.getRequestURI();
		resp.setContentType("text/html; charset=UTF-8");
		String p = req.getParameter("p");
		page.setCount(20);
		page.setPage(1);
		if(!Utils.isEmptyOrNull(p))
		{
			page.setPage(Integer.parseInt(p));
		}
		if(uri.equalsIgnoreCase("/m/home"))
		{
			getHomeTimeline(req, resp);
		}
		else if(uri.equalsIgnoreCase("/m/replies"))
		{
			getReplies(req, resp);
		}
		else if(uri.equalsIgnoreCase("/m/favorites"))
		{
			getFaveorites(req, resp);
		}
		else if(uri.equalsIgnoreCase("/m/retweets_by_me"))
		{
			getRetweetsByMe(req, resp);
		}
		else if(uri.equalsIgnoreCase("/m/retweets_to_me"))
		{
			getRetweetsToMe(req, resp);
		}
		else if(uri.equalsIgnoreCase("/m/public"))
		{
			getPublicTimeline(req, resp);
		}
		else if(uri.equalsIgnoreCase("/m/following"))
		{
			getFollowing(req, resp);
		}
		else if(uri.equalsIgnoreCase("/m/follower"))
		{
			getFollower(req, resp);
		}
		else if(uri.equalsIgnoreCase("/m/blocking"))
		{
			getBlocking(req, resp);
		}
		else
		{
			resp.sendRedirect("/m/home");
		}
	}
	
	public void getHomeTimeline(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("mobile"));
		config.setDefaultEncoding("UTF-8");
		
		try {
			root.put("title", "首页");
			root.put("GetMiniPic", new GetMiniPic());
			root.put("TexttoHTML", new TexttoHTML());
			root.put("login_user", login_user);
			root.put("status", twitter.getHomeTimeline(page));
			root.put("page", page.getPage());
			Template t = config.getTemplate("home.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
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
	
	public void getReplies(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("mobile"));
		config.setDefaultEncoding("UTF-8");
		
		try {
			root.put("title", "回复");
			root.put("GetMiniPic", new GetMiniPic());
			root.put("TexttoHTML", new TexttoHTML());
			root.put("login_user", login_user);
			root.put("status", twitter.getMentions(page));
			root.put("page", page.getPage());
			Template t = config.getTemplate("home.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
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
		config.setDirectoryForTemplateLoading(new File("mobile"));
		config.setDefaultEncoding("UTF-8");
		
		try {
			root.put("title", "收藏");
			root.put("GetMiniPic", new GetMiniPic());
			root.put("TexttoHTML", new TexttoHTML());
			root.put("login_user", login_user);
			root.put("status", twitter.getFavorites(page.getPage()));
			root.put("page", page.getPage());
			Template t = config.getTemplate("home.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
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
	
	public void getRetweetsByMe(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("mobile"));
		config.setDefaultEncoding("UTF-8");
		
		try {
			root.put("title", "Retweets by me");
			root.put("GetMiniPic", new GetMiniPic());
			root.put("TexttoHTML", new TexttoHTML());
			root.put("login_user", login_user);
			root.put("status", twitter.getRetweetedByMe(page));
			root.put("page", page.getPage());
			Template t = config.getTemplate("home.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
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
	
	public void getRetweetsToMe(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("mobile"));
		config.setDefaultEncoding("UTF-8");
		
		try {
			root.put("title", "Retweets To me");
			root.put("GetMiniPic", new GetMiniPic());
			root.put("TexttoHTML", new TexttoHTML());
			root.put("login_user", login_user);
			root.put("status", twitter.getRetweetedToMe(page));
			root.put("page", page.getPage());
			Template t = config.getTemplate("home.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
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
			root.put("user", login_user);
			PagableResponseList<User> follows = twitter.getFriendsStatuses(c);
			root.put("follows", follows);
			root.put("previouscursor", follows.getPreviousCursor());
			root.put("nextcursor", follows.getNextCursor());
			Template t = config.getTemplate("follow.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
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
			root.put("user", login_user);
			PagableResponseList<User> follows = twitter.getFollowersStatuses(c);
			root.put("follows", follows);
			root.put("previouscursor", follows.getPreviousCursor());
			root.put("nextcursor", follows.getNextCursor());
			Template t = config.getTemplate("follow.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
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
	
	public void getBlocking(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("mobile"));
		config.setDefaultEncoding("UTF-8");
		
		String s_p = req.getParameter("p");
		int p = 1;
		if(!Utils.isEmptyOrNull(s_p))
		{
			p = Integer.parseInt(s_p);
		}
		
		try {
			root.put("title", "屏蔽列表");
			root.put("GetMiniPic", new GetMiniPic());
			root.put("login_user", login_user);
			root.put("user", login_user);
			root.put("follows", twitter.getBlockingUsers(p));
			root.put("page", p);
			Template t = config.getTemplate("follow.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
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
	
	public void getPublicTimeline(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("mobile"));
		config.setDefaultEncoding("UTF-8");
		
		try {
			root.put("title", "公共页面");
			root.put("GetMiniPic", new GetMiniPic());
			root.put("TexttoHTML", new TexttoHTML());
			root.put("login_user", login_user);
			root.put("status", twitter.getPublicTimeline());
			Template t = config.getTemplate("home.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
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
