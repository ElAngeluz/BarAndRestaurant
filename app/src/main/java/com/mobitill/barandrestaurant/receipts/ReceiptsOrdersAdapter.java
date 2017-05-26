package com.mobitill.barandrestaurant.receipts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.data.order.model.Order;
import com.mobitill.barandrestaurant.data.product.models.Product;

import java.util.List;

/**
 * Created by andronicus on 5/22/2017.
 */

public class ReceiptsOrdersAdapter extends RecyclerView.Adapter<ReceiptsOrdersViewHolder>{

    private LayoutInflater inflater;
    private List<Order> data; //Check later if List of data is from Product or ProductRepository

    public ReceiptsOrdersAdapter(List<Order> data) {
        this.data = data;
    }

    @Override
    public ReceiptsOrdersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View view =  inflater.inflate(R.layout.receipts_orders, parent, false);
        ReceiptsOrdersViewHolder holder = new ReceiptsOrdersViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ReceiptsOrdersViewHolder holder, int position) {

        Order order = data.get(position);
        holder.bindView(order);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
