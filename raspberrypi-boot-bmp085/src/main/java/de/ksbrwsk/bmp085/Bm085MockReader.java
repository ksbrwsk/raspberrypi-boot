package de.ksbrwsk.bmp085;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

/**
 * a mock reader class for use in development.
 *
 * @author saborowski
 */
@Component
@Profile(value={"dev", "sabo"})
public class Bm085MockReader implements Bmp085Reader {

    private final static Logger LOGGER = LoggerFactory.getLogger(Bm085MockReader.class);

    /**
     * the device information containing device id and location.
     */
    private DeviceInformation deviceInformation;

    /**
     * the event publisher for the {@link de.ksbrwsk.bmp085.Bmp085DataEvent}
     */
    private Bmp085DataEventPublisher bmp085DataEventPublisher;

    /**
     * executor running the Bmp085Listener
     */
    private Executor executor;

    private boolean running = false;

    @Autowired
    public Bm085MockReader(DeviceInformation deviceInformation, Executor executor, Bmp085DataEventPublisher bmp085DataEventPublisher) {
        this.executor = executor;
        this.bmp085DataEventPublisher = bmp085DataEventPublisher;
        this.deviceInformation = deviceInformation;
        try {
            this.connect();
        } catch (Bmp085ReaderException e) {
            LOGGER.error("Error connecting the mock reader", e);
        }
    }

    @Override
    public void connect() throws Bmp085ReaderException {
        LOGGER.info("Connecting BMP085 reader");
        this.running = true;
        this.startListening();
    }

    @Override
    public void disconnect() throws Bmp085ReaderException {
        LOGGER.info("Disconnecting BMP085 reader");
        this.running = false;
    }

    @Override
    public void startListening() {
        this.executor.execute(new Bmp085Mocklistener());
    }

    /**
     * Listener creating a Bmp085DataEvent every 3 seconds.
     *
     * @author saborowski
     */
    private class Bmp085Mocklistener implements Runnable {

        @Override
        public void run() {
            while (running) {
                double temperature = randomDouble(19.7, 23.2);
                double pressure = randomDouble(996.0, 1005.2);
                Bmp085Message message = new Bmp085Message(deviceInformation.getDeviceId(), deviceInformation.getLocation(),
                        temperature, pressure, 180.0);
                bmp085DataEventPublisher.bmp085DataEvent(message);

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    LOGGER.error("thread interrupted", e);

                }
            }
        }

        /**
         * generate a random double value.
         *
         * @param low  the minimum value
         * @param high the max value
         * @return a new random double value
         */
        public double randomDouble(double low, double high) {
            return Math.random() * (high - low) + low;
        }

    }
}
