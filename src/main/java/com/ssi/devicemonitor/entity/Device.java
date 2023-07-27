package com.ssi.devicemonitor.entity;

public abstract class Device {
    private String name;
    private String status;

    public Device(String name) {
        this.name = name;
        this.status = "Offline"; // Set initial status to Offline
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public abstract String getDeviceType();
    public void setStatus(String status) {
        this.status = status;
    }
}
