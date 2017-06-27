package com.mobitill.barandrestaurant.checkout;

import android.support.annotation.NonNull;

import com.mobitill.barandrestaurant.utils.Constants;
import com.mobitill.barandrestaurant.utils.interceptors.EncryptionInterceptor;
import com.mobitill.barandrestaurant.utils.interceptors.EncryptionResponseInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dataintegrated on 5/29/2017.
 */
@Module
public class CheckOutPresenterModule {

    private final CheckOutContract.View mView;

    public CheckOutPresenterModule(CheckOutContract.View view) {
        mView = view;
    }

    @Provides
    @NonNull
    CheckOutContract.View provideCheckOutContractView(){
        return mView;
    }


    @Provides
    EncryptionInterceptor provideEncryptionInterceptor(){
        return new EncryptionInterceptor();
    }

    @Provides
    EncryptionResponseInterceptor provideEncryptionResponseInterceptor(){
        return new EncryptionResponseInterceptor();
    }

    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }
    @Provides
    @Named(Constants.RetrofitSource.POS)
    OkHttpClient providePosOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor,
                                        EncryptionInterceptor encryptionInterceptor,
                                        EncryptionResponseInterceptor encryptionResponseInterceptor){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .addInterceptor(encryptionInterceptor)
                .addInterceptor(encryptionResponseInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build();
        return client;
    }

    @Provides
    @Named(Constants.RetrofitSource.COUNTERA)
    Retrofit provideCounterARetrofit(@Named(Constants.RetrofitSource.POS) OkHttpClient client){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BaseUrl.COUNTERA)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    @Provides
    @Named(Constants.RetrofitSource.COUNTERB)
    Retrofit provideCounterBRetrofit(@Named(Constants.RetrofitSource.POS) OkHttpClient client){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BaseUrl.COUNTERB)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

}
