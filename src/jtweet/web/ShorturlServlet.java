package jtweet.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import jtweet.JtweetServlet;
import jtweet.ShortUrl;
import jtweet.Exception.InvaildUrlFormatException;
import jtweet.Exception.ShorturlNotFoundException;

public class ShorturlServlet extends JTweetServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4074231155979534633L;

	/**
	 * 生成短链接
	 * @param req
	 * @param resp
	 */
	@SuppressWarnings("unchecked")
	private void doShort(HttpServletRequest req, HttpServletResponse resp) {
		resp.setContentType("application/x-javascript; charset=UTF-8");
		JSONObject json = new JSONObject();
		try {
			String url = ShortUrl.processUrl(req.getParameter("url"), req
					.getServerName());
			json.put("short", url);
		} catch (InvaildUrlFormatException e) {
			json.put("error", e.getMessage());
		}
		try {
			resp.getWriter().write(json.toJSONString());
		} catch (IOException e) {
			JTweetServlet.logger.warning(e.getMessage());
			return;
		}
	}

	/**
	 * 还原短链接
	 * @param req
	 * @param resp
	 */
	@SuppressWarnings("unchecked")
	private void doRevert(HttpServletRequest req,
			HttpServletResponse resp) {
		resp.setContentType("application/x-javascript; charset=UTF-8");
		JSONObject json = new JSONObject();
		try {
			ShortUrl shorturl = ShortUrl.get(req.getParameter("code"));
			if (shorturl != null) {
				json.put("long", shorturl.getLongUrl());
			}
		} catch (ShorturlNotFoundException e) {
			json.put("error", e.getMessage());
		}
		try {
			resp.getWriter().write(json.toJSONString());
		} catch (IOException e) {
			JTweetServlet.logger.warning(e.getMessage());
			return;
		}
	}

	/**
	 * 跳转短链接
	 * @param req
	 * @param resp
	 */
	private void doRedeirct(HttpServletRequest req,
			HttpServletResponse resp) {
		JTweetServlet.logger.info("do redirect");
		try {
			String code = req.getRequestURI().split("/")[2];
			ShortUrl shorturl = ShortUrl.get(code);
			if (shorturl != null) {
				resp.sendRedirect(shorturl.getLongUrl());
			}
			throw new ShorturlNotFoundException();
		} catch (IOException e) {
			JTweetServlet.logger.warning(e.getMessage());
		} catch (Exception e) {
			this.showException(req, resp, e);
			return;
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getRequestURI().substring(1);
		if (action.equalsIgnoreCase("short")) {
			this.doShort(req, resp);
		} else if (action.equalsIgnoreCase("revert")) {
			this.doRevert(req, resp);
		} else {
			this.doRedeirct(req, resp);
		}
		return;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getRequestURI().substring(1);
		if (action.equalsIgnoreCase("short")) {
			this.doShort(req, resp);
		} else if (action.equalsIgnoreCase("revert")) {
			this.doRevert(req, resp);
		} else {
			this.doRedeirct(req, resp);
		}
	}
}
