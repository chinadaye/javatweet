package jtweet.web;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import jtweet.HotTopics;
import jtweet.Exception.NotLoginException;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.TwitterException;

@SuppressWarnings("serial")
public class SearchServlet extends JTweetServlet {
	protected String s;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		s = req.getParameter("s");
		try {
			
			this.revertAccount(req);
			int len = s.length();
			if (len > 0) {
				if(len>2){
					int r=0;
					String news ="";
					for(int i =0;i<len;i++){
						String sub = s.substring(i, i+1);
						if(sub.matches("[\u4e00-\u9fa5]")){
							r++;
						}
						if(r==2){
							r=0;
							news += sub+" ";
						}else{
							news += sub;
						}
					}
					s = news.replaceAll("\\s{2,}", " ");
				}
				getSearch(req, resp);
			} else {
				this.showError(req, resp, "搜索关键字不能为空");
				return;
			}
		} catch(NotLoginException e){
			s = URLEncoder.encode(s,"utf-8");
			resp.sendRedirect("/login?rdt="+URLEncoder.encode("/search?s="+s,"utf-8"));
		} catch (Exception e) {
			JTweetServlet.logger.warning(e.getMessage());
			this.showError(req, resp, "未知异常");
		}
	}

	protected void getSearch(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, NotLoginException, TwitterException,
			TemplateException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		String p = req.getParameter("page");
		int page = 1;
		if (p != null) {
			try {
				page = Integer.parseInt(p);
			} catch (NumberFormatException e) {
				page = 1;
			}
		}

		Query query = new Query(s);
		query.setPage(page);

		this.twitter.setSearchBaseURL(JTweetServlet.getRandomBaseUrl());
		QueryResult result = twitter.search(query);
		List<Tweet> tweets = result.getTweets();
		root.put("user", this.getCachedUser());
		root.put("searches",this.getCachedSavedSearch());
		root.put("trends",this.getTrend());
		root.put("rebang",HotTopics.getTopics());
		root.put("search", s);
		root.put("addjs", "/js/search.js");
		root.put("page", page);
		root.put("tweets", tweets);

		Template t = config.getTemplate("search.ftl");
		t.process(root, resp.getWriter());

	}
}
