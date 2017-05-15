package com.mobitill.barandrestaurant.data;

import com.mobitill.barandrestaurant.data.request.Fetch;
import com.mobitill.barandrestaurant.data.request.Params;
import com.mobitill.barandrestaurant.data.request.Request;

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


}
