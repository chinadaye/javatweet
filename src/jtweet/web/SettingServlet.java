package jtweet.web;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.Exception.NotLoginException;

import twitter4j.TwitterException;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.appengine.repackaged.com.google.common.util.Base64;
import com.google.appengine.repackaged.com.google.common.util.Base64DecoderException;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings("serial")
public class SettingServlet extends JTweetServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		
			try {
				this.revertAccount(req);
				getSetting(resp, null);
			} catch (NotLoginException e) {
				this.redirectLogin(req, resp);
			} catch (Exception e) {
				JTweetServlet.logger.warning(e.getMessage());
				this.showError(req, resp, e.getMessage());
			}
	
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		String uri = req.getRequestURI();
		
			try {
				this.revertAccount(req);
				if(uri.equalsIgnoreCase("/setimg"))
				{
						UpdateImg(req, resp);
				}
				else
				{
					UpdateProfile(req, resp);
				}
			} catch (NotLoginException e) {
				this.redirectLogin(req, resp);
			} catch (Exception e) {
				JTweetServlet.logger.warning(e.getMessage());
				this.showError(req, resp, e.getMessage());
			}
			
		
	}
	
	protected void getSetting(HttpServletResponse resp, String msg) throws IOException, TwitterException, NotLoginException, TemplateException
	{
		HashMap<String,Object> root = new HashMap<String,Object>();
		freemarker.template.Configuration config=new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");
		
			root.put("user", this.getCachedUser());
			if(msg != null) root.put("msg", msg);
			Template t = config.getTemplate("setting.ftl");
			t.process(root, resp.getWriter());
	}
	
	protected void UpdateProfile(HttpServletRequest req, HttpServletResponse resp) throws IOException, TwitterException, NotLoginException, TemplateException
	{
		String msg = null;
		String name = req.getParameter("name");
		String url = req.getParameter("url");
		String loc = req.getParameter("location");
		String desc = req.getParameter("description");
		
		if(name.trim().isEmpty()) name = null;
		if(url.trim().isEmpty()) url = null;
		if(loc.trim().isEmpty()) loc = null;
		if(desc.trim().isEmpty()) desc = null;
		
		if(name == null && url == null && loc == null && desc == null)
		{
			msg = "什么也没有更新，因为你啥也没提交。";
		}
		else
		{
				twitter.updateProfile(name, null, url, loc, desc);
				msg = "资料更新成功。";
		}
		
		getSetting(resp, msg);
	}
	protected void UpdateImg(HttpServletRequest req, HttpServletResponse resp) throws IOException, NotLoginException, TwitterException, TemplateException
	{
		String msg = null;
		
		URLFetchService urlFetch = URLFetchServiceFactory.getURLFetchService();
		HTTPRequest httpreq = new HTTPRequest(new URL(twitter.getBaseURL() + "account/update_profile_image.json"), HTTPMethod.POST);
		httpreq.addHeader(new HTTPHeader("User-Agent", twitter.getUserAgent()));
		httpreq.addHeader(new HTTPHeader("Connection", "Keep-Alive"));
		httpreq.addHeader(new HTTPHeader("Content-Type", req.getContentType()));
		String auth = getUsername() + ":" + getPasswd();
		httpreq.addHeader(new HTTPHeader("Authorization", "Basic "+ Base64.encode(auth.getBytes("UTF-8"))));
		
		byte[] buf = new byte[req.getContentLength()];
		req.getInputStream().read(buf);
		
		httpreq.setPayload(buf);
		HTTPResponse httpresp = urlFetch.fetch(httpreq);
		
		if(httpresp.getResponseCode() == 200)
		{
			msg="图片更新成功";
		}
		else
		{
			msg = "图片更新失败，错误代码：" + httpresp.getResponseCode() + "。";
		}
		
		getSetting(resp, msg);	
	}

}
