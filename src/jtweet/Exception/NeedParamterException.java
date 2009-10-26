package jtweet.Exception;

public class NeedParamterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5141098119153632027L;
@Override
public String getMessage() {
	return "request paramters is missed.";
}
}
