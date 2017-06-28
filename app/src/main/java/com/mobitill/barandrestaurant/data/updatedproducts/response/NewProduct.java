
package com.mobitill.barandrestaurant.data.updatedproducts.response;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class NewProduct {

    @SerializedName("category")
    private String mCategory;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("id")
    private String mId;
    @SerializedName("price")
    private Long mPrice;
    @SerializedName("sku")
    private Long mSku;

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
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
