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
    private String synced;

    @SerializedName("Checked_out")
    @Expose
    private String checked_out;

    public OrderItem(String id, String product_id, String order_id, String counter, String synced, String checked_out) {
        this.productId = productId;
        this.orderId = orderId;
        this.counter = counter;
        this.synced = synced;
        this.checked_out = checked_out;
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

    public String getSynced() {
        return synced;
    }

    public void setSynced(String synced) {
        this.synced = synced;
    }

    public String getChecked_out() {
        return checked_out;
    }

    public void setChecked_out(String checked_out) {
        this.checked_out = checked_out;
    }
}
