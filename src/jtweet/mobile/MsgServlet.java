package jtweet.mobile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.mobile.template.GetMiniPic;
import jtweet.mobile.template.TexttoHTML;
import jtweet.util.ShortURL;
import jtweet.util.Utils;
import twitter4j.Paging;
import twitter4j.TwitterException;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings("serial")
public class MsgServlet extends BaseServlet {
	protected String uri;
	protected String send_to = new String();
	protected Paging page = new Paging();
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if(!isLogin(req))
		{
			redirectIndex(resp);
			return;
		}
		init_twitter(req, resp);
		
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
		
		send_to = req.getParameter("to");
		String content = req.getParameter("message");
		if(Utils.isEmptyOrNull(content))
		{
			showError(resp, "请勿发送空消息");
			return;
		}
		else if(Utils.isEmptyOrNull(send_to))
		{
			showError(resp, "请指定接收者");
			return;
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
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				showError(resp, "发送失败，错误：" + e.getStatusCode());
				return;
			}
		}
		send_to = "";
		resp.sendRedirect("/m/outbox");
	}
	
	public void getTimeline(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		uri = req.getRequestURI();
		resp.setContentType("text/html; charset=UTF-8");
		String p = req.getParameter("p");
		page.setCount(20);
		page.setPage(1);
		if(!Utils.isEmptyOrNull(p))
		{
			page.setPage(Integer.parseInt(p));
		}
		if(uri.equalsIgnoreCase("/m/inbox"))
		{
			getInbox(req, resp);
		}
		else if(uri.equalsIgnoreCase("/m/outbox"))
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
		config.setDirectoryForTemplateLoading(new File("mobile"));
		config.setDefaultEncoding("UTF-8");
		
		try {
			root.put("title", "收件箱");
			root.put("GetMiniPic", new GetMiniPic());
			root.put("TexttoHTML", new TexttoHTML());
			root.put("login_user", login_user);
			root.put("msgs", twitter.getDirectMessages(page));
			root.put("page", page.getPage());
			Template t = config.getTemplate("msg.ftl");
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
	
	public void getOutbox(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("mobile"));
		config.setDefaultEncoding("UTF-8");
		
		try {
			root.put("title", "发件箱");
			root.put("GetMiniPic", new GetMiniPic());
			root.put("TexttoHTML", new TexttoHTML());
			root.put("login_user", login_user);
			root.put("msgs", twitter.getSentDirectMessages(page));
			root.put("page", page.getPage());
			Template t = config.getTemplate("msg.ftl");
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
