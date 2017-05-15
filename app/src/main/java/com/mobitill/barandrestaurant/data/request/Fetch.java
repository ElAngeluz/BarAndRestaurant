package com.mobitill.barandrestaurant.data.request;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fetch {

    @SerializedName("appid")
    @Expose
    private String appid;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

}
