package org.usfirst.frc3467.subsystems.PixyCam;

/**
 * This exception represents the case where a vision target was not found.
 */
@SuppressWarnings("serial")
public class NoTargetException extends RuntimeException {

	/**
	 * Create a new exception with the given message.
	 *
	 * @param message the message to attach to the exception
	 */
	public NoTargetException(String message) {
	  super(message);
	}

	public String getMessage() {
		return "No Target Found!";
	}

}
