package jtweet.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jtweet.Encrypt;
import jtweet.Exception.NotLoginException;
import jtweet.gae.GCache;

import twitter4j.Paging;
import twitter4j.SavedSearch;
import twitter4j.Status;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.repackaged.com.google.common.util.Base64;
import com.google.appengine.repackaged.com.google.common.util.Base64DecoderException;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings("serial")
public class JTweetServlet extends HttpServlet {
	protected Twitter twitter = new Twitter();
	protected Paging paging = new Paging(1, 20);
	private String username = null;
	private String passwd = null;
	public static final String ACCOUNT_COOKIE_NAME = "up";
	public static final int JSON_SUCCESS = 1;
	public static final int JSON_FAIL = 2;
	public static final int JSON_ERROR = 3;
	protected static Logger logger = Logger.getLogger(JTweetServlet.class
			.getName());
	protected boolean isLogin = false;

	public void init_twitter(String id, String passwd) {
		twitter.setUserId(id);
		twitter.setPassword(passwd);
		if (APIURL.useproxy) {
			twitter.setBaseURL(APIURL.url);
			twitter.setSearchBaseURL(APIURL.url);
		}
		twitter.setSource("JTweet");
		twitter.setClientURL("http://code.google.com/p/javatweet/");
		twitter.setClientVersion("r25");
		twitter.setUserAgent(twitter.getSource() + " " + twitter.getClientURL()
				+ " " + twitter.getClientVersion());
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
				logger.warning(e.getMessage());
				return false;
			} catch (UnsupportedEncodingException e) {
				logger.warning(e.getMessage());
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
						logger.warning(e.getMessage());
						e.printStackTrace();
					}
					session.setAttribute("username", username);
					return true;
				}
			}
			return false;
		}
	}

	/**
	 * 还原帐号 可以用来检验是否登录 如果登录可有可无 则需要捕捉并忽略NotLoginException
	 * 
	 * @param req
	 * @throws NotLoginException
	 * @throws UnsupportedEncodingException
	 * @throws Base64DecoderException
	 */
	protected void revertAccount(HttpServletRequest req)
			throws NotLoginException, UnsupportedEncodingException,
			Base64DecoderException {
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
						logger.warning(e.getMessage());
						e.printStackTrace();
					}
					session.setAttribute("username", username);
				} else {
					this.init_twitter("defaultclient", "tuitubie");
					throw new NotLoginException();
				}
			} else {
				this.init_twitter("defaultclient", "tuitubie");
				throw new NotLoginException();
			}
		}
		this.init_twitter(username, passwd);
		this.isLogin = true;
	}

	/**
	 * 不登录也可以进行的操作时使用
	 * 
	 * @param req
	 * @throws UnsupportedEncodingException
	 * @throws Base64DecoderException
	 */
	protected void revertAccountOrNot(HttpServletRequest req)
			throws UnsupportedEncodingException, Base64DecoderException {
		try {
			this.revertAccount(req);
		} catch (NotLoginException e) {
			this.isLogin = false;
		}
	}

	@SuppressWarnings("unchecked")
	protected List<Trend> getTrend() {
		try {
			List<Trend> trendlist = (List<Trend>) GCache.get("search_trend");
			if (trendlist == null) {
				Trends trends = this.twitter.getTrends();
				trendlist = Arrays.asList(trends.getTrends());
				GCache.put("search_trend", trendlist, 3600);
			}
			return trendlist;
		} catch (TwitterException e) {
			JTweetServlet.logger.warning(e.getMessage());
		}catch(Exception e){
			JTweetServlet.logger.warning(e.getMessage());
		}
		return null;
	}

	/**
	 * 未登录跳转到登录页面
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	protected void redirectLogin(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		JTweetServlet.logger.info("redirect login");
		resp.sendRedirect("/login");
	}

	/**
	 * 处理错误
	 * 
	 * @param req
	 * @param resp
	 * @param error
	 */
	protected void showError(HttpServletRequest req, HttpServletResponse resp,
			String error) {
		req.setAttribute("error", error);
		try {
			getServletContext().getRequestDispatcher("/template/error.jsp")
					.forward(req, resp);
		} catch (ServletException e) {
			JTweetServlet.logger.warning(e.getMessage());
		} catch (IOException e) {
			JTweetServlet.logger.warning(e.getMessage());
		}
	}

	/**
	 * 处理未知异常
	 * 
	 * @param req
	 * @param resp
	 * @param exception
	 */
	protected void showException(HttpServletRequest req,
			HttpServletResponse resp, Exception exception) {
		JTweetServlet.logger.warning(exception.getStackTrace()[0]
				.getClassName()
				+ "("
				+ exception.getStackTrace()[0].getLineNumber()
				+ "):"
				+ exception.getMessage());
		req.setAttribute("error", exception.getMessage());
		try {
			getServletContext().getRequestDispatcher("/template/error.jsp")
					.forward(req, resp);
		} catch (ServletException e) {
			JTweetServlet.logger.warning(e.getMessage());
		} catch (IOException e) {
			JTweetServlet.logger.warning(e.getMessage());
		}
	}

	public String getUsername() throws NotLoginException {
		if (username == null) {
			throw new NotLoginException();
		}
		return username;
	}

	public String getPasswd() throws NotLoginException {
		if (passwd == null) {
			throw new NotLoginException();
		}
		return passwd;
	}

	/**
	 * 使用memcache缓存帐号信息
	 * 
	 * @return User
	 * @throws TwitterException
	 * @throws NotLoginException
	 */
	protected User getCachedUser() throws TwitterException, NotLoginException {
		return this.showCachedUser(this.getUsername());
	}

	/**
	 * 
	 * @param reflesh
	 * @return
	 * @throws TwitterException
	 * @throws NotLoginException
	 */
	protected User getCachedUser(boolean reflesh) throws TwitterException, NotLoginException {
		return this.showCachedUser(this.getUsername(),reflesh);
	}
	/**
	 * 
	 * @param screenname
	 * @return
	 * @throws TwitterException
	 */
	protected User showCachedUser(String screenname) throws TwitterException {
		return this.showCachedUser(screenname, false);
	}
	
	/**
	 * 
	 * @param screenname
	 * @param reflesh
	 * @return
	 * @throws TwitterException
	 */
	protected User showCachedUser(String screenname, boolean reflesh)
			throws TwitterException {
		User user = null;
		if (!reflesh) {
			user = (User) GCache.get("user:" + screenname);
			if (null != user) {
				return user;
			}
		}
		user = twitter.showUser(screenname);
		GCache.put("user:" + screenname, user, 3600);
		return user;
	}
	/**
	 * 更新缓存
	 * @param user
	 */
	protected void updateCacheUser(User user){
		GCache.put("user:" + user.getScreenName(), user, 3600);
	}
	
	/**
	 * @param paging
	 * @return
	 */
	protected void cacheStatuses(List<Status> statuses){
		MemcacheService ms = GCache.getService();
		for(Status status:statuses){
			if(!ms.contains("status:"+status.getId()))
			GCache.put("status:"+status.getId(), status);
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws TwitterException
	 */
	protected Status showCacheStatus(long id) throws TwitterException {
		Status status = (Status) GCache.get("status:"+id);
		if(status==null){
			status = this.twitter.showStatus(id);
			if(status!=null){
				GCache.put("status:"+status.getId(), status);
			}
		}
		return status;
	}

	/**
	 * 
	 * @param screenname
	 * @param reflesh
	 * @return
	 * @throws TwitterException
	 */
	@SuppressWarnings("unchecked")
	protected List<Status> getCachedUserTimeline(String screenname,
			boolean reflesh) throws TwitterException {
		List<Status> timeline = null;
		if (!reflesh) {
			timeline = (List<Status>) GCache.get("user_timeline_"
					+ paging.getPage() + ":" + screenname);
			if (timeline != null) {
				return timeline;
			}
		}

		timeline = twitter.getUserTimeline(screenname, paging);
		GCache.put("user_timeline:" + screenname, timeline);
		return timeline;
	}

	/**
	 * 前50个followers
	 * 
	 * @return
	 * @throws TwitterException
	 * @throws NotLoginException
	 */
	@SuppressWarnings("unchecked")
	protected List<User> getCachedFollowers() throws TwitterException,
			NotLoginException {
		List<User> followers = (List<User>) GCache.get("followers_100:"
				+ this.getUsername());
		if (null != followers) {
			return followers;
		}
		followers = twitter.getFollowersStatuses(new Paging(1, 100));
		GCache.put("followers_100:" + this.getUsername(), followers, 3600);
		return followers;
	}

	/**
	 * 
	 * @return
	 * @throws TwitterException
	 * @throws NotLoginException
	 */
	@SuppressWarnings("unchecked")
	protected List<SavedSearch> getCachedSavedSearch() throws TwitterException,
			NotLoginException {
		List<SavedSearch> searches = (List<SavedSearch>) GCache
				.get("savedsearches:" + this.getUsername());
		if (searches == null) {
			searches = this.twitter.getSavedSearches();
			if (searches != null)
				GCache.put("savedsearches:" + this.getUsername(), searches,
						3600);
		}
		return searches;
	}

	protected void cleanCachedSavedSearch() throws TwitterException,
			NotLoginException {
		GCache.clean("savedsearches:" + this.getUsername());
	}

	/**
	 * 解析status为html字符串
	 * 
	 * @param status
	 * @return
	 * @throws TemplateException
	 * @throws IOException
	 * @throws NotLoginException
	 * @throws TwitterException
	 */
	protected String renderStatus(Status status) throws TemplateException,
			IOException, TwitterException, NotLoginException {
		List<Status> statuses = new ArrayList<Status>();
		statuses.add(status);
		return this.renderStatuses(statuses);
	}

	/**
	 * 解析status为html字符串
	 * 
	 * @param statuses
	 * @return
	 * @throws TemplateException
	 * @throws IOException
	 * @throws NotLoginException
	 * @throws TwitterException
	 */
	protected String renderStatuses(List<Status> statuses)
			throws TemplateException, IOException, TwitterException,
			NotLoginException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");
		root.put("user", this.getCachedUser());
		root.put("addclass", "newcome");
		root.put("status", statuses);
		Template t = config.getTemplate("status_element.ftl");
		StringWriter stringWriter = new StringWriter();
		BufferedWriter writer = new BufferedWriter(stringWriter);
		t.process(root, writer);
		stringWriter.flush();
		return stringWriter.toString();
	}
}
