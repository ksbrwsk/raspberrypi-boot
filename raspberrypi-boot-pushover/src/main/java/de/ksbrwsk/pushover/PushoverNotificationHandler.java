package de.ksbrwsk.pushover;

import net.pushover.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

/**
 * @author saborowski
 */
@MessageEndpoint
public class PushoverNotificationHandler {

    private static Logger logger = LoggerFactory.getLogger(PushoverNotificationHandler.class);

    private PushoverProperties pushoverProperties;

    @Autowired
    public PushoverNotificationHandler(PushoverProperties pushoverProperties) {
        this.pushoverProperties = pushoverProperties;
    }

    /**
     * Send Pushover Notification if temperature is less than 20.0 degress celsius
     * or greater than 23.0 degress celsius.
     *
     * @param temperatureMessage the temperature data
     */
    @ServiceActivator(inputChannel = "temperatureMessageData")
    public void handleTemperatureMessage(TemperatureMessage temperatureMessage) {
        if(temperatureMessage.getDegreesInCelsius() < 20.0 ||
                temperatureMessage.getDegreesInCelsius() >= 23.0) {


            PushoverClient client = new PushoverRestClient();
            String message = temperatureMessage.toString();
            Status result = null;
            try {
                result = client.pushMessage(PushoverMessage.builderWithApiToken(pushoverProperties.getApiToken())
                        .setUserId(pushoverProperties.getUserId())
                        .setMessage(message)
                        .setPriority(MessagePriority.HIGH) // HIGH|NORMAL|QUIET
                        .setTitle(pushoverProperties.getTitle())
                        .setUrl(pushoverProperties.getUrl())
                        .setTitleForURL(pushoverProperties.getTitleForURL())
                        .setSound(pushoverProperties.getSound())
                        .build());
            } catch (PushoverException e) {
                logger.error("could not create Pushover message. ", e);
            }
            logger.info("could not create Pushover message: {}", result.toString());
        }
    }

}
