package jtweet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import twitter4j.TweetParser;

import jtweet.web.JTweetServlet;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String text = "RT @ifire 下周去杭州，参观#下一周岁的虾米网，#fjjfj @wingoffire 请洗白白 // 欢迎 @ifir老湿光临指导";
		System.out.print(TweetParser.parseTextJava(text));
	}

}
