package jtweet.hack;

import java.io.Serializable;
import java.util.Date;

import jtweet.util.Utils;

import twitter4j.Annotations;
import twitter4j.GeoLocation;
import twitter4j.Tweet;

public final class JTweet implements Tweet, Serializable {

	private static final long serialVersionUID = 7535393937974455775L;

	private final Tweet tweet;

	public JTweet(Tweet tweet) {
		this.tweet = tweet;
	}

	@Override
	public int compareTo(Tweet o) {
		return tweet.compareTo(o);
	}

	@Override
	public String getText() {
		return tweet.getText();
	}

	@Override
	public int getToUserId() {
		return tweet.getToUserId();
	}

	@Override
	public String getToUser() {
		return tweet.getToUser();
	}

	@Override
	public String getFromUser() {
		return tweet.getFromUser();
	}

	@Override
	public long getId() {
		return tweet.getId();
	}

	@Override
	public int getFromUserId() {
		return tweet.getFromUserId();
	}

	@Override
	public String getIsoLanguageCode() {
		return tweet.getIsoLanguageCode();
	}

	@Override
	public String getSource() {
		return tweet.getSource();
	}

	@Override
	public String getProfileImageUrl() {
		return tweet.getProfileImageUrl();
	}

	@Override
	public Date getCreatedAt() {
		return tweet.getCreatedAt();
	}

	@Override
	public GeoLocation getGeoLocation() {
		return tweet.getGeoLocation();
	}

	@Override
	public String getLocation() {
		return tweet.getLocation();
	}

	public String getHumanTime() {
		return Utils.humanReadableString(tweet.getCreatedAt());
	}

	@Override
	public Annotations getAnnotations() {
		return tweet.getAnnotations();
	}
}
