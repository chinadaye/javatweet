package jtweet;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jtweet.gae.GCache;

import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

public class HotTopics {
	private static String getPageSource(String url)
			throws MalformedURLException, IOException {
		HTTPResponse respon = URLFetchServiceFactory.getURLFetchService()
				.fetch(new URL(url));
		return new String(respon.getContent(), "UTF-8");
	}

	private static String getQueries() throws MalformedURLException,
			IOException {
		try{
		String pageSource = HotTopics
				.getPageSource("http://www.google.cn/rebang/detail?bid=12000000");
		String regex = "var queries = \\[(.+)\\];";
		Matcher mt = Pattern.compile(regex).matcher(pageSource);
		if (mt.find()) {
			return mt.group(1);
		}
		}catch(Exception e){
			
		}
		return null;
	}

	/**
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getTopics() throws MalformedURLException,
			IOException {
		if(true)
		return null;
		ArrayList<String> topics = (ArrayList<String>) GCache
				.get("hot_trends_g_cn");
		if (topics == null) {

			String topicsSource = HotTopics.getQueries();
			topicsSource = topicsSource.trim();
			String regex = "\\[\"(.+?)\",";
			Matcher mt = Pattern.compile(regex).matcher(topicsSource);
			topics = new ArrayList<String>();
			while (mt.find()) {
				topics.add(HotTopics.unescape(mt
						.group(1)));
			}
			if (topics.size() > 1) {
				GCache.put("hot_trends_g_cn", topics, 3600);
			}
		}
		return topics.subList(0, 10);
	}

	private static String unescape(String s) {
		int i = 0, len = s.length();
		char c;
		StringBuffer sb = new StringBuffer(len);
		while (i < len) {
			c = s.charAt(i++);
			if (c == '\\') {
				if (i < len) {
					c = s.charAt(i++);
					if (c == 'u') {
						c = (char) Integer.parseInt(s.substring(i, i + 4), 16);
						i += 4;
					} 
				}
			} 
			sb.append(c);
		}
		return sb.toString();
	}
}
