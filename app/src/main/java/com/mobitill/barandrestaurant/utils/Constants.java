package com.mobitill.barandrestaurant.utils;

/**
 * Created by james on 4/27/2017.
 */

public class Constants {

    public static final class RetrofitSource{
        public final static String MOBITILL = "mobitill";
        public final static String COUNTERA =  "counter_a";
        public final static String COUNTERB = "counter_b";
        public static final String POS = "pos";
    }

    public static final class BaseUrl{
//        public static final String MOBITILL = "http://api.mobitill.com/v1/";

        public static final String MOBITILL = "http://foods.mobitill.com:9000/v1/";
        public static final String COUNTERA = "http://192.168.0.51:8080/";
        public static final String COUNTERB = "http://192.168.0.52:8080/";
    }

    public static final class SchedulerProviderSource{
        public static final String V1 = "v1";
        public static final String V2 = "v2";
    }
}
