package com.ssi.devicemonitor.entity;

public class HwDevice extends GeneralDevice{
    private String location;
    private String macAddress;


    public String getLocation() {
        return location;
    }
    public String getMacAddress() {
        return macAddress;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setMacAddress(String macAddress) {
        this.location = macAddress;
    }
    public HwDevice(String name, String manufacturer, String deviceTpe,String version, String location, String macAddress) {
        super(name, manufacturer, deviceTpe, version);

        this.location = location;
        this.macAddress = macAddress;
    }
}
