package jtweet.mobile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.gae.GCache;
import jtweet.util.Utils;
import twitter4j.Status;
import twitter4j.TwitterException;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings("serial")
public class ActionServlet extends BaseServlet {
	protected String referer = new String();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doAction(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doAction(req, resp);
	}

	public void doAction(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String uri = req.getRequestURI();
		resp.setContentType("text/html; charset=UTF-8");
		referer = req.getHeader("Referer");
		if(Utils.isEmptyOrNull(referer))
		{
			referer = "/m/home";
		}
		if(!isLogin(req))
		{
			redirectIndex(resp);
			return;
		}
		init_twitter(req, resp);
		
		if(uri.equalsIgnoreCase("/m/re"))
		{
			update(req, resp);
		}
		else if(uri.equalsIgnoreCase("/m/rt"))
		{
			retweet(req, resp);
		}
		else if(uri.equalsIgnoreCase("/m/rt_offical"))
		{
			rt_offical(req, resp);
		}
		else if(uri.equalsIgnoreCase("/m/fav"))
		{
			fav(req, resp);
		}
		else if(uri.equalsIgnoreCase("/m/unfav"))
		{
			unfav(req, resp);
		}
		else if(uri.equalsIgnoreCase("/m/delstatus"))
		{
			delstatus(req, resp);
		}
		else if(uri.equalsIgnoreCase("/m/fo"))
		{
			fo(req, resp);
		}
		else if(uri.equalsIgnoreCase("/m/unfo"))
		{
			unfo(req, resp);
		}
		else if(uri.equalsIgnoreCase("/m/b"))
		{
			b(req, resp);
		}
		else if(uri.equalsIgnoreCase("/m/unb"))
		{
			unb(req, resp);
		}
		else if(uri.equalsIgnoreCase("/m/dm"))
		{
			dm(req, resp);
		}
		else if(uri.equalsIgnoreCase("/m/delmsg"))
		{
			delmsg(req, resp);
		}
		else
		{
			resp.sendRedirect("/m/home");
		}
	
	}
	
