package twitter4j;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jtweet.web.ShortURL;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.oro.text.perl.Perl5Util;


public class TweetParser {

	public static String parseTextJava(String text){
		List<String> alreadyMatches = new ArrayList<String>();//去除重复匹配
		//twitpic
		String regex = "http://twitpic\\.com\\/([\\w]{5})\\s?";
		Matcher mt = Pattern.compile(regex).matcher(text);
		String images = "";
		while(mt.find()){
			if(alreadyMatches.contains(mt.group())){
				continue;
			}
			alreadyMatches.add(mt.group());
			images += "<img src=\"http://pictiny.appspot.com/tp?code="+mt.group(1)+"\" class=\"twitpic\">";
		}
		
		alreadyMatches.clear();
		//twcili 
		regex = "http://twic\\.li\\/([\\w]+)\\s?";
		mt = Pattern.compile(regex).matcher(text);
		while(mt.find()){
			if(alreadyMatches.contains(mt.group())){
				continue;
			}
			alreadyMatches.add(mt.group());
			images += "<img src=\"http://twic.li/api/photo.jpg?id="+mt.group(1)+"&size=small\" class=\"twitpic\">";
		}
		
		//imgur api key:795d9078195b10b18e2ccb0805a2a37b
		
		//http://moby.to/ktov1t
		alreadyMatches.clear();
		regex = "http://moby\\.to\\/([\\w]+)\\s?";
		mt = Pattern.compile(regex).matcher(text);
		while(mt.find()){
			if(alreadyMatches.contains(mt.group())){
				continue;
			}
			alreadyMatches.add(mt.group());
			images += "<img src=\"http://api.mobypicture.com?t="+mt.group()+"&s=small&k=7A6KUc5NPsXyC2v3&format=plain\" class=\"twitpic\">";
		}
		
		alreadyMatches.clear();
		//twitgoo
		regex = "http://twitgoo\\.com\\/([\\w]{5})\\s?";
		mt = Pattern.compile(regex).matcher(text);
		while(mt.find()){
			if(alreadyMatches.contains(mt.group())){
				continue;
			}
			alreadyMatches.add(mt.group());
			images += "<img src=\""+mt.group()+"/thumb\" class=\"twitpic\">";
		}
		
		alreadyMatches.clear();
		//xiami
		regex = "http://w{0,3}\\.{0,1}xiami\\.com/(artist|album)/(\\d+)";
		mt = Pattern.compile(regex).matcher(text);
		while(mt.find()){
			if(alreadyMatches.contains(mt.group())){
				continue;
			}
			alreadyMatches.add(mt.group());
			images += "<img src=\"http://www.xiami.com/cover/"+mt.group(1)+"/normal/"+mt.group(2)+"\" class=\"twitpic\">";
		}
		/*
		 * <embed src="http://www.xiami.com/widget/2_1205484/singlePlayer.swf" type="application/x-shockwave-flash" width="257" height="33" wmode="transparent"></embed>
		 */
		alreadyMatches.clear();
		regex = "http://www\\.xiami\\.com/song/(\\d+)";
		mt = Pattern.compile(regex).matcher(text);
		String bobo = "";
		while(mt.find()){
			if(alreadyMatches.contains(mt.group())){
				continue;
			}
			alreadyMatches.add(mt.group());
			bobo += "<embed src=\"http://www.xiami.com/widget/2_"+mt.group(1)+"/singlePlayer.swf\" type=\"application/x-shockwave-flash\" width=\"257\" height=\"33\" wmode=\"transparent\"></embed>";
		}
		
		alreadyMatches.clear();
		//img.ly
		regex = "http://img\\.ly\\/([\\w]{3,5})\\s?";
		mt = Pattern.compile(regex).matcher(text);
		while(mt.find()){
			if(alreadyMatches.contains(mt.group())){
				continue;
			}
			alreadyMatches.add(mt.group());
			images += "<img src=\"http://img.ly/show/thumb/"+mt.group(1)+"\" class=\"twitpic\">";
		}
		
		alreadyMatches.clear();
		//brizzly.com
		regex = "http://brizzly\\.com\\/pic\\/([\\w]{3,5})\\s?";
		mt = Pattern.compile(regex).matcher(text);
		while(mt.find()){
			if(alreadyMatches.contains(mt.group())){
				continue;
			}
			alreadyMatches.add(mt.group());
			images += "<img src=\"http://pics.brizzly.com/thumb_sm_" +mt.group(1)+".jpg\" class=\"twitpic\">";
		}
		
		//url
		regex = "([A-Za-z]+://[A-Za-z0-9-,_]+\\.[A-Za-z0-9-_,:%&\\?\\/.#=\\+]+)";//link
		text = text.replaceAll(regex, "<a target=\"_blank\" class=\"mayshort\" href=\"$1\">$1</a>");
		
		//query
		regex = "\\s#([^\\s^，^,^\\/]{1,20})";
		text = text.replaceAll(regex, "<a class=\"search_link\" href=\"/search?s=$1\">#$1</a>");
		
		alreadyMatches.clear();
		//people
		String mentionUsers = "";
		regex = "@([A-Za-z0-9_]+)";
		mt = Pattern.compile(regex).matcher(text);
		while(mt.find()){
			if(alreadyMatches.contains(mt.group())){
				continue;
			}
			alreadyMatches.add(mt.group());
			mentionUsers += " reply_to_"+mt.group(1); 
		}
		text = text.replaceAll(regex, "<a class=\"user_link\" href=\"/@$1\">@$1</a>");
		
		return "<div class=\"twittertext "+mentionUsers+"\">" + text +"</div>"+images+bobo;
	}
	public static String parseText(String text)
	{
		Perl5Util perl = new Perl5Util();
		String text_e= StringEscapeUtils.escapeHtml(text);
		String temp = text_e;
		
		String twitpic_reg = "m/http:\\/\\/twitpic.com\\/[\\w]{5}/i";
		String twitgoo_reg = "m/http:\\/\\/twitgoo.com\\/[\\w]{5}/i";
		String imgly_reg = "m/http:\\/\\/img.ly\\/[\\w]{3,5}/i";
		String brizzly_reg = "m/http:\\/\\/brizzly.com\\/pic\\/[\\w]{3,5}/i";
		String moby_reg = "m/http:\\/\\/(mobypicture.com\\/\\?|moby.to\\/)[\\w]{6}/i";
		String url_reg = "s/\\b([a-zA-Z]+:\\/\\/[\\w_.\\-]+\\.[a-zA-Z]{2,6}[\\/\\w\\-~.?=&%#+$*!:;]*)\\b/<a href=\"$1\" class=\"twitter-link\" class=\"web_link\" target=\"_blank\">$1<\\/a>/ig";
		String mail_reg = "s/\\b([a-zA-Z][a-zA-Z0-9\\_\\.\\-]*[a-zA-Z]*\\@[a-zA-Z][a-zA-Z0-9\\_\\.\\-]*[a-zA-Z]{2,6})\\b/<a href=\"mailto:$1\" class=\"web_link\" >$1<\\/a>/ig";
		String user_reg = "s/([\\s|\\.|\\,|\\:|\\xA1|\\xBF\\>|\\{|\\(]?)@{1}(\\w*)([\\.|\\,|\\:|\\!|\\?|\\>|\\}|\\)]?)[\\s|$]/$1\\<a href=\"\\/user\\?id=$2\" class=\"user_link\"\\>@$2\\<\\/a\\>$3 /ig";
		String trend_reg = "s/([\\s|\\.|\\,|\\:|\\xA1|\\xBF\\>|\\{|\\(]?)#{1}(\\w*)([\\.|\\,|\\:|\\!|\\?|\\>|\\}|\\)]?)[\\s|$]/$1\\<a href=\"\\/search\\?s=%23$2\" class=\"search_link\"\\>#$2\\<\\/a\\>$3 /ig";
		//短域名还原在服务器端实现性能很难保证，应该有客户端js来执行还原，稍候实现
		//String shorturl_reg = "m/http:\\/\\/(bit.ly|j.mp|ff.im)\\/[\\w\\-]{3,10}/i";
		
		/*while(perl.match(shorturl_reg, temp))
		{
			String longurl = ShortURL.getLongURL(perl.group(0));
			//if(longurl != null) text_e = text_e.replace(perl.group(0), longurl);
			if(longurl != null)
			{
				String shorturl = ShortURL.getIsgdURL(longurl);
				if(shorturl != null)
				{
					text_e = text_e.replace(perl.group(0), shorturl);
				}
			}
			temp = perl.postMatch();
		}*/
		
		temp = text_e;
		
		String rst = perl.substitute(url_reg, text_e);
		rst = perl.substitute(mail_reg, rst);
		rst = perl.substitute(user_reg, rst);
		rst = perl.substitute(trend_reg, rst);
		
		rst = "<div class=\"twittertext\">" + rst +"</div>";
		
		while(perl.match(twitpic_reg, temp))
		{
			//rst += "<img src=\"http://twitpic.com/show/thumb/" + perl.group(0).substring(19) + "\" class=\"twitpic\" />";
			rst += "<img src=\"/picthumb?id=" + perl.group(0).substring(19) + "\" class=\"twitpic\" />";
			temp = perl.postMatch();
		}
		
		temp = text_e;
		
		while(perl.match(twitgoo_reg, temp))
		{
			rst += "<img src=\"" + perl.group(0) + "/thumb\" class=\"twitpic\" />";
			temp = perl.postMatch();
		}

		temp = text_e;
		
		while(perl.match(imgly_reg, temp))
		{
			rst += "<img src=\"http://img.ly/show/thumb/" + perl.group(0).substring(14) + "\" class=\"twitpic\" />";
			temp = perl.postMatch();
		}

		temp = text_e;
		
		while(perl.match(brizzly_reg, temp))
		{
			rst += "<img src=\"http://pics.brizzly.com/thumb_sm_" + perl.group(0).substring(23) + ".jpg\" class=\"twitpic\" />";
			temp = perl.postMatch();
		}
		
		temp = text_e;
		
		while(perl.match(moby_reg, temp))
		{
			rst += "<img src=\"" + getMobypic(perl.group(0)) + "\" class=\"twitpic\" />";
			temp = perl.postMatch();
		}
		
		return rst;
	}
	
	private static String getMobypic(String url)
	{
		String rst = "";
		String api_key = "QsQQdkRIqOt6ByZX";
		String url_en;
		try {
			url_en = URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			url_en = url;
			e1.printStackTrace();
		}
		rst = "http://api.mobypicture.com?&t=" + url_en + "&s=thumbnail&k=" + api_key + "&format=plain"; 
		return rst;
	}
}
