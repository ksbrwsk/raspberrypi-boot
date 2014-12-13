package de.ksbrwsk;

import de.ksbrwsk.config.ApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * @author saborowski
 */
@SpringBootApplication
@Import(value = {ApplicationConfiguration.class })
@ImportResource(value = { "classpath:/raspberrypi-bmp085-integration.xml" })
public class BootstrapBmp085
{

    public static void main(String args[])
    {
        SpringApplication.run(BootstrapBmp085.class, args);
    }
}
