package jtweet;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import jtweet.Exception.InvaildUrlFormatException;
import jtweet.Exception.ShorturlNotFoundException;
import jtweet.gae.GCache;
import jtweet.gae.GStore;



@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class ShortUrl implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1611309132485997222L;
	public static final String base = "CeKhUvJ8ARyL_ZSMPTNxuX50bt69EpHWszld3BIQq2gVGDfYir4F7ajkom1ncwO";
	/**
	 * 
	 */

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String longUrl;

	@Persistent
	private String creator = "sys";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ShortUrl(String longUrl) {
		super();
		this.longUrl = longUrl.trim();
	}

	public ShortUrl(String longUrl, String creator) {
		super();
		this.longUrl = longUrl.trim();
		this.creator = creator;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

	public static ShortUrl get(String shortPart) throws ShorturlNotFoundException {
		long id = ShortUrl.getIDFromShortenedURL(shortPart);
		ShortUrl url = (ShortUrl) GCache.get("shorturl:" + id);
		if (url == null) {
			url = GStore.getShortUrl(id);
			if (url == null) {
				throw new ShorturlNotFoundException();
			}
			GCache.put("url:" + id, url);
		}
		return url;
	}

	private static String add(String longurl) {
		ShortUrl su = GStore.getShortUrlByUrl(longurl.trim());
		if (su == null) {
			su = new ShortUrl(longurl);
			su = (ShortUrl) GStore.SaveAndGet(su);
		}
		return ShortUrl.getShortenedURLFromID(su.getId());
	}

	private static String add(String longurl, String owner) {
		ShortUrl su = GStore.getShortUrlByUrl(longurl.trim());
		if (su == null) {
			su = new ShortUrl(longurl, owner);
			su = (ShortUrl) GStore.SaveAndGet(su);
		}
		return ShortUrl.getShortenedURLFromID(su.getId());
	}

	public static String processUrl(String longurl,String urlPrefix) throws InvaildUrlFormatException {
		String regex = "([A-Za-z]+://[A-Za-z0-9-,_]+\\.[A-Za-z0-9-_,:%&\\?\\/.#=\\+]+)";// link
		Matcher mt = Pattern.compile(regex).matcher(longurl);
		if(mt.find()) {
			return "http://"+urlPrefix+"/i/"+ShortUrl.add(mt.group());
		}else{
			throw new InvaildUrlFormatException();
		}
	}
	public static String processUrl(String longurl,String urlPrefix,String username) throws InvaildUrlFormatException {
		String regex = "([A-Za-z]+://[A-Za-z0-9-,_]+\\.[A-Za-z0-9-_,:%&\\?\\/.#=\\+]+)";// link
		Matcher mt = Pattern.compile(regex).matcher(longurl);
		if(mt.find()) {
			return "http://"+urlPrefix+"/i/"+ShortUrl.add(mt.group(),username);
		}else{
			throw new InvaildUrlFormatException();
		}
	}
	

	private static long getIDFromShortenedURL(String shortUrl) {
		int length = ShortUrl.base.length();
		char[] string = shortUrl.toCharArray();
		int size = shortUrl.length() - 1;
		int out = 0;
		for (int i = 0; i < size + 1; i++) {
			int temp = ShortUrl.base.indexOf(string[i]);
			out += temp * Math.pow(length, size - i);
		}
		return out;
	}

	private static String getShortenedURLFromID(long integer) {
		int length = ShortUrl.base.length();
		String out = "";
		while (integer > length - 1) {
			out = ShortUrl.base.charAt((int) Math.floor(integer % length))
					+ out;
			integer = (long) Math.floor(integer / length);
		}
		return ShortUrl.base.charAt((int) integer) + out;

	}
}
