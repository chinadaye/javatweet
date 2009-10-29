package jtweet.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.json.simple.JSONObject;

import twitter4j.Status;

public class CheckServlet extends JTweetServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2770684133440766156L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doCheck(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doCheck(req, resp);
	}

	@SuppressWarnings("unchecked")
	private void doCheck(HttpServletRequest req, HttpServletResponse resp) {
		resp.setContentType("application/x-javascript; charset=UTF-8");
		JSONObject json = new JSONObject();

		String type = req.getParameter("type");
		String sinceId = req.getParameter("since");
		try {
			this.paging.setSinceId(Long.parseLong(sinceId));
			if (null == type||null==sinceId) {
				throw new Exception("缺少参数");
			}else if(type.equalsIgnoreCase("home")){
				this.revertAccount(req);
				List<Status> statuses = this.twitter.getFriendsTimeline(paging);
				if(statuses!=null){
					json.put("code",JTweetServlet.JSON_SUCCESS);
					json.put("count", statuses.size());
				}
			}
		} catch (Exception e) {
			JTweetServlet.logger.warning(e.getStackTrace()[0].getClassName()+"("+e.getStackTrace()[0].getLineNumber()+"):"+e.getMessage());
			json.put("error", e.getMessage());
			json.put("code",JTweetServlet.JSON_ERROR);
			
		}
		try {
			resp.getWriter().write(json.toJSONString());
		} catch (IOException e) {
			JTweetServlet.logger.warning(e.getMessage());
		}
	}
}
