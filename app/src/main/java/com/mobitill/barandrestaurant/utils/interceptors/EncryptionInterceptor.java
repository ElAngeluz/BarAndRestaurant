package com.mobitill.barandrestaurant.utils.interceptors;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobitill.barandrestaurant.data.order.source.local.OrderLocalDataSource;
import com.mobitill.barandrestaurant.data.request.remotemodels.request.OrderRemoteRequest;

import java.io.IOException;

import javax.inject.Inject;

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

    @Inject
    OrderLocalDataSource mOrderLocalDataSource;

    public static final String TAG = EncryptionInterceptor.class.getSimpleName();

    private static final String password = "tfdsfsafedfsdfsfsd";
    private static String salt;
    private static int pswdIterations = 65536;
    private static int keySize = 256;
    private byte[] ivBytes;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
//        Response response = chain.proceed(request);
        RequestBody oldBody = request.body();
        Buffer buffer = new Buffer();
        oldBody.writeTo(buffer);
        String strOldBody = buffer.readUtf8();
        MediaType mediaType = MediaType.parse("text/plain; charset=utf-8");
        try {
            String strNewBody = AESEncryptor.encrypt(strOldBody);
            RequestBody body = RequestBody.create(mediaType, strNewBody);
            request = request.newBuilder()
                    .header("Content-Type", body.contentType().toString())
                    .header("Content-Length", String.valueOf(body.contentLength()))
                    .method(request.method(), body).build();
            return chain.proceed(request);
        } catch (Exception e) {
            Log.d("NetworkThrows Exception", " Failed to connect ");
            Log.d("Intercept Old body", strOldBody);
//            throw new  IOException(e.getMessage());
            try {
                Gson gson = new GsonBuilder().create();
                OrderRemoteRequest orderRemoteRequest = gson.fromJson(strOldBody, OrderRemoteRequest.class);
                InjectInterceptor injectInterceptor = new InjectInterceptor();
                int OrderId = orderRemoteRequest.getOrderId();
//                Log.d(TAG, "process state is : " + orderRemoteRequest.getClass());
                Log.d(TAG, String.valueOf("mOrderLocalDatasource is it null : " + mOrderLocalDataSource == null));
//                mOrderLocalDataSource.testFunc();
//                injectInterceptor.getUpdatedState(String.valueOf(OrderId));
//                mOrderLocalDataSource.updateSyncState(String.valueOf(OrderId),0);

            } catch (Exception ex) {
                ex.printStackTrace();
                Log.d("Serialize Exception", ex.getMessage());
            }

            //mOrder.getEntryId();
//            e.printStackTrace();
            throw new  IOException(e.getMessage());
        }
//        return response;
//        return null;

    }

}
