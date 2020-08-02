package ie.wenderson.ozorio.app.demo.exception;

public class ChipherCryptException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ChipherCryptException(String message) {
		super(message);
	}
	
	public ChipherCryptException(String message, Throwable t) {
		super(message, t);
	}
}
