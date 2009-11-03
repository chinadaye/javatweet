package jtweet.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import jtweet.Exception.NotLoginException;

import twitter4j.TwitterException;
import twitter4j.User;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings("serial")
public class FollowServlet extends JTweetServlet {
	protected String uri;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		String uid = req.getParameter("id");
		uri = req.getRequestURI();
		String action = uri.substring(1);
		String page = req.getParameter("page");

		try {
			this.revertAccount(req);

			if (page == null) {
				paging.setPage(1);
			} else {
				try {
					int p = Integer.parseInt(page);
					if (p > 0)
						paging.setPage(p);
					else
						paging.setPage(1);
				} catch (NumberFormatException e) {
					paging.setPage(1);
				}
			}

			if (action.equalsIgnoreCase("follower")) {
				getFollower(uid, resp);
			} else if (action.equalsIgnoreCase("following")) {
				getFollowing(uid, resp);
			}
		} catch (NotLoginException e) {
			logger.info(e.getMessage());
			this.redirectLogin(req, resp);
		} catch (Exception e) {
			this.showError(req, resp, e.getMessage());
		}

	}

	protected void getFollower(String uid, HttpServletResponse resp)
			throws IOException, TwitterException, NotLoginException,
			TemplateException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		List<User> follower;
		root.put("title", "关注者");
		root.put("user", this.getCachedUser());
		if (uid == null) {
			root.put("user_show", this.getCachedUser());
			follower = twitter.getFollowersStatuses(paging);
		} else {
			root.put("user_show", twitter.showUser(uid));
			follower = twitter.getFollowersStatuses(uid, paging);
		}
		root.put("uri", uri);
		root.put("page", paging.getPage());
		root.put("follow", follower);

		Template t = config.getTemplate("follow.ftl");
		t.process(root, resp.getWriter());

	}

	protected void getFollowing(String uid, HttpServletResponse resp)
			throws IOException, TwitterException, NotLoginException,
			TemplateException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		List<User> following;
		root.put("title", "朋友");
		root.put("user", this.getCachedUser());
		// root.put("rate", twitter.rateLimitStatus());
		if (uid == null) {
			root.put("user_show", this.getCachedUser());
			following = twitter.getFriendsStatuses(paging);
		} else {
			root.put("user_show", twitter.showUser(uid));
			following = twitter.getFriendsStatuses(uid, paging);
		}
		root.put("uri", uri);
		root.put("page", paging.getPage());
		root.put("follow", following);

		Template t = config.getTemplate("follow.ftl");
		t.process(root, resp.getWriter());

	}
}
