package jtweet.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.Exception.NotLoginException;

import twitter4j.DirectMessage;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings("serial")
public class HomeServlet extends JTweetServlet {

	protected String uri;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		uri = req.getRequestURI();
		String action = uri.substring(1);
		String page = req.getParameter("page");

		try {

			try {
				int p = Integer.parseInt(page);
				if (p > 0)
					paging.setPage(p);
				else {
					paging.setPage(1);
				}
			} catch (NumberFormatException e) {
				paging.setPage(1);
			}
			if (action.equalsIgnoreCase("home") || action.equalsIgnoreCase("")) {
				this.revertAccount(req);
				getHomeTimeline(resp);
			} else if (action.equalsIgnoreCase("reply")) {
				this.revertAccount(req);
				getReplyTimeline(resp);
			} else if (action.equalsIgnoreCase("favor")) {
				this.revertAccount(req);
				getFavorTimeline(resp);
			} else if (action.equalsIgnoreCase("follower")) {
				this.revertAccount(req);
				this.getFollower(this.getUsername(), resp);
			} else if (action.equalsIgnoreCase("following")) {
				this.revertAccount(req);
				this.getFollowing(this.getUsername(), resp);
			} else if (action.equalsIgnoreCase("inbox")) {
				this.revertAccount(req);
				getMsgInbox(resp);
			} else if (action.equalsIgnoreCase("sent")) {
				this.revertAccount(req);
				getMsgSent(resp);
			} else if (action.equalsIgnoreCase("public")) {
				this.revertAccountOrNot(req);
				getPubTimeline(resp);
			} else if (action.startsWith("@")) {
				this.revertAccountOrNot(req);
				String[] uriParts = action.substring(1).split("/");
				if (uriParts.length == 1) {
					this.getUserTimeline(action.substring(1), resp);
				} else if (uriParts.length == 2) {
					if (uriParts[1].equalsIgnoreCase("favor")) {
						this.getUserFavor(uriParts[0], resp);
					} else if (uriParts[1].equalsIgnoreCase("follower")) {
						this.getFollower(uriParts[0], resp);
					} else if (uriParts[1].equalsIgnoreCase("following")) {
						this.getFollowing(uriParts[0], resp);
					}
				}

			} else {
				this
						.showError(req, resp, "该页面不存在（" + req.getRequestURI()
								+ "）");
			}
		} catch (NotLoginException e) {
			// 进行登录
			try {
				req.setAttribute("trends", this.getTrend());
				req.getRequestDispatcher("/template/login.jsp").forward(req,
						resp);
			} catch (ServletException e1) {
				JTweetServlet.logger.warning(e1.getMessage());
				this.showError(req, resp, e1.getMessage());
			}
		} catch (Exception e) {
			logger.warning(e.getMessage());
			this.showException(req, resp, e);
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		uri = req.getRequestURI();
		String action = uri.substring(1);

		try {
			this.revertAccount(req);
			if (action.equalsIgnoreCase("send")) {
				String msg = req.getParameter("tweet_msg");
				if (msg == null || msg.equals("")) {
					throw new Exception("私信内容不能为空");
				}
				String userid = req.getParameter("user_id");
				if (userid == null || userid.equals("")) {
					throw new Exception("私信发送对象不能为空");
				}
				this.twitter.sendDirectMessage(userid, msg);
				this.getMsgSent(resp);
			} else {
				this
						.showError(req, resp, "该页面不存在（" + req.getRequestURI()
								+ "）");
			}
		} catch (NotLoginException e) {
			this.redirectLogin(req, resp);
		} catch (Exception e) {
			logger.warning(e.getMessage());
			this.showError(req, resp, e.getMessage());
		}
	}

