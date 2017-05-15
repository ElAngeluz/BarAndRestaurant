package com.mobitill.barandrestaurant.data.product.models.pos.payload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class POSRequest {

    @SerializedName("requestId")
    @Expose
    private String requestId;
    @SerializedName("requestname")
    @Expose
    private String requestname;
    @SerializedName("productsVersion")
    @Expose
    private Integer productsVersion;
    @SerializedName("requestbody")
    @Expose
    private Requestbody requestbody;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestname() {
        return requestname;
    }

    public void setRequestname(String requestname) {
        this.requestname = requestname;
    }

    public Integer getProductsVersion() {
        return productsVersion;
    }

    public void setProductsVersion(Integer productsVersion) {
        this.productsVersion = productsVersion;
    }

    public Requestbody getRequestbody() {
        return requestbody;
    }

    public void setRequestbody(Requestbody requestbody) {
        this.requestbody = requestbody;
    }


}