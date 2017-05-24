package com.mobitill.barandrestaurant.data.orderItem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by andronicus on 5/23/2017.
 */

public class OrderItem {

    @SerializedName("ProductId")
    @Expose
    private String productId;

    @SerializedName("OrderId")
    @Expose
    private String orderId;

    @SerializedName("Counter")
    @Expose
    private String counter;

    @SerializedName("Synced")
    @Expose
    private Integer synced;

    @SerializedName("Checked_out")
    @Expose
    private Integer checkedOut;

    public OrderItem() {
    }

    public OrderItem(String productId, String orderId, String counter, Integer synced, Integer checkedOut) {
        this.productId = productId;
        this.orderId = orderId;
        this.counter = counter;
        this.synced = synced;
        this.checkedOut = checkedOut;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public Integer getSynced() {
        return synced;
    }

    public void setSynced(Integer synced) {
        this.synced = synced;
    }

    public Integer getChecked_out() {
        return checkedOut;
    }

    public void setChecked_out(Integer checkedOut) {
        this.checkedOut = checkedOut;
    }
}
