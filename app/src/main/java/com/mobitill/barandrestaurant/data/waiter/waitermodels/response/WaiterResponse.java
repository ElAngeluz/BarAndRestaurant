package com.mobitill.barandrestaurant.data.waiter.waitermodels.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WaiterResponse {

    @SerializedName("data")
    @Expose
    private List<Waiter> data = null;

    public List<Waiter> getData() {
        return data;
    }

    public void setData(List<Waiter> data) {
        this.data = data;
    }

}
