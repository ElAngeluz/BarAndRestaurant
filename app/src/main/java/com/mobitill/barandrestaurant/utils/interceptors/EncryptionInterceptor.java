package com.mobitill.barandrestaurant.utils.interceptors;


import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by james on 5/23/2017.
 */

public class EncryptionInterceptor implements Interceptor {

    public static final String TAG = EncryptionInterceptor.class.getSimpleName();

    private static final String password = "tfdsfsafedfsdfsfsd";
    private static String salt;
    private static int pswdIterations = 65536  ;
    private static int keySize = 256;
    private byte[] ivBytes;


    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        RequestBody oldBody = request.body();
        Buffer buffer = new Buffer();
        oldBody.writeTo(buffer);
        String strOldBody = buffer.readUtf8();
        MediaType mediaType = MediaType.parse("text/plain; charset=utf-8");
        try {
            String strNewBody = AESEncryptor.encrypt(strOldBody);
            Log.i(TAG, "intercept: test : strNewBody : " + strNewBody);
            Log.i(TAG, "intercept: test : " + AESEncryptor.encrypt("{\"requestbody\":{},\"requestname\":\"queryproducts\"}"));
            RequestBody body = RequestBody.create(mediaType, strNewBody);
            request = request.newBuilder()
                    .header("Content-Type", body.contentType().toString())
                    .header("Content-Length", String.valueOf(body.contentLength()))
                    .method(request.method(), body).build();
            return chain.proceed(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
