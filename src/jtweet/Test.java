package jtweet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jtweet.web.JTweetServlet;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(Math.sqrt(Integer.valueOf("EDFEFF", 16).intValue()));
		System.out.println(Integer.valueOf("DDEEF6", 16).intValue());
		System.out.println(Integer.valueOf("EDFEFF", 16).floatValue()/Integer.valueOf("DDEEF6", 16).floatValue());
		System.out.println(Integer.valueOf("AB65C5", 16).intValue());
		System.out.println(Integer.valueOf("934DAD", 16).intValue());
		System.out.println(Integer.valueOf("AB65C5", 16).floatValue()/Integer.valueOf("934DAD", 16).floatValue());
		System.out.println(Integer.valueOf("B1F44B", 16).intValue());
		System.out.println(Integer.valueOf("99CC33", 16).intValue());
		System.out.println(Integer.valueOf("B1F44B", 16).floatValue()/Integer.valueOf("99CC33", 16).floatValue());
	}

}
