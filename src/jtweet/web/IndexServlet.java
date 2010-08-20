package jtweet.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import jtweet.util.Utils;

@SuppressWarnings("serial")
public class IndexServlet extends BaseServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if(isLogin(req))
		{
			resp.sendRedirect("/home");
			return;
		}
		resp.setContentType("text/html; charset=UTF-8");
		
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");
		
		root.put("baseurl", Utils.getBaseURL(req));
		Template t = config.getTemplate("index.ftl");
		try {
			t.process(root, resp.getWriter());
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
