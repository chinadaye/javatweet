package jtweet.Exception;

public class ShorturlNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7783547851539852873L;
@Override
public String getMessage() {
	return "Shorturl Not Found";
}
}
