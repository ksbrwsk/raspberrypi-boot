package de.ksbrwsk.pushover;


/**
 * @author saborowski
 */
public class TemperatureMessage {

    private String deviceId;
    private String deviceLocation;
    private double degreesInCelsius;
    private double pressureInHPa;
    private double heightInMeter;
    private String measuredAt;

    public TemperatureMessage() {
    }

    public void setDegreesInCelsius(double degreesInCelsius) {
        this.degreesInCelsius = degreesInCelsius;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setDeviceLocation(String deviceLocation) {
        this.deviceLocation = deviceLocation;
    }

    public void setHeightInMeter(double heightInMeter) {
        this.heightInMeter = heightInMeter;
    }

    public void setMeasuredAt(String measuredAt) {
        this.measuredAt = measuredAt;
    }

    public void setPressureInHPa(double pressureInHPa) {
        this.pressureInHPa = pressureInHPa;
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

    public String getMeasuredAt() {
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
