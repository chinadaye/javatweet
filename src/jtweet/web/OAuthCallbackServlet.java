package jtweet.web;

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
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

@SuppressWarnings("serial")
public class OAuthCallbackServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		String oauthVerifier = req.getParameter("oauth_verifier");

		if (Utils.isEmptyOrNull(oauthVerifier)) {
			resp.sendRedirect("/logout");
			return;
		}

		HttpSession session = req.getSession(true);
		session.setMaxInactiveInterval(3600);

		String stayin = req.getParameter("stayin");
		RequestToken requestToken = (RequestToken) session.getAttribute("requestToken");
		session.removeAttribute("requestToken");

		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(Configuration.getConsumerKey(), Configuration.getConsumerSecret());

		try {
			AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, oauthVerifier);
			session.setAttribute("accessToken", accessToken.getToken());
			session.setAttribute("accessTokenSecret", accessToken.getTokenSecret());
			if (!Utils.isEmptyOrNull(stayin) && stayin.equalsIgnoreCase("1")) {
				String accessCookie = Encrypt.encodeAccount(accessToken.getToken(), accessToken.getTokenSecret());
				@SuppressWarnings("deprecation")
				Cookie cookie = new Cookie(BaseServlet.ACCESS_COOKIE_NAME, URLEncoder.encode(accessCookie));
				cookie.setMaxAge(7 * 24 * 3600);
				cookie.setPath("/");
				resp.addCookie(cookie);
			}
			resp.sendRedirect("/home");
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.sendRedirect("/logout");
		}
	}

}
