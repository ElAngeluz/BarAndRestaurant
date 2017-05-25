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

    @SerializedName("waiterId")
    @Expose
    private String waiterId;

    @SerializedName("synced")
    @Expose
    private Integer synced;

    @SerializedName("checkedOut")
    @Expose
    private Integer checkedOut;

    private Long timeStamp;


    public Order() {
        this.entryId = String.valueOf(generateUniqueId());
        this.name = "Order " + this.entryId;
        this.timeStamp = new Date().getTime();
    }

    public Order(String waiterId, Integer synced, Integer checkedOut) {
        this.entryId = String.valueOf(generateUniqueId());
        this.name = "Order " + this.entryId;
        this.timeStamp = new Date().getTime();
        this.waiterId = waiterId;
        this.synced = synced;
        this.checkedOut = checkedOut;
    }


    public Order(String entryId, String name, String waiterId, Integer synced, Integer checkedOut, Long timeStamp) {
        this.entryId = entryId;
        this.name = name;
        this.waiterId = waiterId;
        this.synced = synced;
        this.checkedOut = checkedOut;
        this.timeStamp = timeStamp;
    }

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
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
