package jtweet.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.util.ShortURL;
import jtweet.util.Utils;
import jtweet.web.template.TexttoHTML;
import twitter4j.PagableResponseList;
import twitter4j.TwitterException;
import twitter4j.User;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings("serial")
public class HomeServlet extends BaseServlet {
	protected String uri;
	protected String text = new String();
	protected String action_result = new String();
	protected Long in_reply_to = (long) 0;
	
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if(!isLogin(req))
		{
			redirectIndex(resp);
			return;
		}
		init_twitter(req, resp);
		
		String action = req.getParameter("action");
		String s_status_id = req.getParameter("id");
		if(Utils.isEmptyOrNull(action))
		{
			text = "";
			in_reply_to = (long) 0;
		}
		else if(Utils.isEmptyOrNull(s_status_id))
		{
			if(action.equalsIgnoreCase("fo"))
			{
				String fo = req.getParameter("u");
				try {
					twitter.createFriendship(fo);
					action_result = "Follow成功。";
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					action_result = "Follow失败，错误：" + e.getStatusCode();
				}
			}
			else if(action.equalsIgnoreCase("unfo"))
			{
				String unfo = req.getParameter("u");
				try {
					twitter.destroyFriendship(unfo);
					action_result = "UnFollow成功。";
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					action_result = "UnFollow失败，错误：" + e.getStatusCode();
				}
			}
			else if(action.equalsIgnoreCase("b"))
			{
				String b = req.getParameter("u");
				try {
					twitter.createBlock(b);
					action_result = "Block成功。";
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					action_result = "Block失败，错误：" + e.getStatusCode();
				}
			}
			else if(action.equalsIgnoreCase("unb"))
			{
				String unb = req.getParameter("u");
				try {
					twitter.destroyBlock(unb);
					action_result = "UnBlock成功。";
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					action_result = "Unblock失败，错误：" + e.getStatusCode();
				}
			}
			else
			{
				text = "";
				in_reply_to = (long) 0;
			}
		}
		else if(action.equalsIgnoreCase("re"))
		{
			in_reply_to = Long.parseLong(s_status_id);
			String to = req.getParameter("u");
			text = "@" + to + " ";
		}
		else if(action.equalsIgnoreCase("rt_t"))
		{
			Long id = Long.parseLong(s_status_id);
			String source = req.getParameter("u");
			try {
				text = twitter.showStatus(id).getText();
				text = "RT @" + source + " " + text;
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				action_result = "RT 失败，错误：" + e.getStatusCode();
			}
		}
		else if(action.equalsIgnoreCase("rt"))
		{
			Long id = Long.parseLong(s_status_id);
			try {
				twitter.retweetStatus(id);
				action_result = "RT成功。";
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				action_result = "RT 失败，错误：" + e.getStatusCode();
			}
			
		}
		else if(action.equalsIgnoreCase("unfav"))
		{
			Long id = Long.parseLong(s_status_id);
			try {
				twitter.destroyFavorite(id);
				action_result = "取消收藏成功。";
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				action_result = "取消收藏失败，错误：" + e.getStatusCode();
			}
		}
		else if(action.equalsIgnoreCase("fav"))
		{
			Long id = Long.parseLong(s_status_id);
			try {
				twitter.createFavorite(id);
				action_result = "收藏成功。";
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				action_result = "收藏失败，错误：" + e.getStatusCode();
			}
		}
		else if(action.equalsIgnoreCase("del"))
		{
			Long id = Long.parseLong(s_status_id);
			try {
				twitter.destroyStatus(id);
				action_result = "删除成功。";
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				action_result = "删除失败，错误：" + e.getStatusCode();
			}
		}
		else
		{
			text = "";
			in_reply_to = (long) 0;
		}
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
		
