package jtweet.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.User;


public class JSGenerateServlet extends JTweetServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4294624563699530786L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("application/x-javascript; charset=UTF-8");
		String file = req.getRequestURI().substring(1);
		if(file.equals("js_follower_autocomplete")){
			try {
				this.revertAccount(req);
				List<User> followers = this.getCachedFollowers();
				PrintWriter writer = resp.getWriter();
				writer.write("var followers = '");
				for(User follower:followers){
					writer.write(follower.getScreenName()+" ");
				}
				writer.write("'.split(' ');");
//				writer.write("$(\"#user_followers\").autocomplete(followers,{matchContains: true});");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ;
	}
}
