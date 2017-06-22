package com.mobitill.barandrestaurant.checkout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.mobitill.barandrestaurant.checkout.mpesa.request.MpesaTranscodeRequest;
import com.mobitill.barandrestaurant.checkout.mpesa.request.MpesaTranscodeRequestbody;
import com.mobitill.barandrestaurant.checkout.mpesa.response.MpesaResponse;
import com.mobitill.barandrestaurant.data.order.OrderRepository;
import com.mobitill.barandrestaurant.jobs.checkoutjobs.CheckOutJob;
import com.mobitill.barandrestaurant.utils.Constants;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by dataintegrated on 5/29/2017.
 */

public class CheckOutPresenter implements CheckOutContract.Presenter {

    private static final String TAG = CheckOutPresenter.class.getSimpleName();

    @NonNull CheckOutContract.View mView;

    @NonNull
    private final OrderRepository mOrderRepository;

    @NonNull
    private CompositeDisposable compositeDisposable;

    @NonNull
    private final Retrofit mRetrofitCounterA;

    @NonNull
    Context mContext;

    @Inject
    CheckOutPresenter(@NonNull CheckOutContract.View view,
                      @NonNull OrderRepository orderRepository,
                      @NonNull @Named(Constants.RetrofitSource.COUNTERA) Retrofit retrofitCounterA,
                      @NonNull Context context){
        mView = view;
        mOrderRepository = orderRepository;
        mRetrofitCounterA = retrofitCounterA;
        mContext = context;
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * Method injection is used here to safely reference {@code this} after the object is created.
     */
    @Inject
    void setupListeners() {
        mView.setPresenter(this);
    }

    @Override

    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void checkout(String orderId) {
        if(orderId != null){
            mOrderRepository.getOne(orderId)
                    .subscribe(order -> {
                        order.setCheckoutFlagged(1);
                        order.setPaymentMethod(mView.setPaymentMethod());
                        order.setAmount(mView.setAmount());
                        order.setTransactionId(mView.setTransactionsId());
                        int updated = mOrderRepository.update(order);
                        if (updated > -1) {
                            Log.i(TAG, "checkout: order: " + order.getEntryId());
                            CheckOutJob.scheduleJob();
                        }
                    });
        }
    }

    public void makeCall(){

        MpesaTranscodeRequest mpesaTranscodeRequest = new MpesaTranscodeRequest();
        mpesaTranscodeRequest.setRequestname("mpesasearch");
        mpesaTranscodeRequest.setRequestId("1");
        mpesaTranscodeRequest.setProductsVersion(1);

        mpesaTranscodeRequest.setRequestbody(new MpesaTranscodeRequestbody(""));

        MpesaApiService mpesaApiService = mRetrofitCounterA.create(MpesaApiService.class);
        Call<MpesaResponse> call = mpesaApiService.sendRequest(mpesaTranscodeRequest);
        call.enqueue(new Callback<MpesaResponse>() {
            @Override
            public void onResponse(Call<MpesaResponse> call, Response<MpesaResponse> response) {
                if (response.body().getMessage().equals("ok")){
                    Log.d(TAG, "onResponse: " + response.body().getResponse().getMessage());
                    Toast.makeText(mContext, "Successful", Toast.LENGTH_LONG).show();
                }else {

                    Log.d(TAG, "Error Message: " + response.body().getResponse().getMessage());
                    Toast.makeText(mContext, "An Error Occured", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MpesaResponse> call, Throwable t) {

//                Toast.makeText(mContext, , Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: " +  t.getMessage());
            }
        });
    }


}
