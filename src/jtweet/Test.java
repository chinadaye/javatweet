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
		for (int i = 0; i < 100; i++) {
			System.out.println(JTweetServlet.getRandomBaseUrl());
		}
	
	}

}