		String s_in_reply_to = req.getParameter("in_reply_to_status_id");
		String content = req.getParameter("status");
		in_reply_to = Long.parseLong(s_in_reply_to);
		if(Utils.isEmptyOrNull(content))
		{
			action_result = "请勿发送空消息";
		}
		else if(in_reply_to > 0)
		{
			try {
				if(content.length() > 140)
				{
					content = content.substring(0, 138) + "…";
				}
				twitter.updateStatus(content, in_reply_to);
				action_result = "发推成功。";
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				action_result = "发推失败，错误：" + e.getStatusCode();
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
				action_result = "发推成功。";
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				action_result = "发推失败，错误：" + e.getStatusCode();
			}
		}
		in_reply_to = (long) 0;
		text = "";
		getTimeline(req, resp);
	}
	
	public void getTimeline(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		uri = req.getRequestURI();
		resp.setContentType("text/html; charset=UTF-8");
		if(uri.equalsIgnoreCase("/home"))
		{
			getHomeTimeline(req, resp);
		}
		else if(uri.equalsIgnoreCase("/replies"))
		{
			getReplies(req, resp);
		}
		else if(uri.equalsIgnoreCase("/favorites"))
		{
			getFaveorites(req, resp);
		}
		else if(uri.equalsIgnoreCase("/retweets_by_me"))
		{
			getRetweetsByMe(req, resp);
		}
		else if(uri.equalsIgnoreCase("/retweets_to_me"))
		{
			getRetweetsToMe(req, resp);
		}
		else if(uri.equalsIgnoreCase("/public"))
		{
			getPublicTimeline(req, resp);
		}
		else if(uri.equalsIgnoreCase("/following"))
		{
			getFollowing(req, resp);
		}
		else if(uri.equalsIgnoreCase("/follower"))
		{
			getFollower(req, resp);
		}
		else if(uri.equalsIgnoreCase("/blocking"))
		{
			getBlocking(req, resp);
		}
		else
		{
			resp.sendRedirect("/home");
		}
	}
	
	public void getHomeTimeline(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");
		
		try {
			root.put("title", "首页");
			root.put("title_en", "Home");
			root.put("in_reply_to", in_reply_to);
			root.put("uri", uri);
			root.put("text", text);
			root.put("action_result", action_result);
			root.put("texttohtml", new TexttoHTML());
			root.put("login_user", login_user);
			root.put("status", twitter.getHomeTimeline());
			String[] js = {"/js/home.js"};
			root.put("js", js);
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
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");
		
		try {
			root.put("title", "回复");
			root.put("title_en", "Replies");
			root.put("uri", uri);
			root.put("in_reply_to", in_reply_to);
			root.put("text", text);
			root.put("action_result", action_result);
			root.put("texttohtml", new TexttoHTML());
			root.put("login_user", login_user);
			root.put("status", twitter.getMentions());
			String[] js = {"/js/timeline.js"};
			root.put("js", js);
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
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");
		
		try {
			root.put("title", "收藏");
			root.put("title_en", "Faveorites");
			root.put("uri", uri);
			root.put("in_reply_to", in_reply_to);
			root.put("text", text);
			root.put("action_result", action_result);
			root.put("texttohtml", new TexttoHTML());
			root.put("login_user", login_user);
			root.put("status", twitter.getFavorites());
			String[] js = {"/js/timeline.js"};
			root.put("js", js);
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
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");
		
		try {
			root.put("title", "Retweets by me");
			root.put("title_en", "Retweets by me");
			root.put("uri", uri);
			root.put("in_reply_to", in_reply_to);
			root.put("text", text);
			root.put("action_result", action_result);
			root.put("texttohtml", new TexttoHTML());
			root.put("login_user", login_user);
			root.put("status", twitter.getRetweetedByMe());
			String[] js = {"/js/timeline.js"};
			root.put("js", js);
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
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");
		
		try {
			root.put("title", "Retweets To me");
			root.put("title_en", "Retweets To me");
			root.put("uri", uri);
			root.put("in_reply_to", in_reply_to);
			root.put("text", text);
			root.put("action_result", action_result);
			root.put("texttohtml", new TexttoHTML());
			root.put("login_user", login_user);
			root.put("status", twitter.getRetweetedToMe());
			String[] js = {"/js/timeline.js"};
			root.put("js", js);
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
			root.put("user", login_user);
			root.put("action_result", action_result);
			PagableResponseList<User> follows = twitter.getFriendsStatuses(c);
			root.put("follows", follows);
			root.put("previouscursor", follows.getPreviousCursor());
			root.put("nextcursor", follows.getNextCursor());
			String[] js = {"/js/follow.js"};
			root.put("js", js);
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
			root.put("user", login_user);
			root.put("action_result", action_result);
			PagableResponseList<User> follows = twitter.getFollowersStatuses(c);
			root.put("follows", follows);
			root.put("previouscursor", follows.getPreviousCursor());
			root.put("nextcursor", follows.getNextCursor());
			String[] js = {"/js/follow.js"};
			root.put("js", js);
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
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");
		
		String s_p = req.getParameter("p");
		int p = 1;
		if(!Utils.isEmptyOrNull(s_p))
		{
			p = Integer.parseInt(s_p);
		}
		
		try {
			root.put("title", "屏蔽列表");
			root.put("title_en", "Blocking");
			root.put("login_user", login_user);
			root.put("user", login_user);
			root.put("action_result", action_result);
			root.put("follows", twitter.getBlockingUsers(p));
			root.put("page", p);
			String[] js = {"/js/follow.js"};
			root.put("js", js);
			Template t = config.getTemplate("block.ftl");
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
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");
		
		try {
			root.put("title", "公共页面");
			root.put("title_en", "Public");
			root.put("uri", uri);
			root.put("in_reply_to", in_reply_to);
			root.put("text", text);
			root.put("action_result", action_result);
			root.put("texttohtml", new TexttoHTML());
			root.put("login_user", login_user);
			root.put("status", twitter.getPublicTimeline());
			String[] js = {"/js/timeline.js"};
			root.put("js", js);
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
