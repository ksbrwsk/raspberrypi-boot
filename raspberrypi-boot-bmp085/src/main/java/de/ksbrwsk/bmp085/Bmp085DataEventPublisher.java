package de.ksbrwsk.bmp085;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

public class Bmp085DataEventPublisher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    public Bmp085DataEventPublisher() {
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void bmp085DataEvent(Bmp085Message bmp085Message) {
        applicationEventPublisher.publishEvent(new Bmp085DataEvent(this, bmp085Message));
    }
}
