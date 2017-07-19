package com.mobitill.barandrestaurant.receipts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.data.order.model.Order;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by dataintegrated on 7/17/2017.
 */

public class ReceiptOrdersAdapter extends ExpandableRecyclerViewAdapter<ReceiptOrdersParentViewHolder,ReceiptOrdersChildViewHolder> {

    private Context mContext;

    public ReceiptOrdersAdapter(List<? extends ExpandableGroup> groups,Context context) {
        super(groups);
        mContext = context;
    }

    @Override
    public ReceiptOrdersParentViewHolder onCreateGroupViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receipt_orders_grouped,parent,false);
        return new ReceiptOrdersParentViewHolder(view);
    }

    @Override
    public ReceiptOrdersChildViewHolder onCreateChildViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receipts_orders,parent,false);
        return new ReceiptOrdersChildViewHolder(view,mContext);
    }

    @Override
    public void onBindChildViewHolder(ReceiptOrdersChildViewHolder holder, int position, ExpandableGroup group, int childIndex) {

        Order order = (Order) group.getItems().get(childIndex);
        holder.bindView(order);
    }

    @Override
    public void onBindGroupViewHolder(ReceiptOrdersParentViewHolder holder, int position, ExpandableGroup group) {

        holder.setDate(group.getTitle());
    }
}
