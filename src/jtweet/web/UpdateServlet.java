package jtweet.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.hack.StatusHelper;
import jtweet.hack.TweetHelper;
import jtweet.util.Utils;
import jtweet.web.template.TexttoHTML;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.TwitterException;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings("serial")
public class UpdateServlet extends BaseServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doAction(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doAction(req, resp);
	}

	public void doAction(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String type = req.getParameter("type");
		resp.setContentType("text/html; charset=UTF-8");
		if (!isLogin(req)) {
			return;
		}
		init_twitter(req, resp);

		if (type.equalsIgnoreCase("home")) {
			doUpdatehome(req, resp);
		} else if (type.equalsIgnoreCase("morehome")) {
			morehome(req, resp);
		} else if (type.equalsIgnoreCase("morereplise")) {
			morereplies(req, resp);
		} else if (type.equalsIgnoreCase("morefav")) {
			morefav(req, resp);
		} else if (type.equalsIgnoreCase("morertbyme")) {
			morertbyme(req, resp);
		} else if (type.equalsIgnoreCase("morerttome")) {
			morerttome(req, resp);
		} else if (type.equalsIgnoreCase("morepub")) {
			morepub(req, resp);
		} else if (type.equalsIgnoreCase("moresearch")) {
			moresearch(req, resp);
		} else if (type.equalsIgnoreCase("moreusertimeline")) {
			moreusertimeline(req, resp);
		} else if (type.equalsIgnoreCase("moreinbox")) {
			moreinbox(req, resp);
		} else if (type.equalsIgnoreCase("moreoutbox")) {
			moreoutbox(req, resp);
		}

	}

	public void doUpdatehome(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String since = req.getParameter("since");
		if (Utils.isEmptyOrNull(since)) {
			return;
		}

		long sinceid = Long.parseLong(since.replaceAll("\\D", ""));
		Paging p = new Paging();
		p.setSinceId(sinceid);

		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		try {
			root.put("uri", "/home");
			root.put("newcome", true);
			root.put("texttohtml", new TexttoHTML());
			root.put("login_user", login_user);
			root.put("status", StatusHelper.parseStatus(twitter.getHomeTimeline(p)));
			Template t = config.getTemplate("status_element.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			if (e.getStatusCode() > 0)
				resp.sendError(e.getStatusCode());
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}

	public void morehome(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String s_maxid = req.getParameter("maxid");
		String s_page = req.getParameter("page");
		if (Utils.isEmptyOrNull(s_maxid) || Utils.isEmptyOrNull(s_page)) {
			return;
		}

		long maxid = Long.parseLong(s_maxid.replaceAll("\\D", ""));
		int page = Integer.parseInt(s_page.replaceAll("\\D", ""));
		Paging p = new Paging();
		p.setMaxId(maxid);
		p.setPage(page);

		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		try {
			root.put("uri", "/home");
			root.put("texttohtml", new TexttoHTML());
			root.put("login_user", login_user);
			root.put("status", StatusHelper.parseStatus(twitter.getHomeTimeline(p)));
			Template t = config.getTemplate("status_element.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			if (e.getStatusCode() > 0)
				resp.sendError(e.getStatusCode());
		} catch (TemplateException e) {
			e.printStackTrace();
		}

	}

	public void morereplies(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String s_maxid = req.getParameter("maxid");
		String s_page = req.getParameter("page");
		if (Utils.isEmptyOrNull(s_maxid) || Utils.isEmptyOrNull(s_page)) {
			return;
		}

		long maxid = Long.parseLong(s_maxid.replaceAll("\\D", ""));
		int page = Integer.parseInt(s_page.replaceAll("\\D", ""));
		Paging p = new Paging();
		p.setMaxId(maxid);
		p.setPage(page);

		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		try {
			root.put("uri", "/replies");
			root.put("texttohtml", new TexttoHTML());
			root.put("login_user", login_user);
			root.put("status", StatusHelper.parseStatus(twitter.getMentions(p)));
			Template t = config.getTemplate("status_element.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			if (e.getStatusCode() > 0)
				resp.sendError(e.getStatusCode());
		} catch (TemplateException e) {
			e.printStackTrace();
		}

	}

	public void morefav(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String s_page = req.getParameter("page");
		String u = req.getParameter("u");
		if (Utils.isEmptyOrNull(s_page)) {
			return;
		}

		int page = Integer.parseInt(s_page.replaceAll("\\D", ""));

		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		try {
			if (Utils.isEmptyOrNull(u)) {
				u = twitter.getScreenName();
				root.put("uri", "/favorites");
			} else {
				root.put("uri", "/home");
			}
			root.put("texttohtml", new TexttoHTML());
			root.put("login_user", login_user);
			root.put("status", StatusHelper.parseStatus(twitter.getFavorites(u, page)));
			Template t = config.getTemplate("status_element.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			if (e.getStatusCode() > 0)
				resp.sendError(e.getStatusCode());
		} catch (TemplateException e) {
			e.printStackTrace();
		}

	}

	public void morertbyme(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String s_maxid = req.getParameter("maxid");
		String s_page = req.getParameter("page");
		if (Utils.isEmptyOrNull(s_maxid) || Utils.isEmptyOrNull(s_page)) {
			return;
		}

		long maxid = Long.parseLong(s_maxid.replaceAll("\\D", ""));
		int page = Integer.parseInt(s_page.replaceAll("\\D", ""));
		Paging p = new Paging();
		p.setMaxId(maxid);
		p.setPage(page);

		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		try {
			root.put("uri", "/retweets_by_me");
			root.put("texttohtml", new TexttoHTML());
			root.put("login_user", login_user);
			root.put("status", StatusHelper.parseStatus(twitter.getRetweetedByMe(p)));
			Template t = config.getTemplate("status_element.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			if (e.getStatusCode() > 0)
				resp.sendError(e.getStatusCode());
		} catch (TemplateException e) {
			e.printStackTrace();
		}

	}

	public void morerttome(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String s_maxid = req.getParameter("maxid");
		String s_page = req.getParameter("page");
		if (Utils.isEmptyOrNull(s_maxid) || Utils.isEmptyOrNull(s_page)) {
			return;
		}

		long maxid = Long.parseLong(s_maxid.replaceAll("\\D", ""));
		int page = Integer.parseInt(s_page.replaceAll("\\D", ""));
		Paging p = new Paging();
		p.setMaxId(maxid);
		p.setPage(page);

		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		try {
			root.put("uri", "/retweets_to_me");
			root.put("texttohtml", new TexttoHTML());
			root.put("login_user", login_user);
			root.put("status", StatusHelper.parseStatus(twitter.getRetweetedToMe(p)));
			Template t = config.getTemplate("status_element.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			if (e.getStatusCode() > 0)
				resp.sendError(e.getStatusCode());
		} catch (TemplateException e) {
			e.printStackTrace();
		}

	}

	public void morepub(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		try {
			root.put("uri", "/public");
			root.put("texttohtml", new TexttoHTML());
			root.put("login_user", login_user);
			root.put("status", StatusHelper.parseStatus(twitter.getPublicTimeline()));
			Template t = config.getTemplate("status_element.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			if (e.getStatusCode() > 0)
				resp.sendError(e.getStatusCode());
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}

	public void moresearch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String s_page = req.getParameter("page");
		String s = req.getParameter("s");
		if (Utils.isEmptyOrNull(s) || Utils.isEmptyOrNull(s_page)) {
			return;
		}

		int page = Integer.parseInt(s_page.replaceAll("\\D", ""));
		Query q = new Query(s);
		q.page(page);

		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		try {
			root.put("texttohtml", new TexttoHTML());
			root.put("login_user", login_user);
			root.put("tweets", TweetHelper.parseTweets(twitter.search(q).getTweets()));
			Template t = config.getTemplate("tweet_element.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			if (e.getStatusCode() > 0)
				resp.sendError(e.getStatusCode());
		} catch (TemplateException e) {
			e.printStackTrace();
		}

	}

	public void moreusertimeline(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String s_maxid = req.getParameter("maxid");
		String s_page = req.getParameter("page");
		String u = req.getParameter("u");
		if (Utils.isEmptyOrNull(s_maxid) || Utils.isEmptyOrNull(s_page) || Utils.isEmptyOrNull(u)) {
			return;
		}

		long maxid = Long.parseLong(s_maxid.replaceAll("\\D", ""));
		int page = Integer.parseInt(s_page.replaceAll("\\D", ""));
		Paging p = new Paging();
		p.setMaxId(maxid);
		p.setPage(page);

		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		try {
			root.put("uri", "/home");
			root.put("texttohtml", new TexttoHTML());
			root.put("login_user", login_user);
			root.put("status", StatusHelper.parseStatus(twitter.getUserTimeline(u, p)));
			Template t = config.getTemplate("status_element.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			if (e.getStatusCode() > 0)
				resp.sendError(e.getStatusCode());
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}

	public void moreinbox(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String s_maxid = req.getParameter("maxid");
		String s_page = req.getParameter("page");
		if (Utils.isEmptyOrNull(s_maxid) || Utils.isEmptyOrNull(s_page)) {
			return;
		}

		long maxid = Long.parseLong(s_maxid.replaceAll("\\D", ""));
		int page = Integer.parseInt(s_page.replaceAll("\\D", ""));
		Paging p = new Paging();
		p.setMaxId(maxid);
		p.setPage(page);

		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		try {
			root.put("uri", "/inbox");
			root.put("texttohtml", new TexttoHTML());
			root.put("login_user", login_user);
			root.put("msgs", twitter.getDirectMessages(p));
			Template t = config.getTemplate("msg_element.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			if (e.getStatusCode() > 0)
				resp.sendError(e.getStatusCode());
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}

	public void moreoutbox(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String s_maxid = req.getParameter("maxid");
		String s_page = req.getParameter("page");
		if (Utils.isEmptyOrNull(s_maxid) || Utils.isEmptyOrNull(s_page)) {
			return;
		}

		long maxid = Long.parseLong(s_maxid.replaceAll("\\D", ""));
		int page = Integer.parseInt(s_page.replaceAll("\\D", ""));
		Paging p = new Paging();
		p.setMaxId(maxid);
		p.setPage(page);

		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		try {
			root.put("uri", "/outbox");
			root.put("texttohtml", new TexttoHTML());
			root.put("login_user", login_user);
			root.put("msgs", twitter.getSentDirectMessages(p));
			Template t = config.getTemplate("msg_element.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			if (e.getStatusCode() > 0)
				resp.sendError(e.getStatusCode());
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
}
