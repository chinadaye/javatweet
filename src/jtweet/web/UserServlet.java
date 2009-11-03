package jtweet.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import jtweet.Exception.NotLoginException;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings("serial")
public class UserServlet extends JTweetServlet {
	protected String uri;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		uri = req.getRequestURI();
		String uid = req.getParameter("id");
		String page = req.getParameter("page");
		String show = req.getParameter("show");

			try {
				if(uid==null){
					this.showError(req, resp, "用户id不能为空");
				}
				try{
					this.revertAccount(req);
				}catch(NotLoginException e){
					//查看用户信息时可以不登录
					this.isLogin = false;
				}
				if (page != null) {
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
				}
				if (show == null) {
					getUserTimeline(uid, resp);
				} else if (show.equalsIgnoreCase("favor")) {
					getUserFavor(uid, resp);
				} else {
					this.showError(req, resp, "page not found!");
				}
			} catch (Exception e) {
				JTweetServlet.logger.warning(e.getStackTrace()[0].getLineNumber()+":"+e.getMessage());
				this.showError(req, resp, e.getMessage());
			}

	}

	protected void getUserTimeline(String uid, HttpServletResponse resp)
			throws IOException,  TwitterException, TemplateException, NotLoginException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

			User user = twitter.showUser(uid);
			if(this.isLogin)
			root.put("user", this.getCachedUser());
			
			root.put("user_show", user);
			root.put("title", "时间线");
			root.put("uri", uri + "?id=" + uid);
			root.put("page", paging.getPage());

			if ((this.isLogin&&user.getScreenName().equalsIgnoreCase(getUsername()))
					|| (!user.isProtected())) {
				List<Status> status = twitter.getUserTimeline(uid, paging);
				root.put("status", status);
			}

			Template t = config.getTemplate("user.ftl");
			t.process(root, resp.getWriter());

	}

	protected void getUserFavor(String uid, HttpServletResponse resp)
			throws IOException, TwitterException, NotLoginException, TemplateException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

			User user = twitter.showUser(uid);
			if(isLogin)
			root.put("user", this.getCachedUser());
			
			root.put("user_show", user);
			root.put("title", "收藏");
			root.put("uri", uri + "?id=" + uid + "&show=favor");
			root.put("page", paging.getPage());

			if (!user.isProtected()) {
				List<Status> status = twitter.getFavorites(uid, paging
						.getPage());
				root.put("status", status);
			}

			Template t = config.getTemplate("user.ftl");
			t.process(root, resp.getWriter());

	}
}