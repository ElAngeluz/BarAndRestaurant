
package com.mobitill.barandrestaurant.data.updatedproducts.request;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ProductsRequestModel {

    @SerializedName("query")
    private String mQuery;

    public String getQuery() {
        return mQuery;
    }

    public void setQuery(String query) {
        mQuery = query;
    }

}
