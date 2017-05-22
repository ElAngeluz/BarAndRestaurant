package com.mobitill.barandrestaurant.receipts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.data.product.ProductRepository;
import com.mobitill.barandrestaurant.data.product.models.Product;

import java.util.List;

/**
 * Created by andronicus on 5/22/2017.
 */

public class ReceiptsProductAdapter extends RecyclerView.Adapter<ReceiptsViewHolder>{

    private LayoutInflater inflater;
    private List<Product> data; //Check later if List of data is from Product or ProductRepository

    public ReceiptsProductAdapter(List<Product> data) {
        this.data = data;
    }

    @Override
    public ReceiptsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View view =  inflater.inflate(R.layout.receipts_product_item, parent, false);
        ReceiptsViewHolder holder = new ReceiptsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ReceiptsViewHolder holder, int position) {

        Product product = data.get(position);
        holder.bindView(product);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
