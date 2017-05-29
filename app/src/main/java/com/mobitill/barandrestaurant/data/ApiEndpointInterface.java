package com.mobitill.barandrestaurant.data;

import com.mobitill.barandrestaurant.data.request.ProductResponse;
import com.mobitill.barandrestaurant.data.request.Request;
import com.mobitill.barandrestaurant.data.request.remotemodels.request.OrderRemoteRequest;
import com.mobitill.barandrestaurant.data.request.remotemodels.response.OrderRemoteResponse;
import com.mobitill.barandrestaurant.data.waiter.waitermodels.response.WaiterResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by james on 4/27/2017.
 */

public interface ApiEndpointInterface {

    @POST("waiters/fetch")
    Observable<WaiterResponse> getWaiters(@Body Request request);

    @POST("products/fetch")
    Observable<ProductResponse> getProducts(@Body Request request);

    @POST("pagremote")
    Observable<OrderRemoteResponse> orderRequest(@Body OrderRemoteRequest orderRemoteRequest);
}
