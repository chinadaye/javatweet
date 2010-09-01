package jtweet.web;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.gae.Configuration;
import jtweet.gae.GCache;
import jtweet.util.Utils;
import jtweet.web.template.GetBigPic;
import twitter4j.TwitterException;
import twitter4j.internal.http.BASE64Encoder;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings("serial")
public class SettingServlet extends BaseServlet {
	private static Random RAND = new Random();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		if(!isLogin(req))
		{
			redirectIndex(resp);
			return;
		}
		init_twitter(req, resp);
		getSetting(resp, null);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		String uri = req.getRequestURI();
		if(!isLogin(req))
		{
			redirectIndex(resp);
			return;
		}
		init_twitter(req, resp);
		if (uri.equalsIgnoreCase("/setimg")) {
			UpdateImg(req, resp);
		} else {
			UpdateProfile(req, resp);
		}
	}
	
	protected void getSetting(HttpServletResponse resp, String msg) throws IOException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		try {
			root.put("login_user", login_user);
			root.put("getbigpic", new GetBigPic());
			if (msg != null)
				root.put("msg", msg);
			Template t = config.getTemplate("setting.ftl");
			t.process(root, resp.getWriter());
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void UpdateProfile(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String msg = null;
		String name = req.getParameter("name");
		String url = req.getParameter("url");
		String loc = req.getParameter("location");
		String desc = req.getParameter("desc");

		if (Utils.isEmptyOrNull(name))
			name = null;
		if (Utils.isEmptyOrNull(url))
			url = null;
		if (Utils.isEmptyOrNull(loc))
			loc = null;
		if (Utils.isEmptyOrNull(desc))
			desc = null;

		if (name == null && url == null && loc == null && desc == null) {
			msg = "什么也没有更新，因为你啥也没提交。";
		} else {
			try {
				twitter.updateProfile(name, null, url, loc, desc);
				GCache.clean("user:" + login_screenname + ":" + login_screenname);
				msg = "资料更新成功。";
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				msg = "资料更新失败，错误代码：" + e.getStatusCode() + "。";
				//e.printStackTrace();
			}
		}

		getSetting(resp, msg);
	}
	
	protected void UpdateImg(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String msg = null;

		URLFetchService urlFetch = URLFetchServiceFactory.getURLFetchService();
		HTTPRequest httpreq = new HTTPRequest(new URL("http://api.twitter.com/1/account/update_profile_image.json"), HTTPMethod.POST);
		httpreq.addHeader(new HTTPHeader("Connection", "Keep-Alive"));
		httpreq.addHeader(new HTTPHeader("Content-Type", req.getContentType()));
		httpreq.addHeader(new HTTPHeader("Authorization", generateAuthorizationHeader()));

		byte[] buf = new byte[req.getContentLength()];
		req.getInputStream().read(buf);

		httpreq.setPayload(buf);
		HTTPResponse httpresp = urlFetch.fetch(httpreq);

		if (httpresp.getResponseCode() == 200) {
			GCache.clean("user:" + login_screenname + ":" + login_screenname);
			msg = "图片更新成功";
		} else {
			msg = "图片更新失败，错误代码：" + httpresp.getResponseCode() + "。";
		}

		getSetting(resp, msg);
	}
	
    @SuppressWarnings("deprecation")
	private String generateAuthorizationHeader() {
    	
        Long timestamp = System.currentTimeMillis() / 1000;
        Long nonce = timestamp + RAND.nextInt();
    	String base_str = "POST&http%3A%2F%2Fapi.twitter.com%2F1%2Faccount%2Fupdate_profile_image.json&oauth_consumer_key%3D" + URLEncoder.encode(Configuration.getConsumerKey()) + "%26oauth_nonce%3D" + nonce +"%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D" + timestamp + "%26oauth_token%3D" + URLEncoder.encode(getAccessToken().getToken()) + "%26oauth_version%3D1.0";
        try {
			Mac mac = Mac.getInstance("HmacSHA1");
			String oauthSignature = URLEncoder.encode(Configuration.getConsumerSecret()) + "&" + URLEncoder.encode(getAccessToken().getTokenSecret());
	        SecretKeySpec spec = new SecretKeySpec(oauthSignature.getBytes(), "HmacSHA1");
	        mac.init(spec);
	        byte[] byteHMAC = mac.doFinal(base_str.getBytes());
	        String signature = BASE64Encoder.encode(byteHMAC);
	        
	    	String header_str = "OAuth oauth_nonce=\"" + nonce + "\", oauth_signature_method=\"HMAC-SHA1\", oauth_timestamp=\"" + timestamp
	    						+ "\", oauth_consumer_key=\"" + URLEncoder.encode(Configuration.getConsumerKey()) + "\", oauth_token=\"" + URLEncoder.encode(getAccessToken().getToken()) + "\", oauth_signature=\"" + URLEncoder.encode(signature) + "\", oauth_version=\"1.0\"";
	    	return header_str;
        } catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
}
