package com.mobitill.barandrestaurant.data.product.source.local;

import android.provider.BaseColumns;

/**
 * Created by james on 5/12/2017.
 */

public final class ProductPersistenceContract {
    //To prevent someone from accidentally instantiating the contract class
    // give it a private constructor
    private ProductPersistenceContract(){};

    /**Inner class that defines table contents*/
    public static abstract class ProductEntry implements BaseColumns{
        public static final String TABLE_NAME = "product";
        public static final String COLUMN_NAME_BARCODE = "barcode";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_IDENTIFIER = "identifier";
        public static final String COLUMN_NAME_PRODUCT_NAME = "name";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_VAT = "vat";
        public static final String COLUMN_NAME_CATEGORY = "category";
    }
}
