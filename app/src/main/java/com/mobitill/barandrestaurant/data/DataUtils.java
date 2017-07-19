package com.mobitill.barandrestaurant.data;

import com.mobitill.barandrestaurant.data.request.Fetch;
import com.mobitill.barandrestaurant.data.request.Params;
import com.mobitill.barandrestaurant.data.request.Request;
import com.mobitill.barandrestaurant.data.updatedproducts.request.ProductsRequestModel;
import com.mobitill.barandrestaurant.data.updatedwaiter.request.WaitersRequestModel;

/**
 * Created by james on 5/12/2017.
 */

public class DataUtils {

    public static Request getRequest() {
        Request request = new Request();
        Params params = new Params();
        Fetch fetch = new Fetch();
        fetch.setAppid("0157dc67bf151af569");
        params.setFetch(fetch);
        request.setParams(params);
        return request;
    }

    public static WaitersRequestModel makeWaitersRequest(){

        WaitersRequestModel waitersRequestModel = new WaitersRequestModel();
        waitersRequestModel.setQuery("query { organization (id:\"816443890285020\") {cashiers{id,name,phone,roles,password}} }");
        return waitersRequestModel;
    }

    public static ProductsRequestModel makeProductsRequest(){

        ProductsRequestModel productsRequestModel = new ProductsRequestModel();
        productsRequestModel.setQuery("query { organization (id:\"816443890285020\") {products{id,sku,description,price,location,vat}} }");
        return productsRequestModel;
    }
}
