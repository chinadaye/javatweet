package jtweet.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.Paging;
import twitter4j.TwitterException;
import twitter4j.User;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings("serial")
public class FollowServlet extends JTweetServlet {
	protected String uri;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)	throws IOException
	{
		resp.setContentType("text/html; charset=UTF-8");
		String uid = req.getParameter("id");
		uri = req.getRequestURI();
		String action = uri.substring(1);
		String page = req.getParameter("page");
		
		if(isLogin(req))
		{
			init_twitter(getUsername(), getPasswd());
			if(page != null)
			{
				try
				{
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
				}
			}
			if(action.equalsIgnoreCase("follower"))
			{
				getFollower(uid, resp);
			}
			else if(action.equalsIgnoreCase("following"))
			{
				getFollowing(uid, resp);
			}
		}
		else
		{
			redirectLogin(req, resp);
		}	
	}
	
	protected void getFollower(String uid, HttpServletResponse resp) throws IOException
	{
		HashMap<String,Object> root = new HashMap<String,Object>();
		freemarker.template.Configuration config=new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");
		
		List<User> follower;
		try {
			root.put("title", "跟踪者");
			root.put("user", twitter.verifyCredentials());
			if(uid == null)	
			{
				root.put("user_show", twitter.verifyCredentials());
				follower = twitter.getFollowersStatuses(paging);
			}
			else
			{
				root.put("user_show", twitter.showUser(uid));
				follower = twitter.getFollowersStatuses(uid, paging);
			}
			root.put("uri", uri);
			root.put("page", paging.getPage());
			root.put("follow", follower);
			
			Template t = config.getTemplate("follow.ftl");
			t.process(root, resp.getWriter());
			
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void getFollowing(String uid, HttpServletResponse resp) throws IOException
	{
		HashMap<String,Object> root = new HashMap<String,Object>();
		freemarker.template.Configuration config=new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");
		
		List<User> following;
		try {
			root.put("title", "朋友");
			root.put("user", twitter.verifyCredentials());
			if(uid == null)	
			{
				root.put("user_show", twitter.verifyCredentials());
				following = twitter.getFriendsStatuses(paging);
			}
			else
			{
				root.put("user_show", twitter.showUser(uid));
				following = twitter.getFriendsStatuses(uid, paging);
			}
			root.put("uri", uri);
			root.put("page", paging.getPage());
			root.put("follow", following);
			
			Template t = config.getTemplate("follow.ftl");
			t.process(root, resp.getWriter());
			
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
