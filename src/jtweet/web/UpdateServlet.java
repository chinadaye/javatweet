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
public class UpdateServlet extends JTweetServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		String type = req.getParameter("type");
		String since = req.getParameter("since");

		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");
		root.put("addclass", "newcome");

		try {
			this.revertAccount(req);
			try {
				if (since != null) {
					long sinceid = Long.parseLong(since);
					paging.setSinceId(sinceid);
				}
			} catch (NumberFormatException e) {
				
			}
			root.put("user", this.getCachedUser());
			Template t = null;
			if (type.equalsIgnoreCase("home")) {
				List<Status> status = twitter.getFriendsTimeline(paging);
				root.put("status", status);
				t = config.getTemplate("status_element.ftl");
			} else if (type.equalsIgnoreCase("reply")) {
				List<Status> status = twitter.getMentions(paging);
				root.put("status", status);
				t = config.getTemplate("status_element.ftl");
			} else if (type.equalsIgnoreCase("message")) {
				List<DirectMessage> msg = twitter.getDirectMessages(paging);
				root.put("msg", msg);
				t = config.getTemplate("message_element.ftl");
			} else if (type.equalsIgnoreCase("public")) {
				List<Status> status = twitter.getPublicTimeline(paging);
				root.put("status", status);
				t = config.getTemplate("status_element.ftl");
			} else if (type.equalsIgnoreCase("rate")) {
				t = config.getTemplate("rate.ftl");
			}

			if (t != null)
				t.process(root, resp.getWriter());

		} catch (NotLoginException e) {
			// TODO 返回错误信息 当前不是使用json 前端不能判断
		} catch (Exception e) {
			JTweetServlet.logger.warning(e.getMessage());
			// TODO 返回错误信息 当前不是使用json 前端不能判断
		}
	}
}
