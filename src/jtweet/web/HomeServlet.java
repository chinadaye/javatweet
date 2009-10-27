package jtweet.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.Exception.NotLoginException;

import twitter4j.DirectMessage;
import twitter4j.Status;
import twitter4j.TwitterException;
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
			this.revertAccount(req);
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
			if (action.equalsIgnoreCase("home")) {
				getHomeTimeline(resp);
			} else if (action.equalsIgnoreCase("reply")) {
				getReplyTimeline(resp);
			} else if (action.equalsIgnoreCase("favor")) {
				getFavorTimeline(resp);
			} else if (action.equalsIgnoreCase("message")) {
				getMsgTimeline(resp);
			} else if (action.equalsIgnoreCase("public")) {
				getPubTimeline(resp);
			} else {
				resp.sendRedirect("/home");
			}
		} catch (NotLoginException e) {
			JTweetServlet.log.info(e.getMessage());
			this.redirectLogin(req, resp);
		} catch(TwitterException e){
			// TODO twitter错误处理
		}catch (Exception e) {
			log.warning(e.getMessage());
			// TODO 未知异常处理 错误处理页
		}
	}

	protected void getHomeTimeline(HttpServletResponse resp)
			throws IOException, NotLoginException, TwitterException, TemplateException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

			List<Status> status = twitter.getFriendsTimeline(paging);
			root.put("user", this.getCachedUser());
			root.put("title", "首页");
			root.put("addjs", "/js/home.js");
			root.put("uri", uri);
			root.put("page", paging.getPage());
			root.put("status", status);
			Template t = config.getTemplate("home.ftl");
			t.process(root, resp.getWriter());

		
	}

	protected void getReplyTimeline(HttpServletResponse resp)
			throws IOException, NotLoginException, TwitterException, TemplateException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

			List<Status> status = twitter.getMentions(paging);
			root.put("user", this.getCachedUser());
			// root.put("rate", twitter.rateLimitStatus());
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
			// root.put("rate", twitter.rateLimitStatus());
			root.put("title", "公共页面");
			root.put("addjs", "/js/public.js");
			root.put("uri", uri);
			root.put("page", paging.getPage());
			root.put("status", status);
			Template t = config.getTemplate("home.ftl");
			t.process(root, resp.getWriter());

		
	}

	protected void getFavorTimeline(HttpServletResponse resp)
			throws IOException, NotLoginException, TwitterException, TemplateException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

			List<Status> status = twitter.getFavorites(paging.getPage());
			root.put("user", this.getCachedUser());
			// root.put("rate", twitter.rateLimitStatus());
			root.put("title", "收藏");
			root.put("addjs", "/js/favor.js");
			root.put("uri", uri);
			root.put("page", paging.getPage());
			root.put("status", status);
			Template t = config.getTemplate("home.ftl");
			t.process(root, resp.getWriter());

	}

	protected void getMsgTimeline(HttpServletResponse resp) throws IOException,
			NotLoginException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		try {
			List<DirectMessage> msg = twitter.getDirectMessages(paging);
			root.put("user", this.getCachedUser());
			// root.put("rate", twitter.rateLimitStatus());
			root.put("addjs", "/js/message.js");
			root.put("uri", uri);
			root.put("page", paging.getPage());
			root.put("msg", msg);
			Template t = config.getTemplate("message.ftl");
			t.process(root, resp.getWriter());

		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			resp.sendError(e.getStatusCode());
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}