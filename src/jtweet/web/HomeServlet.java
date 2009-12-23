package jtweet.web;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jtweet.Encrypt;
import jtweet.JtweetServlet;
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

	/**
	 * 初始化
	 */
	@Override
	public void init(ServletConfig cfg) throws ServletException {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		logger.info("init Account filter...");
		String key = cfg.getInitParameter("encrypt_key");
		try {
			Encrypt.init(key);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		}
	}

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
				try {
					this.revertAccount(req);
				} catch (NotLoginException e) {
					try {
						this.doLogin(req, resp, "/home");
					} catch (ServletException e1) {
						logger.warning(e1.getMessage());
						this.showException(req, resp, e1);
					}
					return;
				} catch (Exception e) {
					if (e.getMessage().contains("401")) {
						HttpSession session = req.getSession(true);
						session.invalidate();
						resp.addCookie(new Cookie(
								JTweetServlet.ACCOUNT_COOKIE_NAME, null));
						resp.sendRedirect("/login");
						return;
					} else {
						throw new Exception(e.getMessage());
					}
				}
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
				this.revertAccount(req);
				getPubTimeline(resp);
			} else if (action.startsWith("@")) {
				this.revertAccount(req);
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
			/*
			 * try { String rdt = req.getParameter("rdt");//redirect to
			 * if(rdt==null||rdt.trim().equals("")){ rdt = req.getRequestURI();
			 * } req.setAttribute("rdt", rdt); req.setAttribute("trends",
			 * this.getTrend());
			 * req.getRequestDispatcher("/template/login.jsp").forward(req,
			 * resp); return; } catch (ServletException e1) {
			 * JTweetServlet.logger.warning(e1.getMessage());
			 * this.showError(req, resp, e1.getMessage()); }
			 */
			try {
				this.doLogin(req, resp);
			} catch (ServletException e1) {
				logger.warning(e1.getMessage());
				this.showException(req, resp, e1);
			}
			return;
		} catch (Exception e) {
			logger.warning(e.getMessage());
			this.showException(req, resp, e);
		}
	}

	/**
	 * 进行登录
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doLogin(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		String rdt = req.getParameter("rdt");
		this.doLogin(req, resp, rdt);
	}

	/**
	 * 进行登录
	 * 
	 * @param req
	 * @param resp
	 * @param rdt
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doLogin(HttpServletRequest req, HttpServletResponse resp,
			String rdt) throws ServletException, IOException {
		if (rdt == null || rdt.trim().equals("")) {
			rdt = req.getRequestURI();
		}
		req.setAttribute("rdt", rdt);
		req.setAttribute("trends", this.getTrend());
		req.getRequestDispatcher("/template/login.jsp").forward(req, resp);
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

	/**
	 * 
	 * @param resp
	 * @throws IOException
	 * @throws NotLoginException
	 * @throws TwitterException
	 * @throws TemplateException
	 */
	protected void getHomeTimeline(HttpServletResponse resp)
			throws IOException, NotLoginException, TwitterException,
			TemplateException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		List<Status> statuses = this.twitter.getFriendsTimeline(paging);
		this.cacheStatuses(statuses);
		root.put("user", this.getCachedUser());
		root.put("searches", this.getCachedSavedSearch());
		root.put("title", "首页");
		root.put("addjs", "/js/home.js");
		root.put("uri", uri);
		root.put("page", paging.getPage());
		root.put("status", statuses);
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

		List<Status> statuses = twitter.getMentions(paging);
		this.cacheStatuses(statuses);
		root.put("user", this.getCachedUser());
		root.put("searches", this.getCachedSavedSearch());
		root.put("title", "回复");
		root.put("addjs", "/js/reply.js");
		root.put("uri", uri);
		root.put("page", paging.getPage());
		root.put("status", statuses);
		Template t = config.getTemplate("home.ftl");
		t.process(root, resp.getWriter());

	}

	protected void getPubTimeline(HttpServletResponse resp) throws IOException,
			NotLoginException, TwitterException, TemplateException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		this.twitter.setBaseURL(JTweetServlet.getRandomBaseUrl());
		List<Status> statuses = twitter.getPublicTimeline(paging);
		this.cacheStatuses(statuses);
		root.put("user", this.getCachedUser());
		root.put("searches", this.getCachedSavedSearch());
		root.put("title", "公共页面");
		root.put("addjs", "/js/public.js");
		root.put("uri", uri);
		root.put("page", paging.getPage());
		root.put("status", statuses);
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

		List<Status> statuses = twitter.getFavorites(paging.getPage());
		this.cacheStatuses(statuses);
		root.put("user", this.getCachedUser());
		root.put("searches", this.getCachedSavedSearch());
		root.put("title", "收藏");
		root.put("addjs", "/js/favor.js");
		root.put("uri", uri);
		root.put("page", paging.getPage());
		root.put("status", statuses);
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

		User user;
		user = twitter.showUser(uid);
		root.put("user", this.getCachedUser());

		if (this.twitter.existsBlock(uid)) {
			root.put("is_block", "1");
		}
		if (this.twitter.existsFriendship(this.getUsername(), uid)) {
			root.put("is_follow", "1");
		}
		if(this.twitter.existsFriendship(uid, this.getUsername())){
			root.put("is_follew", "1");
		}

		root.put("user_show", user);
		root.put("title", "时间线");
		root.put("uri", uri);
		root.put("page", paging.getPage());

		if (this.isLogin && uid.equalsIgnoreCase(getUsername())) {
			List<Status> statuses = this.getCachedUserTimeline(uid, true);
			this.cacheStatuses(statuses);
			root.put("status", statuses);
		} else if (!user.isProtected()) {
			List<Status> statuses = this.getCachedUserTimeline(uid, false);
			this.cacheStatuses(statuses);
			root.put("status", statuses);
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
		// if (isLogin)
		root.put("user", this.getCachedUser());

		root.put("user_show", user);
		root.put("title", "收藏");
		root.put("uri", uri);
		root.put("page", paging.getPage());

		if (!user.isProtected()) {
			List<Status> statuses = twitter.getFavorites(uid, paging.getPage());
			this.cacheStatuses(statuses);
			root.put("status", statuses);
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
		/**
		 * 限制count是显示的内容有异常 可能是官方api的问题 各客户端都存在 暂时去掉
		 */
		paging = new Paging(paging.getPage());
		
		List<User> follower;
		root.put("title", "关注者");
		root.put("user", this.showCachedUser(this.getUsername(), true));
		root.put("user_show", twitter.showUser(uid));
		follower = twitter.getFollowersStatuses(uid, paging);
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

		/**
		 * 限制count是显示的内容有异常 可能是官方api的问题 各客户端都存在 暂时去掉
		 */
		paging = new Paging(paging.getPage());
		
		List<User> following;
		root.put("title", "朋友");
		if (this.isLogin)
			root.put("user", this.showCachedUser(this.getUsername(), true));
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