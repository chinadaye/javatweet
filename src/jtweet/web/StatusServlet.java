package jtweet.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import jtweet.Exception.NotLoginException;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import twitter4j.Status;
import twitter4j.TwitterException;

@SuppressWarnings("serial")
public class StatusServlet extends JTweetServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		resp.setContentType("text/html; charset=UTF-8");
		String sid = req.getParameter("id");
		
			try {
				this.revertAccount(req);
				getStatus(sid, resp);
			} catch (NotLoginException e) {
				this.redirectLogin(req, resp);
			} catch (Exception e) {
				JTweetServlet.logger.warning(e.getMessage());
				this.showError(req, resp, e.getMessage());
			}
			
	}
	
	protected void getStatus(String sid, HttpServletResponse resp) throws IOException, TwitterException, TemplateException
	{
			long id = Long.parseLong(sid);
			HashMap<String,Object> root = new HashMap<String,Object>();
			freemarker.template.Configuration config=new freemarker.template.Configuration();
			config.setDirectoryForTemplateLoading(new File("template"));
			config.setDefaultEncoding("UTF-8");
			
			Status status = twitter.showStatus(id);
			root.put("status", status);
			
			Template t = config.getTemplate("status.ftl");
			t.process(root, resp.getWriter());
	}
}
