package jtweet.mobile;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jtweet.gae.Configuration;
import jtweet.util.Encrypt;
import jtweet.util.Utils;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;

@SuppressWarnings("serial")
public class OAuthCallbackServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		String oauthVerifier = req.getParameter("oauth_verifier");
		
		if(Utils.isEmptyOrNull(oauthVerifier))
		{
			resp.sendRedirect("/m/logout");
			return;
		}
		
		HttpSession session = req.getSession(true);
		session.setMaxInactiveInterval(3600);
		
		RequestToken requestToken = (RequestToken) session.getAttribute("requestToken");
		session.removeAttribute("requestToken");
		session.invalidate();
		
		TwitterFactory twitterfactory = new TwitterFactory();
		Twitter twitter = twitterfactory.getOAuthAuthorizedInstance(Configuration.getConsumerKey(), Configuration.getConsumerSecret());
		
		try {
			AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, oauthVerifier);
			String accessCookie = Encrypt.encodeAccount(accessToken.getToken(), accessToken.getTokenSecret());
			@SuppressWarnings("deprecation")
			Cookie cookie = new Cookie(BaseServlet.ACCESS_COOKIE_NAME, URLEncoder.encode(accessCookie));
			cookie.setMaxAge(7 * 24 * 3600);
			cookie.setPath("/");
			resp.addCookie(cookie);
			resp.sendRedirect("/m/home");
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.sendRedirect("/m/logout");
		}
	}
	
}
