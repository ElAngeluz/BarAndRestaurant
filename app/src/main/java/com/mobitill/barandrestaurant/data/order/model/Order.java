package com.mobitill.barandrestaurant.data.order.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;

/**
 * Created by andronicus on 5/16/2017.
 */

public class Order implements Parcelable{

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

    private Integer counterASync;

    private Integer counterBSync;

    protected Order(Parcel in) {
        entryId = in.readString();
        name = in.readString();
        displayId = in.readString();
        waiterId = in.readString();
        if (in.readByte() == 0) {
            synced = null;
        } else {
            synced = in.readInt();
        }
        if (in.readByte() == 0) {
            counterASync = null;
        } else {
            counterASync = in.readInt();
        }
        if (in.readByte() == 0) {
            counterBSync = null;
        } else {
            counterBSync = in.readInt();
        }
        if (in.readByte() == 0) {
            checkedOut = null;
        } else {
            checkedOut = in.readInt();
        }
        if (in.readByte() == 0) {
            processState = null;
        } else {
            processState = in.readInt();
        }
        if (in.readByte() == 0) {
            timeStamp = null;
        } else {
            timeStamp = in.readLong();
        }
        if (in.readByte() == 0) {
            checkoutFlagged = null;
        } else {
            checkoutFlagged = in.readInt();
        }
        paymentMethod = in.readString();
        amount = in.readString();
        transactionId = in.readString();
        category = in.readString();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

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
        this.paymentMethod = "";
        this.amount = "";
        this.transactionId = "";
        this.processState = 0;
        this.counterASync = 0;
        this.counterBSync = 0;

    }

    public Order(String waiterId) {
        this.entryId = String.valueOf(generateUniqueId());
        this.name = "Order " + this.entryId;
        this.timeStamp = new Date().getTime();
        this.waiterId = waiterId;
        this.synced = 0;
        this.checkedOut = 0;
        this.checkoutFlagged = 0;
        this.paymentMethod = "";
        this.amount = "";
        this.transactionId = "";
        this.processState = 0;
        this.counterASync = 0;
        this.counterBSync = 0;
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
                 Integer processState,
                 Integer counterASync,
                 Integer counterBSync) {

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
        this.counterASync = counterASync;
        this.counterBSync = counterBSync;
    }

    public Integer getCounterASync() {
        return counterASync;
    }

    public void setCounterASync(Integer counterASync) {
        this.counterASync = counterASync;
    }

    public Integer getCounterBSync() {
        return counterBSync;
    }

    public void setCounterBSync(Integer counterBSync) {
        this.counterBSync = counterBSync;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(entryId);
        dest.writeString(name);
        dest.writeString(displayId);
        dest.writeString(waiterId);
        if (synced == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(synced);
        }
        if (counterASync == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(counterASync);
        }
        if (counterBSync == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(counterBSync);
        }
        if (checkedOut == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(checkedOut);
        }
        if (processState == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(processState);
        }
        if (timeStamp == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(timeStamp);
        }
        if (checkoutFlagged == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(checkoutFlagged);
        }
        dest.writeString(paymentMethod);
        dest.writeString(amount);
        dest.writeString(transactionId);
        dest.writeString(category);
    }
}
