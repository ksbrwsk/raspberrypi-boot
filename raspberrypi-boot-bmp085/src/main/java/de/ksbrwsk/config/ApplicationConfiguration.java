package de.ksbrwsk.config;

import de.ksbrwsk.bmp085.Bmp085DataEventPublisher;
import de.ksbrwsk.bmp085.DeviceInformation;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author saborowski
 */
@Configuration
@EnableIntegration
@IntegrationComponentScan
@PropertySource("classpath:/application-${spring.profiles.active}.properties")
public class ApplicationConfiguration implements EnvironmentAware {

    private RelaxedPropertyResolver propertyResolver;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment);
    }

    @Bean
    public DeviceInformation deviceInformation() {
        String deviceId = this.propertyResolver.getProperty("deviceId");
        String deviceLocation = this.propertyResolver.getProperty("deviceLocation");
        return new DeviceInformation(deviceId, deviceLocation);
    }

    @Bean
    public Bmp085DataEventPublisher bmp085DataEventPublisher() {
        return new Bmp085DataEventPublisher();
    }

    @Bean
    public Executor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        return executor;
    }
}
