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
@ImportResource(value = { "classpath:/raspberrypi-bmp085-integration.xml" })
public class BootstrapBmp085 {

    private final static Logger LOGGER = LoggerFactory.getLogger(BootstrapBmp085.class);

    public static void main(String args[]) {
        new SpringApplicationBuilder(BootstrapBmp085.class).web(false).run(args);
        LOGGER.info("BMP085 Reader started.");
    }

}
