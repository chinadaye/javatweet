package jtweet.hack;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Tweet;

public class TweetHelper {
	public static List<JTweet> parseTweets(List<Tweet> tweets) {
		List<JTweet> jTweets = new ArrayList<JTweet>(tweets.size());
		for (Tweet tweet : tweets) {
			JTweet jTweet = new JTweet(tweet);
			jTweets.add(jTweet);
		}

		return jTweets;
	}
}
