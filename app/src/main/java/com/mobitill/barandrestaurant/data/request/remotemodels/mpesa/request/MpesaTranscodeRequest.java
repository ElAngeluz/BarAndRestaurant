package com.mobitill.barandrestaurant.data.request.remotemodels.mpesa.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dataintegrated on 6/12/2017.
 */

public class MpesaTranscodeRequest {

    @SerializedName("requestId")
    @Expose
    private String requestId;

    @SerializedName("productsVersion")
    @Expose
    private Integer productsVersion;

    @SerializedName("requestname")
    @Expose
    private String requestname;

    @SerializedName("requestbody")
    @Expose
    private MpesaTranscodeRequestbody requestbody;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Integer getProductsVersion() {
        return productsVersion;
    }

    public void setProductsVersion(Integer productsVersion) {
        this.productsVersion = productsVersion;
    }

    public String getRequestname() {
        return requestname;
    }

    public void setRequestname(String requestname) {
        this.requestname = requestname;
    }

    public MpesaTranscodeRequestbody getRequestbody() {
        return requestbody;
    }

    public void setRequestbody(MpesaTranscodeRequestbody requestbody) {
        this.requestbody = requestbody;
    }
}
