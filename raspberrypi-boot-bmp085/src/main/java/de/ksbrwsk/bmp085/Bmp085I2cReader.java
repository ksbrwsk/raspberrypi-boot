package de.ksbrwsk.bmp085;

import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.Executor;

/**
 * reading the bmp085 sensor data
 *
 * @author saborowski
 */
@Component
@Profile({"prod", "sabo"})
public class Bmp085I2cReader implements Bmp085Reader {

    public static final int BMP085_I2C_ADDR = 119;
    public static final int RASPI_BUS_ID = 1;
    public static final int OSS = 3;
    public static final int CAL_AC1 = 170;
    public static final int CAL_AC2 = 172;
    public static final int CAL_AC3 = 174;
    public static final int CAL_AC4 = 176;
    public static final int CAL_AC5 = 178;
    public static final int CAL_AC6 = 180;
    public static final int CAL_B1 = 182;
    public static final int CAL_B2 = 184;
    public static final int CAL_MB = 186;
    public static final int CAL_MC = 188;
    public static final int CAL_MD = 190;
    public static final int CONTROL = 244;
    public static final int DATA_REG = 246;
    public static final byte READTEMPCMD = 46;
    public static final int READPRESSURECMD = 244;
    private static Logger LOGGER = LoggerFactory.getLogger(Bmp085I2cReader.class);
    private boolean running = false;
    private Bmp085DataEventPublisher bmp085DataEventPublisher;
    private Executor executor;
    private DeviceInformation deviceInformation;
    private I2CDevice bmp085device;

    private int cal_AC1 = 0;
    private int cal_AC2 = 0;
    private int cal_AC3 = 0;
    private int cal_AC4 = 0;
    private int cal_AC5 = 0;
    private int cal_AC6 = 0;
    private int cal_B1 = 0;
    private int cal_B2 = 0;
    private int cal_MB = 0;
    private int cal_MC = 0;
    private int cal_MD = 0;

    @Autowired
    public Bmp085I2cReader(DeviceInformation deviceInformation, Executor executor, Bmp085DataEventPublisher bmp085DataEventPublisher) {
        running = false;
        this.bmp085DataEventPublisher = bmp085DataEventPublisher;
        this.executor = executor;
        this.deviceInformation = deviceInformation;
        try {
            LOGGER.info("opening I2c Factory");
            bmp085device = I2CFactory.getInstance(RASPI_BUS_ID).getDevice(BMP085_I2C_ADDR);
            connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readCalibrationData() throws IOException {
        cal_AC1 = readS16(CAL_AC1);
        cal_AC2 = readS16(CAL_AC2);
        cal_AC3 = readS16(CAL_AC3);
        cal_AC4 = readU16(CAL_AC4);
        cal_AC5 = readU16(CAL_AC5);
        cal_AC6 = readU16(CAL_AC6);
        cal_B1 = readS16(CAL_B1);
        cal_B2 = readS16(CAL_B2);
        cal_MB = readS16(CAL_MB);
        cal_MC = readS16(CAL_MC);
        cal_MD = readS16(CAL_MD);
    }

    private int readU16(int address) throws IOException {
        int hibyte = bmp085device.read(address);
        return (hibyte << 8) + bmp085device.read(address + 1);
    }

    private int readS16(int address) throws IOException {
        int hibyte = bmp085device.read(address);
        if (hibyte > 127)
            hibyte -= 256;
        return hibyte * 256 + bmp085device.read(address + 1);
    }

    private int readU8(int address) throws IOException {
        return bmp085device.read(address);
    }

    private Bmp085Message convertPressureTemp(int rawPressure, int rawTemperature) {
        double temperature;
        double pressure;

        double x1 = ((rawTemperature - cal_AC6) * cal_AC5) / 32768;
        double x2 = (double) (cal_MC * 2048) / (x1 + (double) cal_MD);
        double b5 = x1 + x2;
        temperature = (b5 + 8D) / 16D / 10D;
        double b6 = b5 - 4000D;
        x1 = ((double) cal_B2 * ((b6 * b6) / 4096D)) / 2048D;
        x2 = ((double) cal_AC2 * b6) / 2048D;
        double x3 = x1 + x2;
        double b3 = (((double) (cal_AC1 * 4) + x3) * Math.pow(2D, 3D) + 2D) / 4D;
        x1 = ((double) cal_AC3 * b6) / 8192D;
        x2 = ((double) cal_B1 * ((b6 * b6) / 4096D)) / 65536D;
        x3 = (x1 + x2 + 2D) / 4D;
        double b4 = ((double) cal_AC4 * (x3 + 32768D)) / 32768D;
        double b7 = ((double) rawPressure - b3) * (50000D / Math.pow(2D, 3D));

        if (b7 < -2147483648D) {
            pressure = (b7 * 2D) / b4;
        } else {
            pressure = (b7 / b4) * 2D;
        }

        x1 = (pressure / 256D) * (pressure / 256D);
        x1 = (x1 * 3038D) / 65536D;
        x2 = (-7375D * pressure) / 65536D;

        double pressureTmp = pressure + (x1 + x2 + 3791D) / 16D;
        double pressureHpa = pressureTmp / 100D;
        return new Bmp085Message(deviceInformation.getDeviceId(), deviceInformation.getLocation(), temperature, pressureHpa, 0.0D);
    }

    public void connect() throws Bmp085ReaderException {
        try {
            LOGGER.info("Connecting Sensor");
            readCalibrationData();
            startListening();
            running = true;
        } catch (IOException e) {
            throw new Bmp085ReaderException(e);
        }
    }

    public void disconnect() throws Bmp085ReaderException {
        try {
            running = false;
            I2CFactory.getInstance(1).close();
        } catch (IOException | I2CFactory.UnsupportedBusNumberException e) {
            throw new Bmp085ReaderException(e);
        }
    }

    public void startListening() {
        executor.execute(new Bmp085Listener());
    }

    private class Bmp085Listener implements Runnable {
        final Bmp085I2cReader this$0;

        public Bmp085Listener() {
            this$0 = Bmp085I2cReader.this;
        }

        public void run() {
            LOGGER.info("BMP085 starts listening...");
            try {
                while (running) {
                    bmp085device.write(READPRESSURECMD, READTEMPCMD);
                    Thread.sleep(50L);
                    int rawTemperature = readU16(DATA_REG);
                    bmp085device.write(CONTROL, (byte) -12);
                    Thread.sleep(50L);
                    int msb = readU8(DATA_REG);
                    int lsb = readU8(247);
                    int xlsb = readU8(248);
                    int rawPressure = (msb << 16) + (lsb << 8) + xlsb >> 5;

                    Bmp085Message message = convertPressureTemp(rawPressure, rawTemperature);
                    LOGGER.info("Publish new temperature message -> {}", message.toString());
                    bmp085DataEventPublisher.bmp085DataEvent(message);
                    Thread.sleep(20000L);
                }
            } catch (Exception e) {
                Bmp085I2cReader.LOGGER.error("Error reading sensor data", e);
            }
        }
    }
}
