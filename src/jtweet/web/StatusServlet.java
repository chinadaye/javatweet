package jtweet.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.web.template.GetBigPic;
import jtweet.web.template.TexttoHTML;

import twitter4j.TwitterException;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings("serial")
public class StatusServlet extends BaseServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		
		if(!isLogin(req))
		{
			redirectIndex(resp);
			return;
		}
		init_twitter(req, resp);
		
		String uri = req.getRequestURI();
		String[] path = uri.split("/");
		
		if(path.length < 3)
		{
			resp.sendRedirect("/home");
			return;
		}
		long id = Long.parseLong(path[2]);
		if(id <= 0)
		{
			resp.sendError(404);
			return;
		}
		
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");
		
		try {
			root.put("title", id);
			root.put("getbigpic", new GetBigPic());
			root.put("texttohtml", new TexttoHTML());
			root.put("status", twitter.showStatus(id));
			Template t = config.getTemplate("status.ftl");
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
