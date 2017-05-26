package com.mobitill.barandrestaurant.receipts_detail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.data.product.models.Product;


import java.util.List;

/**
 * Created by andronicus on 5/26/2017.
 */

public class ReceiptsDetailAdapter extends RecyclerView.Adapter<ReceiptsDetailViewHolder> {

    private LayoutInflater inflater;
    private List<Product> products;

    public ReceiptsDetailAdapter(List<Product> products) {
        this.products = products;
    }

    @Override
    public ReceiptsDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View view =  inflater.inflate(R.layout.receipts_detail_products, parent, false);
        ReceiptsDetailViewHolder holder = new ReceiptsDetailViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ReceiptsDetailViewHolder holder, int position) {

        Product product = products.get(position);
        holder.bindView();

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
