package com.mobitill.barandrestaurant.data.orderItem.source.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.mobitill.barandrestaurant.data.orderItem.OrderItemDataSource;
import com.mobitill.barandrestaurant.data.orderItem.model.OrderItem;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import hu.akarnokd.rxjava.interop.RxJavaInterop;
import io.reactivex.Observable;
import rx.functions.Func1;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.mobitill.barandrestaurant.data.orderItem.source.local.OrderItemPersistenceContract.OrderItemEntry;

/**
 * Created by andronicus on 5/23/2017.
 */
@Singleton
public class OrderItemLocalDataSource implements OrderItemDataSource{

    public static final String TAG = OrderItemLocalDataSource.class.getSimpleName();

    @NonNull
    private final BriteDatabase databaseHelper;

    @NonNull
    private Func1<Cursor, OrderItem> orderItemMapperFunction;

    String projection[] = {
            OrderItemEntry.COLUMN_NAME_ENTRY_ID,
            OrderItemEntry.COLUMN_NAME_PRODUCT_ID,
            OrderItemEntry.COLUMN_NAME_ORDER_ID,
            OrderItemEntry.COLUMN_NAME_COUNTER,
            OrderItemEntry.COLUMN_NAME_SYNCED,
            OrderItemEntry.COLUMN_NAME_CHECKED_OUT,
    };

    @Inject
    public OrderItemLocalDataSource(@NonNull BriteDatabase databaseHelper) {
        this.databaseHelper = databaseHelper;
        orderItemMapperFunction = this::getOrderItem;
    }

    private OrderItem getOrderItem(@NonNull Cursor c){
        String entryId = c.getString(c.getColumnIndexOrThrow(OrderItemEntry.COLUMN_NAME_ENTRY_ID));
        String productId = c.getString(c.getColumnIndexOrThrow(OrderItemEntry.COLUMN_NAME_PRODUCT_ID));
        String orderId = c.getString(c.getColumnIndexOrThrow(OrderItemEntry.COLUMN_NAME_ORDER_ID));
        String counter = c.getString(c.getColumnIndexOrThrow(OrderItemEntry.COLUMN_NAME_COUNTER));
        Integer synced = c.getInt(c.getColumnIndexOrThrow(OrderItemEntry.COLUMN_NAME_SYNCED));
        Integer checkedOut = c.getInt(c.getColumnIndexOrThrow(OrderItemEntry.COLUMN_NAME_CHECKED_OUT));
        return new OrderItem(entryId,productId, orderId, counter, synced, checkedOut);
    }


    @Override
    public Observable<List<OrderItem>> getAll() {
        String sql = String.format("SELECT %s FROM %s", TextUtils.join(",", projection), OrderItemEntry.TABLE_NAME);
        rx.Observable<List<OrderItem>> orderItemObservableV1 =
                databaseHelper.createQuery(OrderItemEntry.TABLE_NAME, sql)
                        .mapToList(orderItemMapperFunction).take(50, TimeUnit.MILLISECONDS);

        return RxJavaInterop.toV2Observable(orderItemObservableV1);
    }

    @Override
    public Observable<OrderItem> getOne(String id) {
//        String sql = String.format("SELECT %s FROM %s WHERE %s LIKE ?",
//                TextUtils.join(",", projection), OrderItemEntry.TABLE_NAME, OrderItemEntry.COLUMN_NAME_ID);
//        rx.Observable<OrderItem> orderItemObservableV1 =
//                databaseHelper.createQuery(OrderItemEntry.TABLE_NAME, sql, id)
//                        .mapToOneOrDefault(orderItemMapperFunction, null);
//
//        return RxJavaInterop.toV2Observable(orderItemObservableV1);
        return null;
    }

    @Override
    public OrderItem save(OrderItem item) {
        checkNotNull(item);
        ContentValues contentValues = new ContentValues();
        contentValues.put(OrderItemEntry.COLUMN_NAME_ORDER_ID, item.getOrderId());
        contentValues.put(OrderItemEntry.COLUMN_NAME_PRODUCT_ID, item.getProductId());
        contentValues.put(OrderItemEntry.COLUMN_NAME_COUNTER, item.getCounter());
        contentValues.put(OrderItemEntry.COLUMN_NAME_SYNCED, item.getSynced());
        contentValues.put(OrderItemEntry.COLUMN_NAME_CHECKED_OUT, item.getChecked_out());
        long rowId = databaseHelper.insert(OrderItemEntry.TABLE_NAME, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        return getLastCreated();
    }

    private OrderItem getOrderItemUsingRowId(Long rowId){
        checkNotNull(rowId);
        String sql = String.format("SELECT * FROM %s WHERE ROWID = %d LIMIT 1",
                OrderItemEntry.TABLE_NAME, rowId);
        Cursor cursor = databaseHelper.query(sql, null);
        cursor.moveToLast();
        return getOrderItem(cursor);
    }

    @Override
    public int delete(String id) {
//        String selection = OrderItemEntry.COLUMN_NAME_ID + "LIKE ?";
//        String selectionArgs[] = {id};
//        return databaseHelper.delete(OrderItemEntry.TABLE_NAME, selection, selectionArgs);
        return 0;
    }

    @Override
    public int update(OrderItem item) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(OrderItemEntry.COLUMN_NAME_ORDER_ID, item.getOrderId());
//        contentValues.put(OrderItemEntry.COLUMN_NAME_PRODUCT_ID, item.getProductId());
//        contentValues.put(OrderItemEntry.COLUMN_NAME_COUNTER, item.getCounter());
//        contentValues.put(OrderItemEntry.COLUMN_NAME_SYNCED, item.getSynced());
//        contentValues.put(OrderItemEntry.COLUMN_NAME_CHECKED_OUT, item.getChecked_out());
//        String selection = OrderItemEntry.COLUMN_NAME_ID + "LIKE ?";
//        String[] selectionArgs = {item.getOrderId()};
//        return databaseHelper.update(OrderItemEntry.TABLE_NAME, contentValues, selection, selectionArgs);
        return 0;
    }

    @Override
    public void deleteAll() {
        databaseHelper.delete(OrderItemEntry.TABLE_NAME, null);
    }

    @Override
    public OrderItem getLastCreated() {
        String selectQuery = "SELECT * FROM " + OrderItemEntry.TABLE_NAME + " sqlite_sequence";
        Cursor cursor = databaseHelper.query(selectQuery, null);
        cursor.moveToLast();
        return getOrderItem(cursor);
    }


    @Override
    public Observable<List<OrderItem>> getOrderItemWithOrderId(String orderId) {
                String sql = String.format("SELECT %s FROM %s WHERE %s LIKE ?",
                TextUtils.join(",", projection), OrderItemEntry.TABLE_NAME, OrderItemEntry.COLUMN_NAME_ORDER_ID);
        rx.Observable<List<OrderItem>> orderItemObservableV1 =
                databaseHelper
                        .createQuery(OrderItemEntry.TABLE_NAME, sql, orderId)
                        .mapToList(orderItemMapperFunction).take(50, TimeUnit.MILLISECONDS);
        return RxJavaInterop.toV2Observable(orderItemObservableV1);
    }


}
