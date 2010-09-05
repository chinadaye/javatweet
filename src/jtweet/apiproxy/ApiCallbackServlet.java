package jtweet.apiproxy;

import java.io.IOException;

import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jtweet.gae.Configuration;
import jtweet.gae.GCache;
import jtweet.gae.PMF;
import jtweet.util.Utils;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;

@SuppressWarnings("serial")
public class ApiCallbackServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		String oauthVerifier = req.getParameter("oauth_verifier");
		String uri = req.getRequestURI();
		
		if(Utils.isEmptyOrNull(oauthVerifier) && uri.equalsIgnoreCase("/apicallback"))
		{
			resp.sendRedirect("/api");
			return;
		}
		
				
		HttpSession session = req.getSession(true);
		session.setMaxInactiveInterval(3600);
		if(uri.equalsIgnoreCase("/apilogout"))
		{
			AccessToken accessToken = (AccessToken)session.getAttribute("apitoken");
			if(accessToken != null)
			{
				delToken(accessToken);
				session.removeAttribute("apitoken");
				String out = "<html><head><title>删除信息</title></head><body><h3>" + accessToken.getScreenName() + "的登陆信息已删除</h3></p></body>";
				resp.getOutputStream().write(out.getBytes("UTF-8"));
			}
			else
			{
				resp.sendRedirect("/api");
			}
			return;
		}
		
		RequestToken requestToken = (RequestToken) session.getAttribute("requestToken");
		session.removeAttribute("requestToken");
		
		TwitterFactory twitterfactory = new TwitterFactory();
		Twitter twitter = twitterfactory.getOAuthAuthorizedInstance(Configuration.getConsumerKey(), Configuration.getConsumerSecret());
		
		try {
			AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, oauthVerifier);
			session.setAttribute("apitoken", accessToken);
			GCache.put("apitoken:" + twitter.getScreenName(), accessToken, 3600 * 24 * 7);
			updateToken(accessToken);
			String out = "<html><head><title>API登陆密钥</title></head><body><h3>登陆信息：<br/>(登陆即可再次见得该页面。)</h3><p>用户名:" + twitter.getScreenName() + "</p><p>密码:" + accessToken.getToken().substring(0, 6) + "</p><p><a href=\"/apilogout\">删除登陆记录</a></p></body>";
			resp.getOutputStream().write(out.getBytes("UTF-8"));
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.sendRedirect("/api");
		}
	}
	
	private void updateToken(AccessToken accessToken)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try
    	{
    		ApiUser user = pm.getObjectById(ApiUser.class, accessToken.getScreenName());
    		user.setToken(accessToken);
    	}
		catch(JDOObjectNotFoundException e)
		{
			ApiUser newuser = new ApiUser(accessToken);
			pm.makePersistent(newuser);
		}
    	finally
		{
    		pm.close();
		}
	}
	
	private void delToken(AccessToken accessToken)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try
    	{
    		ApiUser user = pm.getObjectById(ApiUser.class, accessToken.getScreenName());
    		pm.deletePersistent(user);
    	}
    	finally
		{
    		pm.close();
		}
	}
	
}
