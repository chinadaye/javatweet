package jtweet.hack;

import java.io.Serializable;
import java.util.Date;

import jtweet.util.Utils;
import twitter4j.Annotations;
import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.Place;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;

public class JStatus implements Status, Serializable {
	private static final long serialVersionUID = -2056720393048797997L;
	private Status status;

	public JStatus(Status status) {
		this.status = status;
	}

	@Override
	public int compareTo(Status o) {
		return status.compareTo(o);
	}

	@Override
	public RateLimitStatus getRateLimitStatus() {
		return status.getRateLimitStatus();
	}

	@Override
	public Date getCreatedAt() {
		return status.getCreatedAt();
	}

	@Override
	public long getId() {
		return status.getId();
	}

	@Override
	public String getText() {
		return status.getText();
	}

	@Override
	public String getSource() {
		return status.getSource();
	}

	@Override
	public boolean isTruncated() {
		return status.isTruncated();
	}

	@Override
	public long getInReplyToStatusId() {
		return status.getInReplyToStatusId();
	}

	@Override
	public long getInReplyToUserId() {
		return status.getInReplyToUserId();
	}

	@Override
	public String getInReplyToScreenName() {
		return status.getInReplyToScreenName();
	}

	@Override
	public GeoLocation getGeoLocation() {
		return status.getGeoLocation();
	}

	@Override
	public Place getPlace() {
		return status.getPlace();
	}

	@Override
	public boolean isFavorited() {
		return status.isFavorited();
	}

	@Override
	public User getUser() {
		return status.getUser();
	}

	@Override
	public boolean isRetweet() {
		return status.isRetweet();
	}

	@Override
	public Status getRetweetedStatus() {
		return status.getRetweetedStatus();
	}

	@Override
	public String[] getContributors() {
		return status.getContributors();
	}

	public JStatus getRetweetedJStatus() {
		return new JStatus(status.getRetweetedStatus());
	}

	public String getHumanTime() {
		return Utils.humanReadableString(status.getCreatedAt());
	}

	@Override
	public Annotations getAnnotations() {
		return status.getAnnotations();
	}

	@Override
	public HashtagEntity[] getHashtagEntities() {
		return status.getHashtagEntities();
	}

	@Override
	public long getRetweetCount() {
		return status.getRetweetCount();
	}

	@Override
	public URLEntity[] getURLEntities() {
		return status.getURLEntities();
	}

	@Override
	public UserMentionEntity[] getUserMentionEntities() {
		return status.getUserMentionEntities();
	}

	@Override
	public boolean isRetweetedByMe() {
		return status.isRetweetedByMe();
	}
}
