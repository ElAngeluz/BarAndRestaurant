package com.mobitill.barandrestaurant.data.order.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by andronicus on 5/16/2017.
 */

public class Order {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("waiterId")
    @Expose
    private String waiterId;

    @SerializedName("synced")
    @Expose
    private String synced;

    @SerializedName("checkedOut")
    @Expose
    private String checkedOut;


    public Order() {
    }

    public Order(String id, String name, String waiterId) {
        this.id = id;
        this.name = name;
        this.waiterId = waiterId;
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

    public String getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(String waiterId) {
        this.waiterId = waiterId;
    }
}
