package jtweet.mobile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.mobile.template.TexttoHTML;

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
		
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("mobile"));
		config.setDefaultEncoding("UTF-8");
		String uri = req.getRequestURI();
		String[] path = uri.split("/");
		
		if(path.length < 4)
		{
			resp.sendRedirect("/m/home");
			return;
		}
		long id = Long.parseLong(path[3]);
		if(id <= 0)
		{
			showError(resp, "id格式错误");
			return;
		}
		
		try {
			root.put("id", id);
			root.put("login_user", login_user);
			root.put("TexttoHTML", new TexttoHTML());
			root.put("status", twitter.showStatus(id));
			Template t = config.getTemplate("status.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			if(e.getStatusCode() == 401 || e.getStatusCode() == 403)
			{
				Template t = config.getTemplate("status.ftl");
				try {
					t.process(root, resp.getWriter());
				} catch (TemplateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
