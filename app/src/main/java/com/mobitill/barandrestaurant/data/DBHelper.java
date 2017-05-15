package com.mobitill.barandrestaurant.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.mobitill.barandrestaurant.data.product.source.local.ProductPersistenceContract.ProductEntry;
import static com.mobitill.barandrestaurant.data.waiter.source.local.WaitersPersistenceContract.WaitersEntry;

/**
 * Created by james on 4/30/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "BarAndRestaurant.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String BOOLEAN_TYPE = " INTEGER";

    private static final String COMMA_SEP = " ,";

    private static final String SQL_CREATE_WAITER_ENTRIES =
            "CREATE TABLE " + WaitersEntry.TABLE_NAME + " (" +
                    WaitersEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    WaitersEntry.COLUMN_NAME_ID + TEXT_TYPE + " UNIQUE," +
                    WaitersEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    WaitersEntry.COLUMN_NAME_PIN + TEXT_TYPE + " )";

    private static final String SQL_CREATE_PRODUCT_ENTRIES =
            "CREATE TABLE " + ProductEntry.TABLE_NAME + " (" +
                    ProductEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    ProductEntry.COLUMN_NAME_ID + TEXT_TYPE + " UNIQUE," +
                    ProductEntry.COLUMN_NAME_BARCODE + TEXT_TYPE + COMMA_SEP +
                    ProductEntry.COLUMN_NAME_IDENTIFIER + TEXT_TYPE + COMMA_SEP +
                    ProductEntry.COLUMN_NAME_PRODUCT_NAME + TEXT_TYPE + COMMA_SEP +
                    ProductEntry.COLUMN_NAME_PRICE + TEXT_TYPE + COMMA_SEP +
                    ProductEntry.COLUMN_NAME_VAT + TEXT_TYPE + " )";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_WAITER_ENTRIES);
        db.execSQL(SQL_CREATE_PRODUCT_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // not required as at version 1
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // not required as at version 1
    }
}
