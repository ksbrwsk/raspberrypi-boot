package de.ksbrwsk.bmp085;

/**
 * @author saborowski
 */
public class DeviceInformation {

    private String deviceId;
    private String location;

    public DeviceInformation(String deviceId, String location) {
        this.deviceId = deviceId;
        this.location = location;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getLocation() {
        return location;
    }
}
