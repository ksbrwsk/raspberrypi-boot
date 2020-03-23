package de.ksbrwsk;

import de.ksbrwsk.config.PushoverConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;

/**
 * @author saborowski
 */
@SpringBootApplication
@Import(value = {PushoverConfiguration.class })
public class PushoverApplication {

    private final static Logger LOGGER = LoggerFactory.getLogger(PushoverApplication.class);

    public static void main(String args[]) {
        new SpringApplicationBuilder(PushoverApplication.class).run(args);
        LOGGER.info("PushoverApplication started.");
    }
}