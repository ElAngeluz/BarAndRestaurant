package com.mobitill.barandrestaurant.checkout;

import com.mobitill.barandrestaurant.checkout.mpesa.request.MpesaTranscodeRequest;
import com.mobitill.barandrestaurant.checkout.mpesa.response.MpesaResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by dataintegrated on 6/21/2017.
 */

public interface MpesaApiService {

    @POST("mpesasearch")
    Call<MpesaResponse> sendRequest(@Body MpesaTranscodeRequest transcodeRequest);
}
