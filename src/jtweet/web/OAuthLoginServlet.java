package jtweet.web;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jtweet.gae.Configuration;
import jtweet.util.Utils;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.http.RequestToken;

@SuppressWarnings("serial")
public class OAuthLoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doLogin(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doLogin(req, resp);
	}

	private void doLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		String URI = req.getRequestURI();
		String stayin = req.getParameter("stayin");

		HttpSession session = req.getSession(true);
		session.setMaxInactiveInterval(3600);

		TwitterFactory twitterfactory = new TwitterFactory();
		Twitter twitter = twitterfactory.getOAuthAuthorizedInstance(Configuration.getConsumerKey(), Configuration.getConsumerSecret());

		String callbackURL = Utils.getBaseURL(req) + "/oauthcallback";
		if (!Utils.isEmptyOrNull(stayin) && stayin.equalsIgnoreCase("1")) {
			callbackURL += "?stayin=1";
		}
		try {
			RequestToken requestToken = twitter.getOAuthRequestToken(callbackURL);
			session.setAttribute("requestToken", requestToken);
			String authURL = requestToken.getAuthorizationURL();
			if (URI.equalsIgnoreCase("/oauthproxy")) {
				authURL = authURL.replaceFirst("http(s?)://api.twitter.com/oauth/authorize", "/oauth/authorize");
			}
			resp.sendRedirect(authURL);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}
}
