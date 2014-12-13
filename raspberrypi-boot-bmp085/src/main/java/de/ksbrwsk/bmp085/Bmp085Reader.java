package de.ksbrwsk.bmp085;


public interface Bmp085Reader {

    public abstract void connect() throws Bmp085ReaderException;

    public abstract void disconnect() throws Bmp085ReaderException;

    public abstract void startListening();
}
