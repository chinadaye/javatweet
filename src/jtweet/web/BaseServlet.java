package jtweet.web;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jtweet.gae.Configuration;
import jtweet.gae.GCache;
import jtweet.util.Encrypt;
import jtweet.util.Utils;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;

@SuppressWarnings("serial")
public class BaseServlet extends HttpServlet {
	protected Twitter twitter = null;
	protected String browser = null;
	private AccessToken accessToken = null;
	public static final String ACCESS_COOKIE_NAME = "access";
	public String login_screenname;
	public User login_user;

	// 读取session和cookie，判断是否已经登陆
	@SuppressWarnings("deprecation")
	protected boolean isLogin(HttpServletRequest req) {
		HttpSession session = req.getSession(true);
		session.setMaxInactiveInterval(3600);
		String Token = (String) session.getAttribute("accessToken");
		String TokenSecret = (String) session.getAttribute("accessTokenSecret");

		if (!Utils.isEmptyOrNull(Token) && !Utils.isEmptyOrNull(TokenSecret)) {
			accessToken = new AccessToken(Token, TokenSecret);
			return true;
		} else {
			Cookie[] cookies = req.getCookies();
			if (cookies == null) {
				return false;
			}

			Cookie accountCookie = null;
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(BaseServlet.ACCESS_COOKIE_NAME)) {
					accountCookie = cookie;
					break;
				}
			}

			if (accountCookie == null || accountCookie.getValue() == null) {
				return false;
			}

			String[] accountString = Encrypt.decodeAccount(URLDecoder.decode(accountCookie.getValue()));

			if (accountString == null) {
				return false;
			}
			Token = accountString[0];
			TokenSecret = accountString[1];
			accessToken = new AccessToken(Token, TokenSecret);
			session.setAttribute("accessToken", Token);
			session.setAttribute("accessTokenSecret", TokenSecret);
		}

		return true;
	}

	// 清空cookie中的登陆信息，重定向到登陆页面
	protected void redirectIndex(HttpServletResponse resp) throws IOException {
		resp.sendRedirect("/logout");
	}

	// 初始化twitter
	public void init_twitter(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(Configuration.getConsumerKey(), Configuration.getConsumerSecret());
		twitter.setOAuthAccessToken(accessToken);

		try {
			login_screenname = twitter.getScreenName();
			login_user = (User) GCache.get("user:" + login_screenname + ":" + login_screenname);
			if (null == login_user) {
				login_user = twitter.verifyCredentials();
				GCache.put("user:" + login_screenname + ":" + login_screenname, login_user, 120);
			}
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			if (e.getStatusCode() == 401) {
				redirectIndex(resp);
			} else if (e.getStatusCode() > 0) {
				resp.sendError(e.getStatusCode());
			} else {
				resp.getOutputStream().println("Error Message: " + e.getMessage());
			}
		}
		String UA = req.getHeader("User-Agent");
		if (UA == null) {
			browser = "other";
		} else if (UA.contains("MSIE 6.0")) {
			browser = "ie6";
		} else if (UA.contains("MSIE 7.0")) {
			browser = "ie7";
		} else {
			browser = "other";
		}
	}

	public AccessToken getAccessToken() {
		return accessToken;
	}

}
