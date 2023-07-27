package com.ssi.devicemonitor.entity;

import java.util.Date;

public class SwDevice extends GeneralDevice{
    private Date date;

    public Date getDate() {
        return date;
    }
    public SwDevice(String name, String manufacturer, String deviceTpe, String version, Date date) {
        super(name, manufacturer, deviceTpe, version);

        this.date = date;
    }
}

