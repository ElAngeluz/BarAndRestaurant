package com.mobitill.barandrestaurant.data.product.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("identifier")
    @Expose
    private String identifier;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("vat")
    @Expose
    private String vat;

    public Product() {
    }

    public Product(String id, String barcode,  String identifier, String name, String price, String vat) {
        this.barcode = barcode;
        this.id = id;
        this.identifier = identifier;
        this.name = name;
        this.price = price;
        this.vat = vat;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

}