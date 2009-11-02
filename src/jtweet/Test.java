package jtweet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jtweet.web.JTweetServlet;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String directRegex = "^d\\s+([a-z0-9A-Z]+)\\s+([\\S+\\s?]+)$";
		Matcher mt = Pattern.compile(directRegex).matcher(
				"d kkdk f;slkdf ;slkdfls:jkfsdkljf kdjlfsj#43948 ");
		if (mt.find()) {
			System.out.print(mt.group(2));
		}
	}

}
