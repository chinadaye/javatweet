package jtweet.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import jtweet.Exception.NotLoginException;

import twitter4j.DirectMessage;
import twitter4j.Status;
import twitter4j.User;
import freemarker.template.Template;

@SuppressWarnings("serial")
public class UpdateServlet extends JTweetServlet {

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("application/x-javascript; charset=UTF-8");
		JSONObject json = new JSONObject();
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
				JTweetServlet.logger.info(e.getMessage());
			}
			root.put("user", this.getCachedUser());
			Template t = null;
			if (type.equalsIgnoreCase("home")) {
				List<Status> status = twitter.getFriendsTimeline(paging);
				root.put("status", status);
				json.put("code",1);//success
				json.put("count",status.size());
				if(status.size()>0){
					json.put("last_id", status.get(0).getId());
				}
				
				t = config.getTemplate("status_element.ftl");
			} else if(type.equalsIgnoreCase("profilecount")){
				User user = this.getCachedUser(true);
				if(user!=null){
					json.put("code", 1);
					json.put("followers", user.getFollowersCount());
					json.put("friends", user.getFriendsCount());
					json.put("statuses",user.getStatusesCount());
				}
			}/*else if (type.equalsIgnoreCase("reply")) {
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
			}*/

			if (t != null) {

				StringWriter stringWriter = new StringWriter();
				BufferedWriter writer = new BufferedWriter(stringWriter);
				t.process(root, writer);
				stringWriter.flush();
				String data = stringWriter.toString();
				json.put("data", data);
			}

		} catch (Exception e) {
			JTweetServlet.logger.warning(e.getMessage());
			json.put("code", 2);
			json.put("msg", e.getMessage());
		}
		
		try {
			resp.getWriter().write(json.toJSONString());
		} catch (IOException e) {
			JTweetServlet.logger.warning(e.getMessage());
		}
	}
}
