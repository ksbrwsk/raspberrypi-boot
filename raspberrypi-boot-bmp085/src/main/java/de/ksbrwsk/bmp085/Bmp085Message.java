package de.ksbrwsk.bmp085;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Calendar;
import java.util.Date;

public class Bmp085Message {

    private String deviceId;
    private String deviceLocation;
    private double degreesInCelsius;
    private double pressureInHPa;
    private double heightInMeter;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date measuredAt;

    public Bmp085Message(String deviceId, String deviceLocation, double degreesInCelsius, double pressureInHPa, double heightInMeter) {
        this.degreesInCelsius = 0.0D;
        this.pressureInHPa = 0.0D;
        this.heightInMeter = 0.0D;
        this.degreesInCelsius = degreesInCelsius;
        this.deviceId = deviceId;
        this.deviceLocation = deviceLocation;
        this.pressureInHPa = pressureInHPa;
        this.heightInMeter = heightInMeter;
        this.measuredAt = Calendar.getInstance().getTime();
    }

    public String getDeviceLocation() {
        return deviceLocation;
    }

    public double getDegreesInCelsius() {
        return degreesInCelsius;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Date getMeasuredAt() {
        return measuredAt;
    }

    public double getHeightInMeter() {
        return heightInMeter;
    }

    public double getPressureInHPa() {
        return pressureInHPa;
    }

    public String toString() {
        return (new StringBuilder()).append("Bmp085Message{deviceId='")
                .append(deviceId).append('\'')
                .append(", deviceLocation='")
                .append(deviceLocation)
                .append('\'')
                .append(", degreesInCelsius=")
                .append(degreesInCelsius)
                .append(", pressureInHPa=")
                .append(pressureInHPa)
                .append(", heightInMeter=")
                .append(heightInMeter)
                .append(", measuredAt=")
                .append(measuredAt)
                .append('}')
                .toString();
    }
}
