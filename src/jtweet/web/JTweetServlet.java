package jtweet.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import twitter4j.Paging;
import twitter4j.Twitter;

import com.google.appengine.repackaged.com.google.common.util.Base64;
import com.google.appengine.repackaged.com.google.common.util.Base64DecoderException;


@SuppressWarnings("serial")
public class JTweetServlet extends HttpServlet {
	protected Twitter twitter;
	protected Paging paging;
	private String username = null;
	private String passwd = null;
	
	public void init_twitter(String id, String passwd)
	{
		if(APIURL.useproxy){
			twitter = new Twitter(id, passwd, APIURL.url);
			twitter.setSearchBaseURL(APIURL.url);
		}
		else twitter = new Twitter(id, passwd);
		twitter.setSource("JTweet");
		twitter.setClientURL("http://jtweet.yulei666.com");
		twitter.setClientVersion("0.0001");
		twitter.setUserAgent(twitter.getSource() + " " + twitter.getClientURL() + " " + twitter.getClientVersion());
		paging = new Paging(1,20);
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

}
