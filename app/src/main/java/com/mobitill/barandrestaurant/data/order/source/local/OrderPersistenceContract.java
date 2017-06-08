package com.mobitill.barandrestaurant.data.order.source.local;

import android.provider.BaseColumns;

/**
 * Created by andronicus on 5/16/2017.
 */

public final class OrderPersistenceContract{

    //To prevent someone from accidentally instantiating the contract class
    //give it an empty constructor
    private OrderPersistenceContract() {
    }

    /**Inner class that defines table contents*/
    public static abstract class OrderEntry implements BaseColumns {
        public static final String TABLE_NAME = "order_table";
        public static final String COLUMN_NAME_ENTRY_ID = "entry_id";
        public static final String COLUMN_NAME_DISPLAY_ID = "display_id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_WAITER_ID = "waiter_id";
        public static final String COLUMN_NAME_SYNCED = "synced";
        public static final String COLUMN_NAME_CHECKED_OUT = "checked_out";
        public static final String COLUMN_NAME_TIME_STAMP = "timestamp";
        public static final String COLUMN_NAME_FLAGGED_FOR_CHECKOUT = "checkout_flagged";
        public static final String COLUMN_NAME_PAYMENT_METHOD = "payment_method";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_TRANSACTION_ID = "transaction_id";
        public static final String COLUMN_NAME_PROCESS_STATE = "process_state";
    }

}
