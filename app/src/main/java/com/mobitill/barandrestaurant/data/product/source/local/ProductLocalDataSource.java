package com.mobitill.barandrestaurant.data.product.source.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.mobitill.barandrestaurant.data.product.ProductDataSource;
import com.mobitill.barandrestaurant.data.product.models.Product;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import hu.akarnokd.rxjava.interop.RxJavaInterop;
import io.reactivex.Observable;
import rx.functions.Func1;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.mobitill.barandrestaurant.data.product.source.local.ProductPersistenceContract.ProductEntry;

/**
 * Created by james on 5/12/2017.
 */
@Singleton
public class ProductLocalDataSource implements ProductDataSource {

    public static final String TAG = ProductDataSource.class.getSimpleName();

    @NonNull
    private final BriteDatabase mDatabaseHelper;

    @NonNull
    private Func1<Cursor, Product> mProductMapperFunction;

    String projection[] = {
            ProductEntry.COLUMN_NAME_ID,
            ProductEntry.COLUMN_NAME_PRODUCT_NAME,
            ProductEntry.COLUMN_NAME_IDENTIFIER,
            ProductEntry.COLUMN_NAME_PRICE,
            ProductEntry.COLUMN_NAME_VAT,
            ProductEntry.COLUMN_NAME_BARCODE
    };

    @Inject
    public ProductLocalDataSource(@NonNull BriteDatabase databaseHelper){
        mDatabaseHelper = (databaseHelper);
        mProductMapperFunction = this::getProduct;
    }

    private Product getProduct(@NonNull Cursor c){
        String id = c.getString(c.getColumnIndexOrThrow(ProductEntry.COLUMN_NAME_ID));
        String barcode = c.getString(c.getColumnIndexOrThrow(ProductEntry.COLUMN_NAME_BARCODE));
        String identifier = c.getString(c.getColumnIndexOrThrow(ProductEntry.COLUMN_NAME_IDENTIFIER));
        String name = c.getString(c.getColumnIndexOrThrow(ProductEntry.COLUMN_NAME_PRODUCT_NAME));
        String price = c.getString(c.getColumnIndexOrThrow(ProductEntry.COLUMN_NAME_PRICE));
        String vat = c.getString(c.getColumnIndexOrThrow(ProductEntry.COLUMN_NAME_VAT));
        return new Product(id, barcode, identifier, name, price, vat);
    }

    @Override
    public Observable<List<Product>> getAll() {
        String sql = String.format("SELECT %s FROM %s", TextUtils.join(",", projection), ProductEntry.TABLE_NAME);
        rx.Observable<List<Product>> productObservableV1 =
                mDatabaseHelper.createQuery(ProductEntry.TABLE_NAME, sql)
                .mapToList(mProductMapperFunction).take(50, TimeUnit.MILLISECONDS);

        return RxJavaInterop.toV2Observable(productObservableV1);
    }

    @Override
    public Observable<Product> getOne(String id) {
        String sql = String.format("SELECT %s FROM %s WHERE %s LIKE ?",
                TextUtils.join(",", projection), ProductEntry.TABLE_NAME, ProductEntry.COLUMN_NAME_ID);
        rx.Observable<Product> productObservableV1 =
                mDatabaseHelper.createQuery(ProductEntry.TABLE_NAME, sql, id)
                .mapToOneOrDefault(mProductMapperFunction, null);
        return RxJavaInterop.toV2Observable(productObservableV1);
    }

    @Override
    public Product save(Product item) {
        checkNotNull(item);
        ContentValues contentValues = new ContentValues();
        contentValues.put(ProductEntry.COLUMN_NAME_ID, item.getId());
        contentValues.put(ProductEntry.COLUMN_NAME_BARCODE, item.getBarcode());
        contentValues.put(ProductEntry.COLUMN_NAME_IDENTIFIER, item.getIdentifier());
        contentValues.put(ProductEntry.COLUMN_NAME_PRICE, item.getPrice());
        contentValues.put(ProductEntry.COLUMN_NAME_VAT, item.getVat());
        contentValues.put(ProductEntry.COLUMN_NAME_PRODUCT_NAME, item.getName());
        Long rowId = mDatabaseHelper.insert(ProductEntry.TABLE_NAME, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        return getLastCreated();
    }




    @Override
    public int delete(String id) {
        String selection = ProductEntry.COLUMN_NAME_ID + "LIKE ?";
        String selectionArgs[] = {id};
        return mDatabaseHelper.delete(ProductEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public int update(Product item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ProductEntry.COLUMN_NAME_ID, item.getId());
        contentValues.put(ProductEntry.COLUMN_NAME_BARCODE, item.getBarcode());
        contentValues.put(ProductEntry.COLUMN_NAME_IDENTIFIER, item.getIdentifier());
        contentValues.put(ProductEntry.COLUMN_NAME_PRICE, item.getPrice());
        contentValues.put(ProductEntry.COLUMN_NAME_VAT, item.getVat());
        contentValues.put(ProductEntry.COLUMN_NAME_PRODUCT_NAME, item.getName());
        String selection = ProductEntry.COLUMN_NAME_ID + "LIKE ?";
        String[] selectionArgs = {item.getId()};
        return mDatabaseHelper.update(ProductEntry.TABLE_NAME, contentValues, selection, selectionArgs);
    }

    @Override
    public void deleteAll() {
        mDatabaseHelper.delete(ProductEntry.TABLE_NAME, null);
    }

    @Override
    public Product getLastCreated() {
        String selectQuery = "SELECT * FROM " + ProductEntry.TABLE_NAME + " sqlite_sequence";
        Cursor cursor = mDatabaseHelper.query(selectQuery, null);
        cursor.moveToLast();
        return getProduct(cursor);
    }

    @Override
    public Observable<Product> getProductWithIdentifier(String identifier) {
        String sql = String.format("SELECT %s FROM %s WHERE %s LIKE ?",
                TextUtils.join(",", projection), ProductEntry.TABLE_NAME, ProductEntry.COLUMN_NAME_IDENTIFIER);
        rx.Observable<Product> productObservableV1 = mDatabaseHelper
                .createQuery(ProductEntry.TABLE_NAME, sql, identifier)
                .mapToOneOrDefault(mProductMapperFunction, new Product());
        return RxJavaInterop.toV2Observable(productObservableV1);
    }

}
