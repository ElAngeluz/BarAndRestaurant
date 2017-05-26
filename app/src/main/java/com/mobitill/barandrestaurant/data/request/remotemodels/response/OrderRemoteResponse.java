package com.mobitill.barandrestaurant.data.request.remotemodels.response;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderRemoteResponse {

    @SerializedName("requestId")
    @Expose
    private String requestId;
    @SerializedName("productsVersion")
    @Expose
    private Integer productsVersion;
    @SerializedName("status")
    @Expose
    private List<OrderRemoteStatus> status = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private OrderResponse response;
    @SerializedName("orderId")
    @Expose
    private Integer orderId;

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

    public List<OrderRemoteStatus> getStatus() {
        return status;
    }

    public void setStatus(List<OrderRemoteStatus> status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OrderResponse getResponse() {
        return response;
    }

    public void setResponse(OrderResponse response) {
        this.response = response;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}