package de.ksbrwsk.pushover;


/**
 * @author saborowski
 */
public class PushoverExcpeption extends Exception {

    public PushoverExcpeption(String message) {
        super(message);
    }

    public PushoverExcpeption(Throwable cause) {
        super(cause);
    }
}
