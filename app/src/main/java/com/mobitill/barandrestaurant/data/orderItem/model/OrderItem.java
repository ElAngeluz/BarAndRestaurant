package com.mobitill.barandrestaurant.data.orderItem.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.UUID;

/**
 * Created by andronicus on 5/23/2017.
 */

public class OrderItem {

    @NonNull
    private  String id;

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

    private String productName;

//    Refactored
    private String productPrice;

    private Long timeStamp;

    public OrderItem() {
        this.id = UUID.randomUUID().toString();
    }

    public OrderItem(String productId, String orderId, String counter, Integer synced, Integer checkedOut, String productName, String productPrice) {
        this(UUID.randomUUID().toString(), productId, orderId, counter, synced, checkedOut, productName,productPrice, new Date().getTime());
    }

    public OrderItem(String id, String productId, String orderId, String counter, Integer synced, Integer checkedOut, String productName,String productPrice, Long timeStamp) {
        this.id = id;
        this.productId = productId;
        this.orderId = orderId;
        this.counter = counter;
        this.synced = synced;
        this.checkedOut = checkedOut;
        this.productName = productName;
        this.productPrice = productPrice;
        this.timeStamp = timeStamp;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public Integer getCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(Integer checkedOut) {
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


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
