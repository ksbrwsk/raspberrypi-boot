package de.ksbrwsk;

import de.ksbrwsk.config.ApplicationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * @author saborowski
 */
@SpringBootApplication
@Import(value = {ApplicationConfiguration.class })
public class BootstrapBmp085 {

    private final static Logger LOGGER = LoggerFactory.getLogger(BootstrapBmp085.class);

    public static void main(String[] args) {
        SpringApplication.run(BootstrapBmp085.class, args);
        LOGGER.info("BMP085 Reader started.");
    }

}
