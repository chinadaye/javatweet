package jtweet.mobile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.Query;
import twitter4j.TwitterException;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import jtweet.mobile.template.GetMiniPic;
import jtweet.util.Utils;
import jtweet.web.BaseServlet;
import jtweet.web.template.TexttoHTML;

@SuppressWarnings("serial")
public class SearchServlet extends BaseServlet {
	
	protected int page = 1;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		getSearch(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		getSearch(req, resp);
	}
	
	public void getSearch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		
		if(!isLogin(req))
		{
			redirectIndex(resp);
			return;
		}
		init_twitter(req, resp);
		
		String s = req.getParameter("s");
		String p = req.getParameter("p");
		if(!Utils.isEmptyOrNull(p))
		{
			page = Integer.parseInt(p);
		}
		 
		
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("mobile"));
		config.setDefaultEncoding("UTF-8");
		
		try {
			root.put("title", "搜索");
			root.put("login_user", login_user);
			if(!Utils.isEmptyOrNull(s))
			{
				root.put("GetMiniPic", new GetMiniPic());
				root.put("TexttoHTML", new TexttoHTML());
				Query q = new Query(s);
				q.page(page);
				root.put("s", s);
				root.put("page", page);
				root.put("tweets", twitter.search(q).getTweets());
			}
			Template t = config.getTemplate("search.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			if(e.getStatusCode() == 401)
			{
				redirectIndex(resp);
			}
			else if(e.getStatusCode() > 0)
			{
				resp.sendError(e.getStatusCode());
			}
			else
			{
				resp.getOutputStream().println("Error Message: " + e.getMessage());
			}
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