	public void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("mobile"));
		config.setDefaultEncoding("UTF-8");
		String s_id = req.getParameter("id");
		if(s_id == null) s_id = "";
		long id;
		try{
			id = Long.parseLong(s_id);
		} catch(NumberFormatException e){
			id = 0;
		}
		String reply_to = req.getParameter("u");
		String text = new String();
		if(!Utils.isEmptyOrNull(reply_to))
		{
			text = "@" + reply_to + " ";
		}
		
		try {
			root.put("title", "回复");
			root.put("login_user", login_user);
			root.put("reply_to_id", s_id);
			root.put("text", text);
			if(id > 0)
			{
				root.put("reply_to_status", twitter.showStatus(id));
			}
			Template t = config.getTemplate("status_input.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
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
	
	public void retweet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("mobile"));
		config.setDefaultEncoding("UTF-8");
		String s_id = req.getParameter("id");
		if(s_id == null) s_id = "";
		long id;
		try{
			id = Long.parseLong(s_id);
		} catch(NumberFormatException e){
			showError(resp, "id格式错误");
			return;
		}
		String text = new String();
		try {
			root.put("title", "ReTweet");
			root.put("login_user", login_user);
			root.put("reply_to_id", s_id);
			Status status = twitter.showStatus(id);
			text ="RT @" + status.getUser().getScreenName() + " " + status.getText();
			root.put("text", text);
			if(!status.getUser().isProtected())
			{
				root.put("rt_id", id);
			}
			Template t = config.getTemplate("status_input.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
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
	
	public void rt_offical(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String s_id = req.getParameter("id");
		if(s_id == null) s_id = "";
		long id;
		try{
			id = Long.parseLong(s_id);
		} catch(NumberFormatException e){
			showError(resp, "id格式错误");
			return;
		}
		try {
			twitter.retweetStatus(id);
			resp.sendRedirect("/m/home");
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
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
		}
	}
	
	public void fav(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String s_id = req.getParameter("id");
		if(s_id == null) s_id = "";
		long id;
		try{
			id = Long.parseLong(s_id);
		} catch(NumberFormatException e){
			showError(resp, "id格式错误");
			return;
		}
		try {
			twitter.createFavorite(id);
			resp.sendRedirect(referer);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
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
		}
	}
	
	public void unfav(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String s_id = req.getParameter("id");
		if(s_id == null) s_id = "";
		long id;
		try{
			id = Long.parseLong(s_id);
		} catch(NumberFormatException e){
			showError(resp, "id格式错误");
			return;
		}
		try {
			twitter.destroyFavorite(id);
			resp.sendRedirect(referer);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
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
		}
	}
	
	public void delstatus(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String s_id = req.getParameter("id");
		if(s_id == null) s_id = "";
		long id;
		try{
			id = Long.parseLong(s_id);
		} catch(NumberFormatException e){
			showError(resp, "id格式错误");
			return;
		}
		String yes = req.getParameter("yes");
		if(Utils.isEmptyOrNull(yes))
		{
			HashMap<String, Object> root = new HashMap<String, Object>();
			freemarker.template.Configuration config = new freemarker.template.Configuration();
			config.setDirectoryForTemplateLoading(new File("mobile"));
			config.setDefaultEncoding("UTF-8");
			
			try {;
				root.put("text", "你真的要删除id为" + id + "的推吗？");
				Template t = config.getTemplate("confirm.ftl");
				t.process(root, resp.getWriter());
			} catch (TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			try {
				twitter.destroyStatus(id);
				resp.sendRedirect("/m/home");
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
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
			}
		}
	}
	
	public void fo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String id = req.getParameter("u");
		if(Utils.isEmptyOrNull(id))
		{
			showError(resp, "id格式错误");
			return;
		}

		try {
			twitter.createFriendship(id);
			GCache.clean("user:" + login_screenname + ":" + id);
			resp.sendRedirect(referer);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
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
		}
	}
	
	public void unfo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String id = req.getParameter("u");
		if(Utils.isEmptyOrNull(id))
		{
			showError(resp, "id格式错误");
			GCache.clean("user:" + login_screenname + ":" + id);
			return;
		}
		String yes = req.getParameter("yes");
		if(Utils.isEmptyOrNull(yes))
		{
			HashMap<String, Object> root = new HashMap<String, Object>();
			freemarker.template.Configuration config = new freemarker.template.Configuration();
			config.setDirectoryForTemplateLoading(new File("mobile"));
			config.setDefaultEncoding("UTF-8");
			
			try {;
				root.put("text", "你真的要取消关注" + id + "吗？");
				root.put("referer", referer);
				Template t = config.getTemplate("confirm.ftl");
				t.process(root, resp.getWriter());
			} catch (TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			referer = req.getParameter("referer");
			if(Utils.isEmptyOrNull(referer))
			{
				referer = "/m/home";
			}
			try {
				twitter.destroyFriendship(id);
				resp.sendRedirect(referer);
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
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
			}
		}
	}
	
	public void b(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String id = req.getParameter("u");
		if(Utils.isEmptyOrNull(id))
		{
			showError(resp, "id格式错误");
			return;
		}
		String yes = req.getParameter("yes");
		if(Utils.isEmptyOrNull(yes))
		{
			HashMap<String, Object> root = new HashMap<String, Object>();
			freemarker.template.Configuration config = new freemarker.template.Configuration();
			config.setDirectoryForTemplateLoading(new File("mobile"));
			config.setDefaultEncoding("UTF-8");
			
			try {
				root.put("text", "你真的要屏蔽" + id + "吗？");
				root.put("referer", referer);
				Template t = config.getTemplate("confirm.ftl");
				t.process(root, resp.getWriter());
			} catch (TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			referer = req.getParameter("referer");
			if(Utils.isEmptyOrNull(referer))
			{
				referer = "/m/blocking";
			}
			try {
				twitter.createBlock(id);
				resp.sendRedirect("/m/blocking");
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
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
			}
		}
	}
	
	public void unb(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String id = req.getParameter("u");
		if(Utils.isEmptyOrNull(id))
		{
			showError(resp, "id格式错误");
			return;
		}
		String yes = req.getParameter("yes");
		if(Utils.isEmptyOrNull(yes))
		{
			HashMap<String, Object> root = new HashMap<String, Object>();
			freemarker.template.Configuration config = new freemarker.template.Configuration();
			config.setDirectoryForTemplateLoading(new File("mobile"));
			config.setDefaultEncoding("UTF-8");
			
			try {
				root.put("text", "你真的要取消屏蔽" + id + "吗？");
				root.put("referer", referer);
				Template t = config.getTemplate("confirm.ftl");
				t.process(root, resp.getWriter());
			} catch (TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			referer = req.getParameter("referer");
			if(Utils.isEmptyOrNull(referer))
			{
				referer = "/m/blocking";
			}
			try {
				twitter.destroyBlock(id);
				resp.sendRedirect("/m/blocking");
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
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
			}
		}
	}
	
	public void dm(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("mobile"));
		config.setDefaultEncoding("UTF-8");
		String send_to = req.getParameter("u");
		
		try {
			root.put("login_user", login_user);
			root.put("send_to", send_to);
			Template t = config.getTemplate("msg_input.ftl");
			t.process(root, resp.getWriter());
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void delmsg(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String s_id = req.getParameter("id");
		if(s_id == null) s_id = "";
		long id;
		try{
			id = Long.parseLong(s_id);
		} catch(NumberFormatException e){
			showError(resp, "id格式错误");
			return;
		}
		String yes = req.getParameter("yes");
		if(Utils.isEmptyOrNull(yes))
		{
			HashMap<String, Object> root = new HashMap<String, Object>();
			freemarker.template.Configuration config = new freemarker.template.Configuration();
			config.setDirectoryForTemplateLoading(new File("mobile"));
			config.setDefaultEncoding("UTF-8");
			
			try {;
				root.put("text", "你真的要删除id为" + id + "的消息吗？");
				root.put("referer", referer);
				Template t = config.getTemplate("confirm.ftl");
				t.process(root, resp.getWriter());
			} catch (TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			referer = req.getParameter("referer");
			if(Utils.isEmptyOrNull(referer))
			{
				referer = "/m/inbox";
			}
			try {
				twitter.destroyStatus(id);
				resp.sendRedirect(referer);
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
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
			}
		}
	}
	
}
