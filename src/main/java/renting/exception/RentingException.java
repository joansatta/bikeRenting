package renting.exception;

public class RentingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RentingException() {
		super();
	}

	public RentingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RentingException(String message, Throwable cause) {
		super(message, cause);
	}

	public RentingException(String message) {
		super(message);
	}

	public RentingException(Throwable cause) {
		super(cause);
	}
	
	
}
