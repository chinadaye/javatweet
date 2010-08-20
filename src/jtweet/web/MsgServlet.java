package jtweet.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.util.ShortURL;
import jtweet.util.Utils;
import jtweet.web.template.TexttoHTML;
import twitter4j.TwitterException;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings("serial")
public class MsgServlet extends BaseServlet {
	protected String uri;
	protected String text = new String();
	protected String action_result = new String();
	protected String send_to = new String();
	
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if(!isLogin(req))
		{
			redirectIndex(resp);
			return;
		}
		init_twitter(req, resp);
		
		String action = req.getParameter("action");
		String s_msg_id = req.getParameter("id");
		send_to = req.getParameter("u");
		if(Utils.isEmptyOrNull(action) || (Utils.isEmptyOrNull(s_msg_id) && Utils.isEmptyOrNull(send_to)))
		{
			text = "";
			send_to = "";
		}
		else if(action.equalsIgnoreCase("re"))
		{
		}
		else if(action.equalsIgnoreCase("del"))
		{
			send_to = "";
			int id = Integer.parseInt(s_msg_id);
			try {
				twitter.destroyDirectMessage(id);
				action_result = "删除成功。";
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				action_result = "删除失败，错误：" + e.getStatusCode();
			}
		}
		else
		{
			text = "";
			send_to = "";
		}
		getTimeline(req, resp);		
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		if(!isLogin(req))
		{
			redirectIndex(resp);
			return;
		}
		init_twitter(req, resp);
		
		send_to = req.getParameter("send_to");
		String content = req.getParameter("msg");
		if(Utils.isEmptyOrNull(content))
		{
			action_result = "请勿发送空消息";
		}
		else if(Utils.isEmptyOrNull(send_to))
		{
			action_result = "请指定接收者";
		}
		else
		{
			try {
				content = ShortURL.ShortURL_isgd(content);
				if(content.length() > 140)
				{
					content = content.substring(0, 138) + "…";
				}
				twitter.sendDirectMessage(send_to, content);
				action_result = "发送成功。";
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				action_result = "发送失败，错误：" + e.getStatusCode();
			}
		}
		send_to = "";
		text = "";
		getTimeline(req, resp);
	}
	
	public void getTimeline(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		uri = req.getRequestURI();
		resp.setContentType("text/html; charset=UTF-8");
		if(uri.equalsIgnoreCase("/inbox"))
		{
			getInbox(req, resp);
		}
		else if(uri.equalsIgnoreCase("/outbox"))
		{
			getOutbox(req, resp);
		}
		else
		{
			resp.sendRedirect("/home");
		}
	}
	
	public void getInbox(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");
		
		try {
			root.put("title", "收件箱");
			root.put("title_en", "Inbox");
			root.put("send_to", send_to);
			root.put("uri", uri);
			root.put("text", text);
			root.put("action_result", action_result);
			root.put("texttohtml", new TexttoHTML());
			root.put("login_user", login_user);
			root.put("msgs", twitter.getDirectMessages());
			String[] js = {"/js/msg.js"};
			root.put("js", js);
			Template t = config.getTemplate("msg.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			if(e.getStatusCode() == 401)
			{
				redirectIndex(resp);
			}
			else
			{
				resp.sendError(e.getStatusCode());
			}
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getOutbox(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");
		
		try {
			root.put("title", "发件箱");
			root.put("title_en", "Outbox");
			root.put("uri", uri);
			root.put("send_to", send_to);
			root.put("text", text);
			root.put("action_result", action_result);
			root.put("texttohtml", new TexttoHTML());
			root.put("login_user", login_user);
			root.put("msgs", twitter.getSentDirectMessages());
			String[] js = {"/js/msg.js"};
			root.put("js", js);
			Template t = config.getTemplate("msg.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			if(e.getStatusCode() == 401)
			{
				redirectIndex(resp);
			}
			else
			{
				resp.sendError(e.getStatusCode());
			}
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
