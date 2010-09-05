package jtweet.apiproxy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.gae.Configuration;
import jtweet.gae.GCache;
import jtweet.gae.PMF;
import jtweet.util.Utils;
import twitter4j.http.AccessToken;
import twitter4j.internal.http.BASE64Encoder;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.appengine.repackaged.com.google.common.util.Base64;
import com.google.appengine.repackaged.com.google.common.util.Base64DecoderException;


@SuppressWarnings("serial")
public class ApiServlet extends HttpServlet {
	private static Random RAND = new Random();
	private AccessToken accessToken;
	//static final Logger logger = Logger.getLogger(ApiServlet.class.getName());
	
	protected String twbase = "http://api.twitter.com";
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String req_url = req.getRequestURI().substring(4);
		String query_string = req.getQueryString();
		String username = new String();
		String passwd = new String();

		if(req_url.isEmpty() || req_url.equalsIgnoreCase("/"))
		{
			showinfo(req, resp);
			return;
		}
		if(!req_url.startsWith("/1")) req_url = "/1" + req_url;
		if(TRequest.getRequestType(req_url) == TRequest.NOTSUPPORT)
			resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
		else
		{
			
			URLFetchService urlFetch = URLFetchServiceFactory.getURLFetchService();
			HTTPRequest httpreq = new HTTPRequest(query_string == null ? new URL(twbase + req_url) : new URL(twbase + req_url + "?" + query_string), HTTPMethod.GET);
			
			String BasicAuth = req.getHeader("Authorization");
			//logger.log(Level.SEVERE, BasicAuth);
			if(!Utils.isEmptyOrNull(BasicAuth))
			{
				BasicAuth = BasicAuth.substring(6);
				try {
					BasicAuth = new String(Base64.decode(BasicAuth));
					username = BasicAuth.substring(0, BasicAuth.indexOf(":"));
					//logger.log(Level.SEVERE, username);
					passwd = BasicAuth.substring(BasicAuth.indexOf(":") + 1);
					//logger.log(Level.SEVERE, passwd);
					accessToken = getToken(username);
					if(accessToken != null && accessToken.getToken().startsWith(passwd))
					{
						Hashtable<String, String> params = new Hashtable<String, String>();
						Enumeration<String> param_names = req.getParameterNames();
						while(param_names.hasMoreElements())
						{
							String key = param_names.nextElement();
							params.put(key, req.getParameter(key));
						}
						httpreq.addHeader(new HTTPHeader("Authorization", generateAuthorizationHeader("GET", req_url, params)));
					}
				} catch (Base64DecoderException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			try
			{
				HTTPResponse httpresp = urlFetch.fetch(httpreq);
				if(httpresp.getResponseCode() == 401)
				{
					resp.setHeader("WWW-Authenticate","BASIC realm=\"Twitter.com realm\"");
					resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				}
				else
				{
					resp.getOutputStream().write(httpresp.getContent());
					//return err code
					if(httpresp.getResponseCode() != 200) resp.sendError(httpresp.getResponseCode());
				}
			}
			catch(IOException e)
			{
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
				//e.printStackTrace();
			}
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String req_url = req.getRequestURI().substring(4);
		String username = new String();
		String passwd = new String();
		String payload = new String();

		if(req_url.isEmpty() || req_url.equalsIgnoreCase("/"))
		{
			showinfo(req, resp);
			return;
		}
		if(!req_url.startsWith("/1")) req_url = "/1" + req_url;
		if(TRequest.getRequestType(req_url) == TRequest.NOTSUPPORT)
			resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
		else
		{
			
			URLFetchService urlFetch = URLFetchServiceFactory.getURLFetchService();
			HTTPRequest httpreq = new HTTPRequest(new URL(twbase + req_url), HTTPMethod.POST);
			
			String BasicAuth = req.getHeader("Authorization");
			if(!Utils.isEmptyOrNull(BasicAuth))
			{
				BasicAuth = BasicAuth.substring(6);
				try {
					BasicAuth = new String(Base64.decode(BasicAuth));
					username = BasicAuth.substring(0, BasicAuth.indexOf(":"));
					passwd = BasicAuth.substring(BasicAuth.indexOf(":") + 1);
					accessToken = getToken(username);
					if(accessToken != null && accessToken.getToken().startsWith(passwd))
					{
						Hashtable<String, String> params = new Hashtable<String, String>();
						if(!req.getContentType().contains("multipart/form-data"))
						{
							Enumeration<String> param_names = req.getParameterNames();
							while(param_names.hasMoreElements())
							{
								String key = param_names.nextElement();
								if(!key.equalsIgnoreCase("source"))
								{
									params.put(key, URLEncoder.encode(req.getParameter(key), "UTF-8").replaceAll("\\+", "%20"));
									payload += key + "=" + URLEncoder.encode(req.getParameter(key), "UTF-8") + "&";
								}
							}
							//logger.log(Level.SEVERE, payload);
						}
						httpreq.addHeader(new HTTPHeader("Authorization", generateAuthorizationHeader("POST", req_url, params)));
					}
				} catch (Base64DecoderException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			httpreq.addHeader(new HTTPHeader("Content-Type", req.getContentType()));
			if(req.getContentType().contains("multipart/form-data"))
			{
				httpreq.addHeader(new HTTPHeader("Connection", "Keep-Alive"));
				byte[] buf = new byte[req.getContentLength()];
				req.getInputStream().read(buf);
				httpreq.setPayload(buf);
			}
			else
			{
				httpreq.setPayload(payload.substring(0, payload.length()-1).getBytes("UTF-8"));
			}
			try
			{
				HTTPResponse httpresp = urlFetch.fetch(httpreq);
				if(httpresp.getResponseCode() == 401)
				{
					resp.setHeader("WWW-Authenticate","BASIC realm=\"Twitter.com realm\"");
					resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				}
				else
				{
					resp.getOutputStream().write(httpresp.getContent());
					//return err code
					if(httpresp.getResponseCode() != 200) resp.sendError(httpresp.getResponseCode());
				}
			}
			catch(IOException e)
			{
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
				//e.printStackTrace();
			}
		}
	}
	
    @SuppressWarnings("deprecation")
	private String generateAuthorizationHeader(String methed, String uri, Hashtable<String, String> params) {
    	
        Long timestamp = System.currentTimeMillis() / 1000;
        Long nonce = timestamp + RAND.nextInt();
    	
        params.put("oauth_consumer_key", Configuration.getConsumerKey());
        params.put("oauth_nonce", String.valueOf(nonce));
        params.put("oauth_signature_method", "HMAC-SHA1");
        params.put("oauth_token", accessToken.getToken());
        params.put("oauth_timestamp", String.valueOf(timestamp));
        params.put("oauth_version", "1.0");
        
        
        
        String base_str = methed + "&http%3A%2F%2Fapi.twitter.com" + URLEncoder.encode(uri) + "&" + encodeParams(params);
        //logger.log(Level.SEVERE, base_str);
        try {
			Mac mac = Mac.getInstance("HmacSHA1");
			String oauthSignature = URLEncoder.encode(Configuration.getConsumerSecret()) + "&" + URLEncoder.encode(accessToken.getTokenSecret());
	        SecretKeySpec spec = new SecretKeySpec(oauthSignature.getBytes(), "HmacSHA1");
	        mac.init(spec);
	        byte[] byteHMAC = mac.doFinal(base_str.getBytes());
	        String signature = BASE64Encoder.encode(byteHMAC);
	        
	    	String header_str = "OAuth oauth_nonce=\"" + nonce + "\", oauth_signature_method=\"HMAC-SHA1\", oauth_timestamp=\"" + timestamp
	    						+ "\", oauth_consumer_key=\"" + URLEncoder.encode(Configuration.getConsumerKey()) + "\", oauth_token=\"" + URLEncoder.encode(accessToken.getToken()) + "\", oauth_signature=\"" + URLEncoder.encode(signature) + "\", oauth_version=\"1.0\"";
	    	//logger.log(Level.SEVERE, header_str);
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
    
    private String encodeParams(Hashtable<String, String> params)
    {
    	StringBuffer rst = new StringBuffer();
    	Set<String> keyset = params.keySet();
    	String[] keys = new String[keyset.size()];
    	keyset.toArray(keys);
    	Arrays.sort(keys);
    	for(String key : keys)
    	{
    		try {
				rst.append(URLEncoder.encode(key, "UTF-8").replaceAll("\\+", "%20") + "%3D" + URLEncoder.encode(params.get(key), "UTF-8").replaceAll("\\+", "%20") + "%26");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	//logger.log(Level.SEVERE, rst);
    	return rst.substring(0, rst.length() - 3);
    }
    
    private void showinfo(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
    	String out = "<html><head><title>Jtweet API proxy with OAuth.</title></head><body><h1>Jtweet API proxy with OAuth</h1><p>请使用\"" + Utils.getBaseURL(req) + "/api/\"作为api</p><p>！！用您的用户名和登陆后系统给出的替代密码登陆！！</p><p>！！后台数据库会记录您的AccessToken，请确认搭建者为您信任的人！！</p><p><a href=\"/apioauth\">OAuth登陆</a></p><p><a href=\"/apioauthproxy\">OAuth Proxy登陆</a></p></body></html>";
    	resp.setContentType("text/html; charset=UTF-8");
    	resp.getOutputStream().write(out.getBytes("UTF-8"));
    }
    
    private AccessToken getToken(String username)
    {
    	AccessToken accessToken = (AccessToken) GCache.get("apitoken:" + username);
    	if(accessToken != null) return accessToken;
    	//AccessToken accessToken = null;
    	PersistenceManager pm = PMF.get().getPersistenceManager();
    	try
    	{
    		ApiUser user = pm.getObjectById(ApiUser.class, username);
    		accessToken = new AccessToken(user.getToken(), user.getSec());
    	}
    	finally
		{
    		pm.close();
		}
    	return accessToken;
    }
}
