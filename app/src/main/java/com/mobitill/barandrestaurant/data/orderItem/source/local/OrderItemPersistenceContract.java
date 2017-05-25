package com.mobitill.barandrestaurant.data.orderItem.source.local;

import android.provider.BaseColumns;

/**
 * Created by andronicus on 5/23/2017.
 */

public final class OrderItemPersistenceContract {

    /*
    * To prevent someone from accidentally instantiating the contract class,
    * give it a private constructor
    */
    private OrderItemPersistenceContract() {
    }

    /*
    * Inner class that defines table contents
    * */

    public static abstract class OrderItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "order_item";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_PRODUCT_ID = "product_id";
        public static final String COLUMN_NAME_ORDER_ID = "order_id";
        public static final String COLUMN_NAME_COUNTER = "counter";
        public static final String COLUMN_NAME_SYNCED = "Synced";
        public static final String COLUMN_NAME_CHECKED_OUT = "checked_out";
        public static final String COLUMN_NAME_PRODUCT_NAME = "product_name";

    }
}
