package com.mobitill.barandrestaurant.data.product.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product implements Parcelable {

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (barcode != null ? !barcode.equals(product.barcode) : product.barcode != null)
            return false;
        if (!id.equals(product.id)) return false;
        if (identifier != null ? !identifier.equals(product.identifier) : product.identifier != null)
            return false;
        if (!name.equals(product.name)) return false;
        if (price != null ? !price.equals(product.price) : product.price != null) return false;
        return vat != null ? vat.equals(product.vat) : product.vat == null;
    }

    @Override
    public int hashCode() {
        int result = barcode != null ? barcode.hashCode() : 0;
        result = 31 * result + id.hashCode();
        result = 31 * result + (identifier != null ? identifier.hashCode() : 0);
        result = 31 * result + name.hashCode();
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (vat != null ? vat.hashCode() : 0);
        return result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.barcode);
        dest.writeString(this.id);
        dest.writeString(this.identifier);
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeString(this.vat);
    }

    protected Product(Parcel in) {
        this.barcode = in.readString();
        this.id = in.readString();
        this.identifier = in.readString();
        this.name = in.readString();
        this.price = in.readString();
        this.vat = in.readString();
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}