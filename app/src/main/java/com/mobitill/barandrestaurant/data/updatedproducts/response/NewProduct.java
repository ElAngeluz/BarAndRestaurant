
package com.mobitill.barandrestaurant.data.updatedproducts.response;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class NewProduct {

    @SerializedName("location")
    private String mLocation;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("id")
    private String mId;
    @SerializedName("price")
    private Long mPrice;
    @SerializedName("sku")
    private Long mSku;
    @SerializedName("vat")
    private String mVat;

    public String getVat() {
        return mVat;
    }

    public void setVat(String vat) {
        mVat = vat;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Long getPrice() {
        return mPrice;
    }

    public void setPrice(Long price) {
        mPrice = price;
    }

    public Long getSku() {
        return mSku;
    }

    public void setSku(Long sku) {
        mSku = sku;
    }

}
