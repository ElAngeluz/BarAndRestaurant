package com.mobitill.barandrestaurant.utils.interceptors;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by james on 5/23/2017.
 */

public class EncryptionResponseInterceptor implements Interceptor {

    private static final String TAG = EncryptionResponseInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request =  chain.request();
        Response response = chain.proceed(request);

        ResponseBody oldBody = response.body();

        //Buffer buffer = new Buffer();
        String strOldBody = oldBody.string();
        MediaType mediaType = oldBody.contentType();
        try {
            String newBody = AESEncryptor.decrypt(strOldBody);
            ResponseBody responseBody = ResponseBody.create(mediaType, newBody);
            return response.newBuilder().body(responseBody).build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }
}
