package de.ksbrwsk;

import de.ksbrwsk.config.GDataConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;

/**
 * @author saborowski
 */
@SpringBootApplication
@Import(value = {GDataConfiguration.class })
public class GDataApplication {

    private final static Logger LOGGER = LoggerFactory.getLogger(GDataApplication.class);

    public static void main(String args[]) {
        new SpringApplicationBuilder(GDataApplication.class).web(false).run(args);
        LOGGER.info("GDataApplication started.");
    }
}