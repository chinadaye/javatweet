package jtweet.mobile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import jtweet.gae.Configuration;
import jtweet.gae.GCache;
import jtweet.util.Encrypt;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.http.AccessToken;

@SuppressWarnings("serial")
public class BaseServlet extends HttpServlet
{
	protected Twitter twitter = null;
	private AccessToken accessToken = null;
	public static final String ACCESS_COOKIE_NAME = "access";
	public String login_screenname;
	public User login_user;
		
	//读取session和cookie，判断是否已经登陆
	@SuppressWarnings("deprecation")
	protected boolean isLogin(HttpServletRequest req) 
	{
		Cookie[] cookies = req.getCookies();
		if (cookies == null)
		{
			return false;
		}
		
		Cookie accountCookie = null;
		for (Cookie cookie : cookies)
		{
			if (cookie.getName().equals(BaseServlet.ACCESS_COOKIE_NAME))
			{
				accountCookie = cookie;
				break;
			}
		}

		if (accountCookie == null || accountCookie.getValue() == null)
		{
			return false;
		}

		String[] accountString = Encrypt.decodeAccount(URLDecoder.decode(accountCookie.getValue()));

		if (accountString == null)
		{
			return false;
		}
		String Token = accountString[0];
		String TokenSecret = accountString[1];
		accessToken = new AccessToken(Token, TokenSecret);
		return true;
	}
	
	//清空cookie中的登陆信息，重定向到登陆页面
	protected void redirectIndex(HttpServletResponse resp) throws IOException
	{
		resp.sendRedirect("/m/logout");
	}
	
	//初始化twitter
	public void init_twitter(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		TwitterFactory twitterfactory = new TwitterFactory();
		twitter = twitterfactory.getOAuthAuthorizedInstance(Configuration.getConsumerKey(), Configuration.getConsumerSecret(), accessToken);
		try {
			login_screenname = twitter.getScreenName();
			login_user = (User) GCache.get("user:" + login_screenname + ":" + login_screenname);
			if (null == login_user) {
				login_user = twitter.verifyCredentials();
				GCache.put("user:" + login_screenname + ":" + login_screenname, login_user, 120);
			}
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
		}
	}
	
	public void showError(HttpServletResponse resp, String err) throws IOException
	{
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("mobile"));
		config.setDefaultEncoding("UTF-8");
		
		try {;
			root.put("err", err);
			Template t = config.getTemplate("err.ftl");
			t.process(root, resp.getWriter());
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public AccessToken getAccessToken()
	{
		return accessToken;
	}

}
