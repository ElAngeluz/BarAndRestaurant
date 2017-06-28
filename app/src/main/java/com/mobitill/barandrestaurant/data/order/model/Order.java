package com.mobitill.barandrestaurant.data.order.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;

/**
 * Created by andronicus on 5/16/2017.
 */

public class Order {

    @SerializedName("id")
    @Expose
    private String entryId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("displayId")
    @Expose
    private String displayId;

    @SerializedName("waiterId")
    @Expose
    private String waiterId;

    @SerializedName("synced")
    @Expose
    private Integer synced;

    @SerializedName("checkedOut")
    @Expose
    private Integer checkedOut;

    private Integer processState;

    private Long timeStamp;

    private Integer checkoutFlagged;

    private String paymentMethod;

    private String amount;

    private String transactionId;

    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Order() {
        this.entryId = String.valueOf(generateUniqueId());
        this.name = "Order " + this.entryId;
        this.timeStamp = new Date().getTime();
        this.synced = 0;
        this.checkedOut = 0;
        this.checkoutFlagged = 0;

//        refactored
        this.paymentMethod = "";
        this.amount = "";
        this.transactionId = "";
        this.processState = 0;

    }

    public Order(String waiterId) {
        this.entryId = String.valueOf(generateUniqueId());
        this.name = "Order " + this.entryId;
        this.timeStamp = new Date().getTime();
        this.waiterId = waiterId;
        this.synced = 0;
        this.checkedOut = 0;
        this.checkoutFlagged = 0;

//        refactored
        this.paymentMethod = "";
        this.amount = "";
        this.transactionId = "";
        this.processState = 0;
    }


    public Order(String entryId,
                 String displayId,
                 String name,
                 String waiterId,
                 Integer synced,
                 Integer checkedOut,
                 Long timeStamp,
                 Integer checkoutFlagged,
                 String paymentMethod,
                 String amount,
                 String transactionId,
                 Integer processState) {

        this.entryId = entryId;
        this.displayId = displayId;
        this.name = name;
        this.waiterId = waiterId;
        this.synced = synced;
        this.checkedOut = checkedOut;
        this.timeStamp = timeStamp;
        this.checkoutFlagged = checkoutFlagged;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.transactionId = transactionId;
        this.processState = processState;
    }

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    public String getDisplayId() {
        return displayId;
    }

    public void setDisplayId(String displayId) {
        this.displayId = displayId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(String waiterId) {
        this.waiterId = waiterId;
    }

    public Integer getSynced() {
        return synced;
    }

    public void setSynced(Integer synced) {
        this.synced = synced;
    }

    public Integer getCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(Integer checkedOut) {
        this.checkedOut = checkedOut;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getCheckoutFlagged() {
        return checkoutFlagged;
    }

    public void setCheckoutFlagged(Integer checkoutFlagged) {
        this.checkoutFlagged = checkoutFlagged;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getProcessState() {
        return processState;
    }

    public void setProcessState(Integer processState) {
        this.processState = processState;
    }

    /**
     * Gnereate unique ID from UUID in positive space
     * @return long value representing UUID
     */
    private Long generateUniqueId()
    {
        long val = -1;
        do
        {
            final UUID uid = UUID.randomUUID();
            final ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
            buffer.putLong(uid.getLeastSignificantBits());
            buffer.putLong(uid.getMostSignificantBits());
            final BigInteger bi = new BigInteger(buffer.array());
            val = bi.intValue();
        } while (val < 0);
        return val;
    }

}
