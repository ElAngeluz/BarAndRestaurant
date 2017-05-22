package com.mobitill.barandrestaurant.register.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.data.product.models.Product;

import java.util.List;

/**
 * Created by andronicus on 5/19/2017.
 */

public class ProductAdapterDelegate extends AdapterDelegate<List<Product>> {

    public static final String TAG = ProductAdapterDelegate.class.getSimpleName();

    private LayoutInflater mLayoutInflater;

    public ProductAdapterDelegate(Activity activity){
        mLayoutInflater = activity.getLayoutInflater();
    }

    @Override
    protected boolean isForViewType(@NonNull List<Product> products, int i) {
        return products.get(i) instanceof Product;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ProductViewHolder(mLayoutInflater.inflate(R.layout.product_item, viewGroup, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<Product> products, int i, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull List<Object> list) {
        ProductViewHolder vh = (ProductViewHolder) viewHolder;
        Product product = (Product) products.get(i);

        vh.mProductButton.setText(product.getName());
    }


    static class ProductViewHolder extends RecyclerView.ViewHolder{

        public Button mProductButton;

        public ProductViewHolder(View itemView) {
            super(itemView);
            mProductButton = (Button) itemView.findViewById(R.id.btn_product_item);
        }
    }
}
