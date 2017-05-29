package com.mobitill.barandrestaurant.data.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobitill.barandrestaurant.data.product.models.Product;

import java.util.List;

public class ProductResponse {

    @SerializedName("data")
    @Expose
    private List<Product> data = null;

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }

}