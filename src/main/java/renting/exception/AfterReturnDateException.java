package renting.exception;

public class AfterReturnDateException extends RentingException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	public AfterReturnDateException() {
		super();
	}

	public AfterReturnDateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AfterReturnDateException(String message, Throwable cause) {
		super(message, cause);
	}

	public AfterReturnDateException(String message) {
		super(message);
	}

	public AfterReturnDateException(Throwable cause) {
		super(cause);
	}
	
	
}
