package jtweet.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jtweet.Encrypt;
import jtweet.Exception.NotLoginException;
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
	private String username = null;
	private String passwd = null;
	public static final String ACCOUNT_COOKIE_NAME = "up";
	protected static Logger log = Logger.getLogger(JTweetServlet.class.getName());
	
	public void init_twitter(String id, String passwd) {
		twitter = new Twitter(id, passwd);
		if (APIURL.useproxy) {
			twitter.setBaseURL(APIURL.url);
			twitter.setSearchBaseURL(APIURL.url);
		}
		twitter.setSource("JTweet");
		twitter.setClientURL("http://code.google.com/p/javatweet/");
		twitter.setClientVersion("r25");
		twitter.setUserAgent(twitter.getSource() + " " + twitter.getClientURL()
				+ " " + twitter.getClientVersion());
		paging = new Paging(1, 20);
	}

	@Deprecated
	protected boolean isLogin(HttpServletRequest req) {
		HttpSession session = req.getSession(true);
		session.setMaxInactiveInterval(3600);
		username = (String) session.getAttribute("username");
		passwd = (String) session.getAttribute("passwd");
		if (username != null && passwd != null) {
			try {
				passwd = new String(Base64.decode(passwd), "UTF-8");
				return true;
			} catch (Base64DecoderException e) {
				log.warning(e.getMessage());
				return false;
			} catch (UnsupportedEncodingException e) {
				log.warning(e.getMessage());
				return false;
			}
		} else {
			Cookie[] cookies = req.getCookies();
			Cookie accountCookie = null;
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals(
							JTweetServlet.ACCOUNT_COOKIE_NAME)) {
						accountCookie = cookie;
						break;
					}
				}
			}
			if (accountCookie != null) {
				String[] accountString = Encrypt.decodeAccount(accountCookie
						.getValue());
				if (accountString != null) {
					username = accountString[0];
					passwd = accountString[1];
					String passwd_en;
					try {
						passwd_en = Base64.encode(passwd.getBytes("UTF-8"));
						session.setAttribute("passwd", passwd_en);
					} catch (UnsupportedEncodingException e) {
						log.warning(e.getMessage());
						e.printStackTrace();
					}
					session.setAttribute("username", username);
					return true;
				}
			}
			return false;
		}
	}

	protected void revertAccount(HttpServletRequest req) throws NotLoginException, UnsupportedEncodingException, Base64DecoderException{
		HttpSession session = req.getSession(true);
		session.setMaxInactiveInterval(3600);
		username = (String) session.getAttribute("username");
		passwd = (String) session.getAttribute("passwd");
		if (username != null && passwd != null) {
				passwd = new String(Base64.decode(passwd), "UTF-8");
		} else {
			Cookie[] cookies = req.getCookies();
			Cookie accountCookie = null;
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals(
							JTweetServlet.ACCOUNT_COOKIE_NAME)) {
						accountCookie = cookie;
						break;
					}
				}
			}
			if (accountCookie != null) {
				String[] accountString = Encrypt.decodeAccount(accountCookie
						.getValue());
				if (accountString != null) {
					username = accountString[0];
					passwd = accountString[1];
					String passwd_en;
					try {
						passwd_en = Base64.encode(passwd.getBytes("UTF-8"));
						session.setAttribute("passwd", passwd_en);
					} catch (UnsupportedEncodingException e) {
						log.warning(e.getMessage());
						e.printStackTrace();
					}
					session.setAttribute("username", username);
				}
			}else{
				throw new NotLoginException();
			}
		}
		this.init_twitter(username, passwd);
	}
	protected void redirectLogin(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession(true);
		session.invalidate();
		resp.addCookie(new Cookie(JTweetServlet.ACCOUNT_COOKIE_NAME, null));
		resp.sendRedirect("/login");
	}

	public String getUsername() throws NotLoginException {
		if(username==null){
			throw new NotLoginException();
		}
		return username;
	}

	public String getPasswd() throws NotLoginException {
		if(passwd==null){
			throw new NotLoginException();
		}
		return passwd;
	}
	
	/**
	 * 使用memcache缓存帐号信息
	 * @return User
	 * @throws TwitterException 
	 * @throws NotLoginException 
	 */
	protected User getCachedUser() throws TwitterException, NotLoginException{
		User user = (User) GCache.get("user:"+this.getUsername());
		if(null!=user){
			return user;
		}
		user = twitter.verifyCredentials();
		GCache.put("user:"+this.getUsername(), user,3600*24);
		return user;
	}

}
