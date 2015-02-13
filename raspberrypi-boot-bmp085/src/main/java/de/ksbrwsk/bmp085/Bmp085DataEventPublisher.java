package de.ksbrwsk.bmp085;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

/**
 * @author saborowski
 */
@MessageEndpoint
public class Bmp085DataEventPublisher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    public Bmp085DataEventPublisher() {
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @ServiceActivator(outputChannel = "newBmp085DataEvent")
    public void bmp085DataEvent(Bmp085Message bmp085Message) {
        applicationEventPublisher.publishEvent(new Bmp085DataEvent(this, bmp085Message));
    }
}
