package com.mobitill.barandrestaurant.data.request.remotemodels.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderRemoteRequest {

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
    private OrderRemoteRequestbody requestbody;

    @SerializedName("paymentmode")
    @Expose
    private String paymentmode;

    @SerializedName("paymentinfo")
    @Expose
    private String paymentinfo;

    @SerializedName("orderId")
    @Expose
    private Integer orderId;

    public String getPaymentmode() {
        return paymentmode;
    }

    public void setPaymentmode(String paymentmode) {
        this.paymentmode = paymentmode;
    }

    public String getPaymentinfo() {
        return paymentinfo;
    }

    public void setPaymentinfo(String paymentinfo) {
        this.paymentinfo = paymentinfo;
    }

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

    public OrderRemoteRequestbody getRequestbody() {
        return requestbody;
    }

    public void setRequestbody(OrderRemoteRequestbody requestbody) {
        this.requestbody = requestbody;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}