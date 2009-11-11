package jtweet.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.Exception.NotLoginException;

import org.apache.oro.text.perl.Perl5Util; //import org.json.JSONException;
import org.json.simple.JSONObject;

import freemarker.template.Template;

import twitter4j.Status;
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

	@SuppressWarnings("unchecked")
	public void doAction(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("application/x-javascript; charset=UTF-8");
		String action = req.getParameter("type");
		String id = req.getParameter("id");
		JSONObject json = new JSONObject();


		try {
			this.revertAccount(req);
			if (action.equalsIgnoreCase("post")) {
				String tweet = req.getParameter("tweet_msg");
				Status respon = null;
				if (tweet != null) {
					// tweet = ShortURL(tweet);
					// 匹配私信
					String directRegex = "^d\\s+([a-z0-9A-Z]+)\\s+([\\S+\\s?]+)$";
					Matcher mt = Pattern.compile(directRegex).matcher(
							tweet.trim());
					if (mt.find()) {
						JTweetServlet.logger.info(" direct found");
						twitter.sendDirectMessage(mt.group(1), mt.group(2));
					} else if (id != null) {
						try {
							long sid = Long.parseLong(id);
							respon = twitter.updateStatus(tweet, sid);
						} catch (NumberFormatException e) {
							respon = twitter.updateStatus(tweet);
						}
					} else {
						respon = twitter.updateStatus(tweet);
					}
					if(respon!=null){
						json.put("id", respon.getId());
						json.put("data", this.renderStatus(respon));
					}
					rst = true;
				} else {
					json.put("info", "empty msg");
				}
			} else if (action.equalsIgnoreCase("msg")) {
				String tweet = req.getParameter("tweet_msg");
				String directRegex = "d\\s+[a-z0-9A-Z]+\\s+(\\S+)";
				Matcher mt = Pattern.compile(directRegex).matcher(tweet.trim());
				if (mt.find()) {
					tweet = mt.group(1);
				}
				// tweet = ShortURL(tweet);
				if (id != null) {
					twitter.sendDirectMessage(id, tweet);
					rst = true;
				} else {
					json.put("info", "ID err");
				}
			} else if (action.equalsIgnoreCase("delmsg")) {
				if (id != null) {
					try {
						int sid = Integer.parseInt(id);
						twitter.destroyDirectMessage(sid);
						rst = true;
					} catch (NumberFormatException e) {
						json.put("info", "ID err");
					}
				} else {
					json.put("info", "ID err");
				}
			} else if (action.equalsIgnoreCase("delete")) {
				if (id != null) {
					try {
						long sid = Long.parseLong(id);
						twitter.destroyStatus(sid);
						rst = true;
					} catch (NumberFormatException e) {
						json.put("info", "ID err");
					}
				} else {
					json.put("info", "ID err");
				}
			} else if (action.equalsIgnoreCase("favor")) {
				if (id != null) {
					try {
						long sid = Long.parseLong(id);
						twitter.createFavorite(sid);
						rst = true;
					} catch (NumberFormatException e) {
						json.put("info", "ID err");
					}
				} else {
					json.put("info", "ID err");
				}
			} else if (action.equalsIgnoreCase("unfavor")) {
				if (id != null) {
					try {
						long sid = Long.parseLong(id);
						twitter.destroyFavorite(sid);
						rst = true;
					} catch (NumberFormatException e) {
						json.put("info", "ID err");
					}
				} else {
					json.put("info", "ID err");
				}
			} else if (action.equalsIgnoreCase("follow")) {
				if (id != null) {
					twitter.createFriendship(id);
					rst = true;
				} else {
					json.put("info", "ID err");
				}
			} else if (action.equalsIgnoreCase("unfollow")) {
				if (id != null) {
					twitter.destroyFriendship(id);
					rst = true;
				} else {
					json.put("info", "ID err");
				}
			} else if (action.equalsIgnoreCase("block")) {
				if (id != null) {
					twitter.createBlock(id);
					rst = true;
				} else {
					json.put("info", "ID err");
				}
			} else if (action.equalsIgnoreCase("unblock")) {
				if (id != null) {
					twitter.destroyBlock(id);
					rst = true;
				} else {
					json.put("info", "ID err");
				}
			} else {
				json.put("info", "Action type err.");
			}

		} catch (TwitterException e) {
			rst = false;
			JTweetServlet.logger.warning(e.getMessage());
			if (e.getStatusCode() == 400) {
				if (action.equalsIgnoreCase("delete")) {
					rst = true;
				} else {
					json.put("info", e.getStatusCode());
					e.printStackTrace();
				}
			} else {
				json.put("info", e.getStatusCode());
				e.printStackTrace();
			}
		} catch (NotLoginException e) {
			JTweetServlet.logger.warning(e.getMessage());
			logger.info(e.getMessage());
			json.put("info", e.getMessage());
		} catch (Exception e) {
			JTweetServlet.logger.warning(e.getMessage());
		}

		if (rst) {
			json.put("result", "ok");
		} else {
			json.put("result", "err");
		}
		resp.getWriter().print(json.toJSONString());
	}

	@Deprecated
	protected String ShortURL(String text) {
		String rst = text;
		String url_reg = "m/\\b[a-zA-Z]+:\\/\\/[\\w_.\\-]+\\.[a-zA-Z]{2,6}[\\/\\w\\-~.?=&%#+$*!:;]*\\b/i";
		String temp = text;

		Perl5Util perl = new Perl5Util();
		while (perl.match(url_reg, temp)) {
			String url = perl.group(0);
			if (url.length() > 30) {
				String short_url = ShortURL.getIsgdURL(url);
				if (short_url != null)
					rst = rst.replace(url, short_url);
				/*
				 * try { rst = rst.replace(url, Bitly.getBitlyURL(url)); } catch
				 * (JSONException e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); } catch (IOException e) { // TODO
				 * Auto-generated catch block e.printStackTrace(); }
				 */
			}
			temp = perl.postMatch();
		}

		return rst;
	}
}