	protected void getHomeTimeline(HttpServletResponse resp)
			throws IOException, NotLoginException, TwitterException,
			TemplateException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		List<Status> status = twitter.getFriendsTimeline(paging);
		root.put("user", this.getCachedUser());
		root.put("searches", this.getCachedSavedSearch());
		root.put("title", "首页");
		root.put("addjs", "/js/home.js");
		root.put("uri", uri);
		root.put("page", paging.getPage());
		root.put("status", status);
		root.put("needreply", true);
		Template t = config.getTemplate("home.ftl");
		t.process(root, resp.getWriter());

	}

	protected void getReplyTimeline(HttpServletResponse resp)
			throws IOException, NotLoginException, TwitterException,
			TemplateException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		List<Status> status = twitter.getMentions(paging);
		root.put("user", this.getCachedUser());
		root.put("searches", this.getCachedSavedSearch());
		root.put("title", "回复");
		root.put("addjs", "/js/reply.js");
		root.put("uri", uri);
		root.put("page", paging.getPage());
		root.put("status", status);
		Template t = config.getTemplate("home.ftl");
		t.process(root, resp.getWriter());

	}

	protected void getPubTimeline(HttpServletResponse resp) throws IOException,
			NotLoginException, TwitterException, TemplateException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		List<Status> status = twitter.getPublicTimeline(paging);
		root.put("user", this.getCachedUser());
		root.put("searches", this.getCachedSavedSearch());
		root.put("title", "公共页面");
		root.put("addjs", "/js/public.js");
		root.put("uri", uri);
		root.put("page", paging.getPage());
		root.put("status", status);
		Template t = config.getTemplate("home.ftl");
		t.process(root, resp.getWriter());

	}

	protected void getFavorTimeline(HttpServletResponse resp)
			throws IOException, NotLoginException, TwitterException,
			TemplateException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		List<Status> status = twitter.getFavorites(paging.getPage());
		root.put("user", this.getCachedUser());
		root.put("searches", this.getCachedSavedSearch());
		root.put("title", "收藏");
		root.put("addjs", "/js/favor.js");
		root.put("uri", uri);
		root.put("page", paging.getPage());
		root.put("status", status);
		Template t = config.getTemplate("home.ftl");
		t.process(root, resp.getWriter());

	}

	@Deprecated
	protected void getMsgTimeline(HttpServletResponse resp) throws IOException,
			NotLoginException, TwitterException, TemplateException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		List<DirectMessage> msg = twitter.getDirectMessages(paging);
		root.put("user", this.getCachedUser());
		root.put("searches", this.getCachedSavedSearch());
		root.put("title", "私信收件箱");
		root.put("addjs", "/js/message.js");
		root.put("uri", uri);
		root.put("page", paging.getPage());
		root.put("msg", msg);
		Template t = config.getTemplate("message.ftl");
		t.process(root, resp.getWriter());

	}

	protected void getMsgInbox(HttpServletResponse resp) throws IOException,
			NotLoginException, TwitterException, TemplateException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		List<DirectMessage> msg = twitter.getDirectMessages(paging);
		root.put("user", this.getCachedUser());
		root.put("searches", this.getCachedSavedSearch());
		root.put("title", "私信收件箱");
		root.put("uri", uri);
		root.put("page", paging.getPage());
		root.put("msg", msg);
		Template t = config.getTemplate("inbox.ftl");
		t.process(root, resp.getWriter());

	}

	protected void getMsgSent(HttpServletResponse resp) throws IOException,
			NotLoginException, TwitterException, TemplateException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		List<DirectMessage> msg = twitter.getSentDirectMessages(paging);
		root.put("user", this.getCachedUser());
		root.put("searches", this.getCachedSavedSearch());
		root.put("title", "私信发件箱");
		root.put("uri", uri);
		root.put("page", paging.getPage());
		root.put("msg", msg);
		Template t = config.getTemplate("sent.ftl");

		t.process(root, resp.getWriter());

	}

	protected void getUserTimeline(String uid, HttpServletResponse resp)
			throws IOException, TwitterException, TemplateException,
			NotLoginException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		User user ;
		if (this.isLogin){
			user = twitter.showUser(uid);
			root.put("user", this.getCachedUser());
		}else{
			user = this.showCachedUser(uid);
		}
			

		root.put("user_show", user);
		root.put("title", "时间线");
		root.put("uri", uri );
		root.put("page", paging.getPage());

		if(this.isLogin&&uid.equalsIgnoreCase(getUsername())){
			List<Status> status = this.getCachedUserTimeline(uid,true);
			root.put("status", status);
		}else if(!user.isProtected()){
			List<Status> status = this.getCachedUserTimeline(uid,false);
			root.put("status", status);
		}

		Template t = config.getTemplate("user.ftl");
		t.process(root, resp.getWriter());

	}

	protected void getUserFavor(String uid, HttpServletResponse resp)
			throws IOException, TwitterException, NotLoginException,
			TemplateException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		User user = twitter.showUser(uid);
		if (isLogin)
			root.put("user", this.getCachedUser());

		root.put("user_show", user);
		root.put("title", "收藏");
		root.put("uri", uri + "?id=" + uid + "&show=favor");
		root.put("page", paging.getPage());

		if (!user.isProtected()) {
			List<Status> status = twitter.getFavorites(uid, paging.getPage());
			root.put("status", status);
		}

		Template t = config.getTemplate("user.ftl");
		t.process(root, resp.getWriter());

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
		if (this.isLogin)
			root.put("user", this.getCachedUser());
//		if (uid == null) {
//			root.put("user_show", this.getCachedUser());
//			follower = twitter.getFollowersStatuses(paging);
//		} else {
			root.put("user_show", twitter.showUser(uid));
			follower = twitter.getFollowersStatuses(uid, paging);
//		}
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
		if (this.isLogin)
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