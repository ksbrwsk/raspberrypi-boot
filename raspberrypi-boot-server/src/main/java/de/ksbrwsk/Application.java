package de.ksbrwsk;

import de.ksbrwsk.config.WebSocketConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author saborowski
 */
@SpringBootApplication
@Import(value = {WebSocketConfig.class})
public class Application
{
    public static void main(String args[])
    {
        SpringApplication.run(Application.class, args);
    }
}
