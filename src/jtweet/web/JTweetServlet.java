package jtweet.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jtweet.gae.GCache;

import twitter4j.Paging;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import com.google.appengine.repackaged.com.google.common.util.Base64;
import com.google.appengine.repackaged.com.google.common.util.Base64DecoderException;


@SuppressWarnings("serial")
public class JTweetServlet extends HttpServlet {
	protected Twitter twitter;
	protected Paging paging;
	protected String browser;
	private String username = null;
	private String passwd = null;
	
	public void init_twitter(String id, String passwd, HttpServletRequest req)
	{
		twitter = new Twitter(id, passwd);
		if(APIURL.useproxy){
			twitter.setBaseURL(APIURL.url);
		} 
		if(APIURL.usesearchproxy){
			twitter.setSearchBaseURL(APIURL.search_url);
		}
		twitter.setSource("JTweet");
		twitter.setClientURL("http://code.google.com/p/javatweet/");
		twitter.setClientVersion("r46");
		twitter.setUserAgent(twitter.getSource() + " " + twitter.getClientURL() + " " + twitter.getClientVersion());
		paging = new Paging(1,20);
		String UA = req.getHeader("User-Agent");
		if(UA == null)
		{
			browser = "other";
		}
		else if(UA.contains("MSIE 6.0"))
		{
			browser = "ie6";
		}
		else if(UA.contains("MSIE 7.0"))
		{
			browser = "ie7";
		}
		else
		{
			browser = "other";
		}
	}

	protected boolean isLogin(HttpServletRequest req)
	{
		HttpSession session = req.getSession(true);
		session.setMaxInactiveInterval(3600);
		username = (String)session.getAttribute("username");
		passwd = (String)session.getAttribute("passwd");
		if(username != null && passwd != null)
		{
			try {
				passwd = new String(Base64.decode(passwd), "UTF-8");
				return true;
			} catch (Base64DecoderException e) {
				// TODO Auto-generated catch block
				return false;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				return false;
			}
		}
		else return false;
	}
	
	protected void redirectLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{
		HttpSession session = req.getSession(true);
		session.invalidate();
		resp.sendRedirect("/login");
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getPasswd()
	{
		return passwd;
	}
	
	public User getTuser() throws TwitterException
	{
		User tuser = (User) GCache.get("user:" + getUsername());
		if(null == tuser)
		{
			tuser = twitter.verifyCredentials();
			GCache.put("user:" + getUsername(), tuser, 3600);
		}
		return tuser;
	}

}
