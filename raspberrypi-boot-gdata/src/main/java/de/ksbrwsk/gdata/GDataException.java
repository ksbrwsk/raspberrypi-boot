package de.ksbrwsk.gdata;

/**
 * @author saborowski
 */
public class GDataException extends Exception {

    public GDataException(Throwable cause) {
        super(cause);
    }

    public GDataException(String message) {
        super(message);
    }
}
