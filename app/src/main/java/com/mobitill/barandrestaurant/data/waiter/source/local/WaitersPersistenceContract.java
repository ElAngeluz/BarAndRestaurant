package com.mobitill.barandrestaurant.data.waiter.source.local;

import android.provider.BaseColumns;

/**
 * Created by james on 5/2/2017.
 */

public final class WaitersPersistenceContract {

    //To prevent someone from accidentally instantiating the contract class
    //give it an empty constructor
    private WaitersPersistenceContract(){}

    /**Inner class that defines table contents*/
    public static abstract class WaitersEntry implements BaseColumns{
        public static final String TABLE_NAME = "waiter";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PIN = "pin";
    }


}
