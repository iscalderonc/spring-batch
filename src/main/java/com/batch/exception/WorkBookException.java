package com.batch.exception;

public class WorkBookException extends Exception {
	
private static final long serialVersionUID = 1L;
	
	public String classname;
	
	/**
     * Constructs an <code>WorkBookException</code> with no 
     * detail message. 
     */
	public WorkBookException() {
		super();
	}
	
	 /**
     * Report an InvalidClassException for the reason specified.
     *
     * @param message  String describing the reason for the exception.
     */
	public WorkBookException(String message) {
		super(message);
	}
	
	 /**
     * Report an InvalidClassException for the reason specified with a code error
     *
     * @param errorCode error code, util for put code errors for a more guide of trace
     * @param message  String describing the reason for the exception.
     */
	public WorkBookException(String errorCode, String message) {
		super("["+errorCode+"] " + message);
	}
	
	/**
     * Constructs a new runtime exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * <code>cause</code> is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link #getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @since  1.4
     */
	public WorkBookException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Constructs a new runtime exception with the specified code error, detail message and
     * cause.  <p>Note that the detail message associated with
     * <code>cause</code> is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.C
	 * @param errorCode error code, util for put code errors for a more guide of trace
	 * @param message message the detail message (which is saved for later retrieval
     *         by the {@link #getMessage()} method).
	 * @param cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
	 */
	public WorkBookException(String errorCode, String message,  Throwable cause) {
		super("["+errorCode+"] " + message, cause);
	}
	
	/**
     * Produce the message and include the classname, if present.
     */
    public String getMessage() {
		if (classname == null) {
			return super.getMessage();
		} else {
			return classname + "; " + super.getMessage();
		}
    }

}
