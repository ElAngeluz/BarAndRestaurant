
package com.mobitill.barandrestaurant.data.updatedproducts.response;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Organization {

    @SerializedName("products")
    private List<NewProduct> mProducts;

    public List<NewProduct> getProducts() {
        return mProducts;
    }

    public void setProducts(List<NewProduct> products) {
        mProducts = products;
    }

}
