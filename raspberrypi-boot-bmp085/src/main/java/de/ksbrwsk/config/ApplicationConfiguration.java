package de.ksbrwsk.config;

import de.ksbrwsk.bmp085.Bmp085AsyncUncaughtExceptionHandler;
import de.ksbrwsk.bmp085.Bmp085DataEventPublisher;
import de.ksbrwsk.bmp085.DeviceInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author saborowski
 */
@Configuration
@EnableIntegration
@IntegrationComponentScan
public class ApplicationConfiguration implements AsyncConfigurer, EnvironmentAware {

    private final Logger log = LoggerFactory.getLogger(ApplicationConfiguration.class);

    private RelaxedPropertyResolver propertyResolver;

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

    public Executor getAsyncExecutor() {
        log.debug("creating async task Executor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(10000);
        executor.setThreadNamePrefix("bmp085-Executor-");
        executor.initialize();
        return executor;
    }

    /**
     * The {@link org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler} instance to be used
     * when an exception is thrown during an asynchronous method execution
     * with {@code void} return type.
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new Bmp085AsyncUncaughtExceptionHandler();
    }
}
