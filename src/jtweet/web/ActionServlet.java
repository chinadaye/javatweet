package jtweet.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.gae.GCache;
import jtweet.util.ShortURL;
import jtweet.util.Utils;

import org.json.simple.JSONObject;

import twitter4j.StatusUpdate;
import twitter4j.TwitterException;

@SuppressWarnings("serial")
public class ActionServlet extends BaseServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doAction(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doAction(req, resp);
	}

	public void doAction(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String type = req.getParameter("type");
		resp.setContentType("application/x-javascript; charset=UTF-8");
		if (!isLogin(req)) {
			JSONObject json = new JSONObject();
			json.put("success", false);
			json.put("info", "no login");
			resp.getOutputStream().write(json.toJSONString().getBytes("utf-8"));
			return;
		}
		init_twitter(req, resp);

		if (type.equalsIgnoreCase("poststatus")) {
			poststatus(req, resp);
		} else if (type.equalsIgnoreCase("rtstatus")) {
			rtstatus(req, resp);
		} else if (type.equalsIgnoreCase("fav")) {
			favstatus(req, resp);
		} else if (type.equalsIgnoreCase("unfav")) {
			unfavstatus(req, resp);
		} else if (type.equalsIgnoreCase("delstatus")) {
			delstatus(req, resp);
		} else if (type.equalsIgnoreCase("sendmsg")) {
			sendmsg(req, resp);
		} else if (type.equalsIgnoreCase("delmsg")) {
			delmsg(req, resp);
		} else if (type.equalsIgnoreCase("fo")) {
			fo(req, resp);
		} else if (type.equalsIgnoreCase("unfo")) {
			unfo(req, resp);
		} else if (type.equalsIgnoreCase("b")) {
			block(req, resp);
		} else if (type.equalsIgnoreCase("unb")) {
			unblock(req, resp);
		} else if (type.equalsIgnoreCase("reloadprofile")) {
			reloadprofile(req, resp);
		} else {
			JSONObject json = new JSONObject();
			json.put("success", false);
			json.put("info", "wrong action");
			resp.getOutputStream().write(json.toJSONString().getBytes("utf-8"));
		}

	}

	public void poststatus(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String t = req.getParameter("t");
		String re_id = req.getParameter("id");
		long id = 0;
		boolean rst = false;
		String info = new String();
		JSONObject json = new JSONObject();

		try {
			if (!Utils.isEmptyOrNull(re_id)) {
				id = Long.parseLong(re_id);
			}
			t = ShortURL.ShortURL_isgd(t);
			if (t.length() > 140) {
				t = t.substring(0, 139) + "…";
			}
			if (id > 0) {
				StatusUpdate statusUpdate = new StatusUpdate(t);
				twitter.updateStatus(statusUpdate.inReplyToStatusId(id));
			} else {
				StatusUpdate statusUpdate = new StatusUpdate(t);
				twitter.updateStatus(statusUpdate);
			}
			rst = true;
		} catch (TwitterException e) {
			info = "代码：" + e.getStatusCode();
		} catch (NumberFormatException e) {
			info = "id格式错误";
		}
		if (rst) {
			json.put("success", true);
		} else {
			json.put("success", false);
			json.put("info", info);
		}
		resp.getOutputStream().write(json.toJSONString().getBytes("utf-8"));
	}

	public void rtstatus(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String re_id = req.getParameter("id");
		long id = 0;
		boolean rst = false;
		String info = new String();
		JSONObject json = new JSONObject();

		try {
			if (!Utils.isEmptyOrNull(re_id)) {
				id = Long.parseLong(re_id);
			}
			if (id > 0) {
				twitter.retweetStatus(id);
				rst = true;
			} else {
				info = "id格式错误";
			}

		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			info = "代码：" + e.getStatusCode();
		} catch (NumberFormatException e) {
			info = "id格式错误";
		}
		if (rst) {
			json.put("success", true);
		} else {
			json.put("success", false);
			json.put("info", info);
		}
		resp.getOutputStream().write(json.toJSONString().getBytes("utf-8"));
	}

	public void delstatus(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String re_id = req.getParameter("id");
		long id = 0;
		boolean rst = false;
		String info = new String();
		JSONObject json = new JSONObject();

		try {
			if (!Utils.isEmptyOrNull(re_id)) {
				id = Long.parseLong(re_id);
			}
			if (id > 0) {
				twitter.destroyStatus(id);
				rst = true;
			} else {
				info = "id格式错误";
			}

		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			info = "代码：" + e.getStatusCode();
		} catch (NumberFormatException e) {
			info = "id格式错误";
		}
		if (rst) {
			json.put("success", true);
		} else {
			json.put("success", false);
			json.put("info", info);
		}
		resp.getOutputStream().write(json.toJSONString().getBytes("utf-8"));
	}

	public void favstatus(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String re_id = req.getParameter("id");
		long id = 0;
		boolean rst = false;
		String info = new String();
		JSONObject json = new JSONObject();

		try {
			if (!Utils.isEmptyOrNull(re_id)) {
				id = Long.parseLong(re_id);
			}
			if (id > 0) {
				twitter.createFavorite(id);
				rst = true;
			} else {
				info = "id格式错误";
			}

		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			info = "代码：" + e.getStatusCode();
		} catch (NumberFormatException e) {
			info = "id格式错误";
		}
		if (rst) {
			json.put("success", true);
		} else {
			json.put("success", false);
			json.put("info", info);
		}
		resp.getOutputStream().write(json.toJSONString().getBytes("utf-8"));
	}

	public void unfavstatus(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String re_id = req.getParameter("id");
		long id = 0;
		boolean rst = false;
		String info = new String();
		JSONObject json = new JSONObject();

		try {
			if (!Utils.isEmptyOrNull(re_id)) {
				id = Long.parseLong(re_id);
			}
			if (id > 0) {
				twitter.destroyFavorite(id);
				rst = true;
			} else {
				info = "id格式错误";
			}

		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			info = "代码：" + e.getStatusCode();
		} catch (NumberFormatException e) {
			info = "id格式错误";
		}
		if (rst) {
			json.put("success", true);
		} else {
			json.put("success", false);
			json.put("info", info);
		}
		resp.getOutputStream().write(json.toJSONString().getBytes("utf-8"));
	}

	public void sendmsg(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String t = req.getParameter("t");
		String id = req.getParameter("id");
		boolean rst = false;
		String info = new String();
		JSONObject json = new JSONObject();

		if (Utils.isEmptyOrNull(id)) {
			rst = false;
			info = "id格式错误";
		}
		t = ShortURL.ShortURL_isgd(t);
		if (t.length() > 140) {
			t = t.substring(0, 139) + "…";
		} else {
			try {
				twitter.sendDirectMessage(id, t);
				rst = true;
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				info = "代码：" + e.getStatusCode();
			}
		}
		if (rst) {
			json.put("success", true);
		} else {
			json.put("success", false);
			json.put("info", info);
		}
		resp.getOutputStream().write(json.toJSONString().getBytes("utf-8"));
	}

	public void delmsg(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String re_id = req.getParameter("id");
		int id = 0;
		boolean rst = false;
		String info = new String();
		JSONObject json = new JSONObject();

		try {
			if (!Utils.isEmptyOrNull(re_id)) {
				id = Integer.parseInt(re_id);
			}
			if (id > 0) {
				twitter.destroyDirectMessage(id);
				rst = true;
			} else {
				info = "id格式错误";
			}

		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			info = "代码：" + e.getStatusCode();
		} catch (NumberFormatException e) {
			info = "id格式错误";
		}
		if (rst) {
			json.put("success", true);
		} else {
			json.put("success", false);
			json.put("info", info);
		}
		resp.getOutputStream().write(json.toJSONString().getBytes("utf-8"));
	}

	public void fo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String id = req.getParameter("id");
		boolean rst = false;
		String info = new String();
		JSONObject json = new JSONObject();

		try {
			if (!Utils.isEmptyOrNull(id)) {
				twitter.createFriendship(id);
				GCache.clean("user:" + login_screenname + ":" + id);
				rst = true;
			} else {
				info = "id格式错误";
			}

		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			info = "代码：" + e.getStatusCode();
		}
		if (rst) {
			json.put("success", true);
		} else {
			json.put("success", false);
			json.put("info", info);
		}
		resp.getOutputStream().write(json.toJSONString().getBytes("utf-8"));
	}

	public void unfo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String id = req.getParameter("id");
		boolean rst = false;
		String info = new String();
		JSONObject json = new JSONObject();

		try {
			if (!Utils.isEmptyOrNull(id)) {
				twitter.destroyFriendship(id);
				GCache.clean("user:" + login_screenname + ":" + id);
				rst = true;
			} else {
				info = "id格式错误";
			}

		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			info = "代码：" + e.getStatusCode();
		}
		if (rst) {
			json.put("success", true);
		} else {
			json.put("success", false);
			json.put("info", info);
		}
		resp.getOutputStream().write(json.toJSONString().getBytes("utf-8"));
	}

	public void block(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String id = req.getParameter("id");
		boolean rst = false;
		String info = new String();
		JSONObject json = new JSONObject();

		try {
			if (!Utils.isEmptyOrNull(id)) {
				twitter.createBlock(id);
				rst = true;
			} else {
				info = "id格式错误";
			}

		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			info = "代码：" + e.getStatusCode();
		}
		if (rst) {
			json.put("success", true);
		} else {
			json.put("success", false);
			json.put("info", info);
		}
		resp.getOutputStream().write(json.toJSONString().getBytes("utf-8"));
	}

	public void unblock(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String id = req.getParameter("id");
		boolean rst = false;
		String info = new String();
		JSONObject json = new JSONObject();

		try {
			if (!Utils.isEmptyOrNull(id)) {
				twitter.destroyBlock(id);
				rst = true;
			} else {
				info = "id格式错误";
			}

		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			info = "代码：" + e.getStatusCode();
		}
		if (rst) {
			json.put("success", true);
		} else {
			json.put("success", false);
			json.put("info", info);
		}
		resp.getOutputStream().write(json.toJSONString().getBytes("utf-8"));
	}

	public void reloadprofile(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		boolean rst = false;
		String info = new String();
		JSONObject json = new JSONObject();

		try {
			GCache.clean("user:" + login_screenname + ":" + login_screenname);
			login_user = twitter.verifyCredentials();
			GCache.put("user:" + login_screenname + ":" + login_screenname, login_user, 120);
			rst = true;
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			info = "代码：" + e.getStatusCode();
		}
		if (rst) {
			json.put("success", true);
			json.put("t_count", login_user.getStatusesCount());
			json.put("foing_count", login_user.getFriendsCount());
			json.put("foer_count", login_user.getFollowersCount());
			json.put("favs_count", login_user.getFavouritesCount());
		} else {
			json.put("success", false);
			json.put("info", info);
		}
		resp.getOutputStream().write(json.toJSONString().getBytes("utf-8"));
	}
}
