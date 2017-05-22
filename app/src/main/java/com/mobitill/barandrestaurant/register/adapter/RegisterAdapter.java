package com.mobitill.barandrestaurant.register.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;
import com.mobitill.barandrestaurant.data.product.models.Product;

import java.util.List;

/**
 * Created by james on 5/22/2017.
 */

public class RegisterAdapter extends RecyclerView.Adapter {

    public static final String TAG = RegisterAdapter.class.getSimpleName();

    private AdapterDelegatesManager<List<Product>> mDelegatesManager;
    private List<Product> mItems;

    public RegisterAdapter(Activity activity, List<Product> items){
        mItems = items;

        mDelegatesManager = new AdapterDelegatesManager<>();
        // AdapterDelegatesManager internally assigns view type integers
        mDelegatesManager.addDelegate(new ProductAdapterDelegate(activity));
    }

    @Override
    public int getItemViewType(int position) {
        return mDelegatesManager.getItemViewType(mItems, position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mDelegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mDelegatesManager.onBindViewHolder(mItems, position, holder);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setItems(List<Product> items) {
        mItems = items;
    }
}
