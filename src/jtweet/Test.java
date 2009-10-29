package jtweet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		String text = "やっぱりうまい http://twitpic.com/ndnvw";
//		String regex = "http://twitpic\\.com\\/([\\w]{5})\\s?";
//		Matcher mt = Pattern.compile(regex).matcher(text);
//		if(mt.find()){
//			System.out.println(mt.group());
//		}
		if("http://is.gd/4H6uX".matches(".*is\\.gd\\/.*")){
			System.out.println("yes");
		}
	}

}
