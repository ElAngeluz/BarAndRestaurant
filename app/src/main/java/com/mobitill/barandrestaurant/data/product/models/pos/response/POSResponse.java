package com.mobitill.barandrestaurant.data.product.models.pos.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class POSResponse {

    @SerializedName("requestId")
    @Expose
    private String requestId;
    @SerializedName("productsVersion")
    @Expose
    private Integer productsVersion;
    @SerializedName("status")
    @Expose
    private List<Status> status = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private Response response;

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

    public List<Status> getStatus() {
        return status;
    }

    public void setStatus(List<Status> status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }


}