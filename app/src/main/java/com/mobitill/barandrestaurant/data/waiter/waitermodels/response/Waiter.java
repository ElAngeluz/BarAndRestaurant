package com.mobitill.barandrestaurant.data.waiter.waitermodels.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Waiter {

    private static final long STALE_MS = 5 * 1000;

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("pin")
    @Expose
    private String pin;

    final long timestamp;

    public Waiter() {
        this.timestamp = System.currentTimeMillis();
    }

    public Waiter(String id, String name, String pin) {
        this.id = id;
        this.name = name;
        this.pin = pin;
        timestamp = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public boolean isUpToDate(){
        return System.currentTimeMillis() - timestamp < STALE_MS;
    }

}