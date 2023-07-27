package com.ssi.devicemonitor.entity;

public class GeneralDevice extends Device {
    private String manufacturer;
    private String deviceType;
    private String version;
    public String getManufacturer() {
        return manufacturer;
    }

    public String getDeviceType() {
        return deviceType;
    }
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    public void setDeviceType(String deviceType) {
        this.version = deviceType;
    }

    public GeneralDevice(String name) {
        super(name);
    }
    public GeneralDevice(String name,  String manufacturer, String deviceTpe, String version) {

        super(name);

        this.manufacturer = manufacturer;
        this.deviceType = deviceTpe;
        this.version = version;
    }
}
