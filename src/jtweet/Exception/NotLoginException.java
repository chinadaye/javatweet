package jtweet.Exception;

public class NotLoginException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6714067442592613930L;
@Override
public String getMessage() {
	
	return "Not login";
}
}
