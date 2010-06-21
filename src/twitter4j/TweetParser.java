package twitter4j;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import jtweet.web.ShortURL;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.oro.text.perl.Perl5Util;

import com.google.appengine.repackaged.com.google.common.util.Base64;

public class TweetParser {

	public static String parseText(String text) {
		Perl5Util perl = new Perl5Util();
		String text_e = StringEscapeUtils.escapeHtml(text);
		String temp;

		String twitpic_reg = "m/http:\\/\\/twitpic.com\\/[\\w]{5}/i";
		String twitgoo_reg = "m/http:\\/\\/twitgoo.com\\/[\\w]{5}/i";
		String imgly_reg = "m/http:\\/\\/img.ly\\/[\\w]{3,5}/i";
		String brizzly_reg = "m/http:\\/\\/brizzly.com\\/pic\\/[\\w]{3,5}/i";
		String owly_reg = "m/http:\\/\\/ow.ly\\/i\\/[\\w]{3,5}/i";
		String picgd_reg = "m/http:\\/\\/pic.gd\\/[\\w]{3,5}/i";
		String tweetphoto_reg = "m/http:\\/\\/tweetphoto.com\\/[\\w]{3,5}/i";
		String ts1in_reg = "m/http:\\/\\/ts1.in\\/[\\w]{3,5}/i";
		String moby_reg = "m/http:\\/\\/(mobypicture.com\\/\\?|moby.to\\/)[\\w]{6}/i";
		String url_reg = "s/\\b([a-zA-Z]+:\\/\\/[\\w_.\\-]+\\.[a-zA-Z]{2,6}[\\/\\w\\-~.?=&%#+$*!:;]*)\\b/<a href=\"$1\" class=\"twitter-link\" class=\"web_link\" target=\"_blank\">$1<\\/a>/ig";
		String mail_reg = "s/\\b([a-zA-Z][a-zA-Z0-9\\_\\.\\-]*[a-zA-Z]*\\@[a-zA-Z][a-zA-Z0-9\\_\\.\\-]*[a-zA-Z]{2,6})\\b/<a href=\"mailto:$1\" class=\"web_link\" >$1<\\/a>/ig";
		String user_reg = "s/([\\s|\\.|\\,|\\:|\\xA1|\\xBF\\>|\\{|\\(]?)@{1}(\\w*)([\\.|\\,|\\:|\\!|\\?|\\>|\\}|\\)]?)([\\s]|$)/$1\\<a href=\"\\/user\\?id=$2\" class=\"user_link\"\\>@$2\\<\\/a\\>$3 /ig";
		String trend_reg = "s/([\\s|\\.|\\,|\\:|\\xA1|\\xBF\\>|\\{|\\(]?)#{1}(\\w*)([\\.|\\,|\\:|\\!|\\?|\\>|\\}|\\)]?)([\\s]|$)/$1\\<a href=\"\\/search\\?s=%23$2\" class=\"search_link\"\\>#$2\\<\\/a\\>$3 /ig";
		String shorturl_reg = "m/(href=\\\"http:\\/\\/(bit.ly|j.mp|ff.im)\\/[\\w\\-]{3,10})\\\"/i";

		String rst = perl.substitute(url_reg, text_e);
		rst = perl.substitute(mail_reg, rst);
		rst = perl.substitute(user_reg, rst);
		rst = perl.substitute(trend_reg, rst);

		temp = rst;
		while (perl.match(shorturl_reg, temp)) {
			rst = rst.replace(perl.group(0), "href=\"/expend?u=" + Base64.encode(Base64.encode(perl.group(0).substring(6, perl.group(0).length() - 1).getBytes()).getBytes()) + "\"");
			temp = perl.postMatch();
		}

		temp = text_e;

		rst = "<div class=\"twittertext\">" + rst + "</div>";

		while (perl.match(twitpic_reg, temp)) {
			// rst += "<img src=\"http://twitpic.com/show/thumb/" +
			// perl.group(0).substring(19) + "\" class=\"twitpic\" />";
			rst += "<img src=\"/picthumb?id=" + perl.group(0).substring(19) + "\" class=\"twitpic\" />";
			temp = perl.postMatch();
		}

		temp = text_e;

		while (perl.match(twitgoo_reg, temp)) {
			rst += "<img src=\"" + perl.group(0) + "/thumb\" class=\"twitpic\" />";
			temp = perl.postMatch();
		}

		temp = text_e;

		while (perl.match(imgly_reg, temp)) {
			rst += "<img src=\"http://img.ly/show/thumb/" + perl.group(0).substring(14) + "\" class=\"twitpic\" />";
			temp = perl.postMatch();
		}

		temp = text_e;

		while (perl.match(brizzly_reg, temp)) {
			rst += "<img src=\"http://pics.brizzly.com/thumb_sm_" + perl.group(0).substring(23) + ".jpg\" class=\"twitpic\" />";
			temp = perl.postMatch();
		}

		temp = text_e;

		while (perl.match(owly_reg, temp)) {
			rst += "<img src=\"http://static.ow.ly/photos/thumb/" + perl.group(0).substring(15) + ".jpg\" class=\"twitpic\" />";
			temp = perl.postMatch();
		}

		temp = text_e;

		while (perl.match(picgd_reg, temp)) {
			rst += "<img src=\"http://TweetPhotoAPI.com/api/TPAPI.svc/imagefromurl?size=thumbnail&url=" + perl.group(0).substring(14) + "\" class=\"twitpic\" />";
			temp = perl.postMatch();
		}

		temp = text_e;

		while (perl.match(tweetphoto_reg, temp)) {
			rst += "<img src=\"http://TweetPhotoAPI.com/api/TPAPI.svc/imagefromurl?size=thumbnail&url=" + perl.group(0).substring(22) + "\" class=\"twitpic\" />";
			temp = perl.postMatch();
		}

		temp = text_e;

		while (perl.match(ts1in_reg, temp)) {
			rst += "<img src=\"http://ts1.in/thumb/" + perl.group(0).substring(14) + "\" class=\"twitpic\" />";
			temp = perl.postMatch();
		}

		temp = text_e;

		while (perl.match(moby_reg, temp)) {
			rst += "<img src=\"" + getMobypic(perl.group(0)) + "\" class=\"twitpic\" />";
			temp = perl.postMatch();
		}

		return rst;
	}

	private static String getMobypic(String url) {
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
