package de.ksbrwsk.bmp085;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * hanlder for uncaught asynchrones exceptions.
 *
 * @author saborowski
 */
public class Bmp085AsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(Bmp085AsyncUncaughtExceptionHandler.class);

    /**
     * Handle the given uncaught exception thrown from an asynchronous method.
     *
     * @param ex     the exception thrown from the asynchronous method
     * @param method the asynchronous method
     * @param params the parameters used to invoked the method
     */
    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        log.error("handling uncaught exception", ex);
        //TODO: handling asynchrones excpetions
    }
}
