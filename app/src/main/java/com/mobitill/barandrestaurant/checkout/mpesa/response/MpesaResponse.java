
package com.mobitill.barandrestaurant.checkout.mpesa.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class MpesaResponse {

    @SerializedName("message")
    private String mMessage;

    @SerializedName("orderId")
    private Long mOrderId;
    @SerializedName("productsVersion")
    private Long mProductsVersion;
    @SerializedName("requestId")
    private String mRequestId;
    @SerializedName("response")
    private Response mResponse;
    @SerializedName("status")
    private List<Status> mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Long getOrderId() {
        return mOrderId;
    }

    public void setOrderId(Long orderId) {
        mOrderId = orderId;
    }

    public Long getProductsVersion() {
        return mProductsVersion;
    }

    public void setProductsVersion(Long productsVersion) {
        mProductsVersion = productsVersion;
    }

    public String getRequestId() {
        return mRequestId;
    }

    public void setRequestId(String requestId) {
        mRequestId = requestId;
    }

    public Response getResponse() {
        return mResponse;
    }

    public void setResponse(Response response) {
        mResponse = response;
    }

    public List<Status> getStatus() {
        return mStatus;
    }

    public void setStatus(List<Status> status) {
        mStatus = status;
    }

}
