package com.mobitill.barandrestaurant.data.order.source.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.mobitill.barandrestaurant.data.order.model.Order;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import hu.akarnokd.rxjava.interop.RxJavaInterop;
import io.reactivex.Observable;
import rx.functions.Func1;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.mobitill.barandrestaurant.data.order.source.local.OrderPersistenceContract.OrderEntry;

@Singleton
public class OrderLocalDataSource implements OrderDataSource {

    private static final String TAG = OrderLocalDataSource.class.getSimpleName();

    @NonNull
    private final BriteDatabase mDatabaseHelper;

    @NonNull
    private Func1<Cursor, Order> mOrderMapperFunction;

    @Inject
    public OrderLocalDataSource(@NonNull BriteDatabase briteDatabaseHelper) {
        mDatabaseHelper = checkNotNull(briteDatabaseHelper);
        mOrderMapperFunction = this::getOrder;
    }

    private Order getOrder(@NonNull Cursor c) {
        String entryId = c.getString(c.getColumnIndexOrThrow(OrderEntry.COLUMN_NAME_ENTRY_ID));
        String name = c.getString(c.getColumnIndexOrThrow(OrderEntry.COLUMN_NAME_NAME));
        String waiterId = c.getString(c.getColumnIndexOrThrow(OrderEntry.COLUMN_NAME_WAITER_ID));
        Integer synced = c.getInt(c.getColumnIndexOrThrow(OrderEntry.COLUMN_NAME_SYNCED));
        Integer checkedOut = c.getInt(c.getColumnIndexOrThrow(OrderEntry.COLUMN_NAME_CHECKED_OUT));
        Long timestamp = c.getLong(c.getColumnIndexOrThrow(OrderEntry.COLUMN_NAME_TIME_STAMP));
        return new Order(entryId, name, waiterId, synced, checkedOut, timestamp);
    }

    @Override
    public Observable<List<Order>> getAll() {
        String[] projection = {
                OrderEntry.COLUMN_NAME_ENTRY_ID,
                OrderEntry.COLUMN_NAME_NAME,
                OrderEntry.COLUMN_NAME_WAITER_ID,
                OrderEntry.COLUMN_NAME_SYNCED,
                OrderEntry.COLUMN_NAME_CHECKED_OUT,
                OrderEntry.COLUMN_NAME_TIME_STAMP
        };

        String sql = String.format("SELECT %s FROM %s", TextUtils.join(",", projection), OrderEntry.TABLE_NAME);
        rx.Observable<List<Order>> observableV1 = mDatabaseHelper.createQuery(OrderEntry.TABLE_NAME, sql)
                .mapToList(mOrderMapperFunction).take(50, TimeUnit.MILLISECONDS);
        // convert observable from rxjava1 observable to rxjava2 observable
        Observable<List<Order>> observableV2 = RxJavaInterop.toV2Observable(observableV1);
        return observableV2;
    }

    @Override
    public Observable<Order> getOne(String id) {
        checkNotNull(id);
        String[] projection = {
                OrderEntry.COLUMN_NAME_ENTRY_ID,
                OrderEntry.COLUMN_NAME_NAME,
                OrderEntry.COLUMN_NAME_WAITER_ID,
                OrderEntry.COLUMN_NAME_SYNCED,
                OrderEntry.COLUMN_NAME_CHECKED_OUT,
                OrderEntry.COLUMN_NAME_TIME_STAMP
        };

        String sql = String.format("SELECT %s FROM %s WHERE %s LIKE ?",
                TextUtils.join(",", projection), OrderEntry.TABLE_NAME, OrderEntry.COLUMN_NAME_ENTRY_ID);
        rx.Observable<Order> orderObservableV1 = mDatabaseHelper.createQuery(OrderEntry.TABLE_NAME,
                sql, id).mapToOneOrDefault(mOrderMapperFunction, null);
        Observable<Order> orderObservableV2 = RxJavaInterop.toV2Observable(orderObservableV1);
        return orderObservableV2;
    }



    @Override
    public Order save(Order item) {
        checkNotNull(item);
        ContentValues contentValues = new ContentValues();
        contentValues.put(OrderEntry.COLUMN_NAME_ENTRY_ID, item.getEntryId());
        contentValues.put(OrderEntry.COLUMN_NAME_NAME, item.getName());
        contentValues.put(OrderEntry.COLUMN_NAME_WAITER_ID, item.getWaiterId());
        contentValues.put(OrderEntry.COLUMN_NAME_SYNCED, item.getSynced());
        contentValues.put(OrderEntry.COLUMN_NAME_CHECKED_OUT, item.getCheckedOut());
        contentValues.put(OrderEntry.COLUMN_NAME_TIME_STAMP, item.getTimeStamp());
        long rowId = mDatabaseHelper.insert(OrderEntry.TABLE_NAME, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        return getLastCreated();
    }

    @Override
    public Order getLastCreated(){
        String selectQuery = "SELECT * FROM " + OrderEntry.TABLE_NAME + " sqlite_sequence";
        Cursor cursor = mDatabaseHelper.query(selectQuery, null);
        cursor.moveToLast();
        return getOrder(cursor);
    }

    @Override
    public int delete(String id) {
        String selection = OrderEntry.COLUMN_NAME_ENTRY_ID + "LIKE ?";
        String selectionArgs[] = {id};
        return mDatabaseHelper.delete(OrderEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public int update(Order item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(OrderEntry.COLUMN_NAME_ENTRY_ID, item.getEntryId());
        contentValues.put(OrderEntry.COLUMN_NAME_NAME, item.getName());
        contentValues.put(OrderEntry.COLUMN_NAME_WAITER_ID, item.getWaiterId());
        contentValues.put(OrderEntry.COLUMN_NAME_SYNCED, item.getSynced());
        contentValues.put(OrderEntry.COLUMN_NAME_CHECKED_OUT, item.getCheckedOut());
        contentValues.put(OrderEntry.COLUMN_NAME_TIME_STAMP, item.getTimeStamp());
        String selection = OrderEntry.COLUMN_NAME_ENTRY_ID + " LIKE?";
        String[] selectionArgs = {item.getEntryId()};
        return mDatabaseHelper.update(OrderEntry.TABLE_NAME, contentValues, selection, selectionArgs);
    }

    @Override
    public void deleteAll() {
        mDatabaseHelper.delete(OrderEntry.TABLE_NAME, null);
    }

    @Override
    public Order getOrderFromRowId(Long rowId) {
        checkNotNull(rowId);
         String sql = String.format("SELECT * FROM %s WHERE ROWID = %d LIMIT 1",
                 OrderEntry.TABLE_NAME, rowId);
        Cursor cursor = mDatabaseHelper.query(sql, null);
        return getOrder(cursor);
    }
}

