package com.mobitill.barandrestaurant.data.request.remotemodels.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderRemoteStatus {

    @SerializedName("proucts")
    @Expose
    private Integer proucts;

    public Integer getProucts() {
        return proucts;
    }

    public void setProucts(Integer proucts) {
        this.proucts = proucts;
    }

}