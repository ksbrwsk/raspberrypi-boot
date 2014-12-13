package de.ksbrwsk.bmp085;

import org.springframework.context.ApplicationEvent;

/**
 * event when sensor measures new data.
 *
 * @author saborowski
 */
public class Bmp085DataEvent extends ApplicationEvent {

    private Bmp085Message bmp085Message;

    public Bmp085DataEvent(Object source, Bmp085Message bmp085Message) {
        super(source);
        this.bmp085Message = bmp085Message;
    }

    public Bmp085Message getBmp085Message() {
        return bmp085Message;
    }
}
