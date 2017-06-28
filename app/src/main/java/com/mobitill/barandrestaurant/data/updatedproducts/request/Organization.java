package com.mobitill.barandrestaurant.data.updatedproducts.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dataintegrated on 6/27/2017.
 */

public class Organization {
     @SerializedName("id")
    private String Id;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
