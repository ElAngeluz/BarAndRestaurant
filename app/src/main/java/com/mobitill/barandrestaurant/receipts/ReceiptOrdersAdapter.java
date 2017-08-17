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

    private static final String TAG = "ReceiptOrdersAdapter";
    private Context mContext;
    private ReceiptsFragment.CommunicationHandler mHandler;
    private Order mOrder;
    private String mWaiterName;

    public ReceiptOrdersAdapter(List<? extends ExpandableGroup> groups,Context context,ReceiptsFragment.CommunicationHandler communicationHandler, String waiterName) {
        super(groups);
        mContext = context;
        mHandler = communicationHandler;
        this.mWaiterName = waiterName;
    }

    @Override
    public ReceiptOrdersParentViewHolder onCreateGroupViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receipt_orders_grouped,parent,false);
        return new ReceiptOrdersParentViewHolder(view);
    }

    @Override
    public ReceiptOrdersChildViewHolder onCreateChildViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receipts_orders,parent,false);
        return new ReceiptOrdersChildViewHolder(view, mContext, mHandler,mWaiterName);
    }

    @Override
    public void onBindChildViewHolder(ReceiptOrdersChildViewHolder holder, int position, ExpandableGroup group, int childIndex) {

        Order order = (Order) group.getItems().get(childIndex);
        mOrder = order;
        holder.bindView(order);
    }

    @Override
    public void onBindGroupViewHolder(ReceiptOrdersParentViewHolder holder, int position, ExpandableGroup group) {

        holder.setDate(group.getTitle());
    }
}
