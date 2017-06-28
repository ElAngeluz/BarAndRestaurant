package com.mobitill.barandrestaurant.data;

import com.mobitill.barandrestaurant.data.request.remotemodels.request.OrderRemoteRequest;
import com.mobitill.barandrestaurant.data.request.remotemodels.response.OrderRemoteResponse;
import com.mobitill.barandrestaurant.data.updatedproducts.request.ProductsRequestModel;
import com.mobitill.barandrestaurant.data.updatedproducts.response.ProductsResponseModel;
import com.mobitill.barandrestaurant.data.updatedwaiter.request.WaitersRequestModel;
import com.mobitill.barandrestaurant.data.updatedwaiter.response.WaitersResponseModel;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by james on 4/27/2017.
 */

public interface ApiEndpointInterface {

//    @POST("waiters/fetch")
//    Observable<WaiterResponse> getWaiters(@Body Request request);

//    @POST("products/fetch")
//    Observable<ProductResponse> getProducts(@Body Request request);

    @POST("graphql")
    Observable<WaitersResponseModel> getWaiters(@Body WaitersRequestModel waitersRequestModel);

    @POST("graphql")
    Observable<ProductsResponseModel> getProducts(@Body ProductsRequestModel productsRequestModel);

    @POST("pagremote")
    Observable<OrderRemoteResponse> orderRequest(@Body OrderRemoteRequest orderRemoteRequest);
}
