package jtweet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jtweet.web.JTweetServlet;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String regex = "#([^\\s^，^,^\\/]{1,20})";
		Matcher mt = Pattern.compile(regex).matcher("#Treat叫大家/，Twitter的万圣节彩蛋-");
		while(mt.find()){
			System.out.print(mt.group(1));
		}
	}

}
