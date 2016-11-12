package de.ksbrwsk.bmp085;

/**
 * @author saborowski
 */
public interface Bmp085Reader {

    void connect() throws Bmp085ReaderException;

    void disconnect() throws Bmp085ReaderException;

    void startListening();
}
