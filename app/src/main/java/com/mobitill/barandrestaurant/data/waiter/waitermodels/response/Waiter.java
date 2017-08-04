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

//    phone is used as a user's unique identifier LIKE your PIN
    @SerializedName("phone")
    @Expose
    private String phone;

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    final long timestamp;

    public Waiter() {
        this.timestamp = System.currentTimeMillis();
    }

    public Waiter(String id, String name, String phone,String password) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.password = password;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isUpToDate(){
        return System.currentTimeMillis() - timestamp < STALE_MS;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}