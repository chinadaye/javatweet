package jtweet.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import twitter4j.TwitterException;

@SuppressWarnings("serial")
public class ActionServlet extends JTweetServlet {
	protected boolean rst = false;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException {	
		doAction(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws IOException {
		doAction(req, resp);
	}
	
	public void doAction(HttpServletRequest req, HttpServletResponse resp)throws IOException
	{
		resp.setContentType("application/x-javascript; charset=UTF-8");
		String action = req.getParameter("type");
		String id = req.getParameter("id");
		JSONObject json = new JSONObject();
		
		if(isLogin(req))
		{
			init_twitter(getUsername(), getPasswd());
			try {
				if(action.equalsIgnoreCase("post"))
				{
					String tweet = req.getParameter("tweet_msg");
					if(id != null)
					{
						try
						{
							long sid = Long.parseLong(id);
							twitter.updateStatus(tweet, sid);
						}
						catch(NumberFormatException e)
						{
							twitter.updateStatus(tweet);
						}
					}
					else
					{
						twitter.updateStatus(tweet);
					}
					rst = true;
				}
				else if(action.equalsIgnoreCase("msg"))
				{
					String tweet = req.getParameter("tweet_msg");
					if(id != null)
					{
						twitter.sendDirectMessage(id, tweet);
						rst = true;
					}
					else
					{
						json.put("info", "ID err");
					}
				}
				else if(action.equalsIgnoreCase("delmsg"))
				{
					if(id != null)
					{
						try
						{
							int sid = Integer.parseInt(id);
							twitter.destroyDirectMessage(sid);
							rst = true;
						}
						catch(NumberFormatException e)
						{
							json.put("info", "ID err");
						}
					}
					else
					{
						json.put("info", "ID err");
					}
				}
				else if(action.equalsIgnoreCase("delete"))
				{
					if(id != null)
					{
						try
						{
							long sid = Long.parseLong(id);
							twitter.destroyStatus(sid);
							rst = true;
						}
						catch(NumberFormatException e)
						{
							json.put("info", "ID err");
						}
					}
					else
					{
						json.put("info", "ID err");
					}
				}
				else if(action.equalsIgnoreCase("favor"))
				{
					if(id != null)
					{
						try
						{
							long sid = Long.parseLong(id);
							twitter.createFavorite(sid);
							rst = true;
						}
						catch(NumberFormatException e)
						{
							json.put("info", "ID err");
						}
					}
					else
					{
						json.put("info", "ID err");
					}
				}
				else if(action.equalsIgnoreCase("unfavor"))
				{
					if(id != null)
					{
						try
						{
							long sid = Long.parseLong(id);
							twitter.destroyFavorite(sid);
							rst = true;
						}
						catch(NumberFormatException e)
						{
							json.put("info", "ID err");
						}
					}
					else
					{
						json.put("info", "ID err");
					}
				}
				else if(action.equalsIgnoreCase("follow"))
				{
					if(id != null)
					{
						twitter.createFriendship(id);
						rst = true;
					}
					else
					{
						json.put("info", "ID err");
					}
				}
				else if(action.equalsIgnoreCase("unfollow"))
				{
					if(id != null)
					{
						twitter.destroyFriendship(id);
						rst = true;
					}
					else
					{
						json.put("info", "ID err");
					}
				}
				else if(action.equalsIgnoreCase("block"))
				{
					if(id != null)
					{
						twitter.createBlock(id);
						rst = true;
					}
					else
					{
						json.put("info", "ID err");
					}
				}
				else if(action.equalsIgnoreCase("unblock"))
				{
					if(id != null)
					{
						twitter.destroyBlock(id);
						rst = true;
					}
					else
					{
						json.put("info", "ID err");
					}
				}
				else
				{
					json.put("info", "Action type err.");
				}
				
	
			} catch (TwitterException e) {
				rst = false;
				// TODO Auto-generated catch block
				if(e.getStatusCode() == 400)
				{
					if(action.equalsIgnoreCase("delete"))
					{
						rst = true;
					}
					else
					{
						json.put("info", e.getStatusCode());
						e.printStackTrace();
					}
				}
				else
				{
					json.put("info", e.getStatusCode());
					e.printStackTrace();
				}
			}
		}
		else
		{
			json.put("info", "No login.");
		}
		
		if(rst)
		{
			json.put("result", "ok");
		}
		else
		{
			json.put("result", "err");
		}
		resp.getWriter().print(json.toJSONString());
	}
}
