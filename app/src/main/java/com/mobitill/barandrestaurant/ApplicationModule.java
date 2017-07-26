package com.mobitill.barandrestaurant;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.mobitill.barandrestaurant.data.DBHelper;
import com.mobitill.barandrestaurant.utils.Constants;
import com.mobitill.barandrestaurant.utils.interceptors.EncryptionInterceptor;
import com.mobitill.barandrestaurant.utils.interceptors.EncryptionResponseInterceptor;
import com.mobitill.barandrestaurant.utils.schedulers.BaseScheduleProvider;
import com.mobitill.barandrestaurant.utils.schedulers.SchedulersProvider;
import com.mobitill.barandrestaurant.utils.schedulers.V1BaseSchedulerProvider;
import com.mobitill.barandrestaurant.utils.schedulers.V1SchedulersProvider;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

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
 * Created by james on 4/27/2017.
 */
@Module
public class ApplicationModule {

    private final Context mContext;

    public ApplicationModule(Context context){
        mContext = context;
    }

    @Provides
    Context provideContext(){
        return mContext;
    }

    @Provides
    SharedPreferences provideSharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    RxSharedPreferences provideRxPreferences(SharedPreferences sharedPreferences){
        return RxSharedPreferences.create(sharedPreferences);
    }

    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
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
    @Named(Constants.RetrofitSource.POS)
    OkHttpClient providePosOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor,
                                        EncryptionInterceptor encryptionInterceptor,
                                        EncryptionResponseInterceptor encryptionResponseInterceptor){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120,TimeUnit.SECONDS)
                .addInterceptor(encryptionInterceptor)
                .retryOnConnectionFailure(true)
                .addInterceptor(encryptionResponseInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build();
        return client;
    }

    @Provides
    @Named(Constants.RetrofitSource.MOBITILL)
    OkHttpClient provideMobitillOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
        return client;
    }

    @Provides
    @Named(Constants.RetrofitSource.MOBITILL)
    Retrofit provideServerRetrofit(@Named(Constants.RetrofitSource.MOBITILL) OkHttpClient client){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BaseUrl.MOBITILL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
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

    @Provides
    V1BaseSchedulerProvider provideV1SchedulersProvider(){
        return new V1SchedulersProvider();
    }

    @Provides
    BaseScheduleProvider provideSchedulerProvider(){
        return new SchedulersProvider();
    }

    @Provides
    DBHelper provideDBHelper(Context context){
        return new DBHelper(context);
    }

    @Provides
    SqlBrite provideSqlBrite(){
        return new SqlBrite.Builder().build();
    }

    @Provides
    BriteDatabase provideBriteDatabaseHelper(SqlBrite sqlBrite,
                                             DBHelper dbHelper,
                                             V1BaseSchedulerProvider schedulerProvider){
        return sqlBrite.wrapDatabaseHelper(dbHelper, schedulerProvider.computation());
    }
}

