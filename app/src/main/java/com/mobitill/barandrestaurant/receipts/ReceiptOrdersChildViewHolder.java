package com.mobitill.barandrestaurant.receipts;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.data.order.model.Order;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dataintegrated on 7/17/2017.
 */

public class ReceiptOrdersChildViewHolder extends ChildViewHolder implements View.OnClickListener{

    public static final String WAITER_NAME = "waiter_name";

    @BindView(R.id.tv_order)
    TextView textViewOrder;

    @BindView(R.id.tv_set_checkout)
    TextView mTextViewSetCheckout;

    private Order mOrder;
    private Context mContext;
    private ReceiptsFragment.CommunicationHandler mHandler;
    private String mWaiterName;

    public ReceiptOrdersChildViewHolder(View itemView, Context context,ReceiptsFragment.CommunicationHandler communicationHandler,String waiterName) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        itemView.setClickable(true);
        itemView.setOnClickListener(this);
        mContext = context;
        mHandler = communicationHandler;
        this.mWaiterName = waiterName;
    }

    public void bindView(Order order){
        mOrder = order;

        textViewOrder.setText("Order " + order.getDisplayId());

        if (order.getCheckedOut() == 1){
            mTextViewSetCheckout.setText(R.string.checked_out);
        }else {
            mTextViewSetCheckout.setText(R.string.not_checked_out);
        }
    }

    @Override
    public void onClick(View v) {
        mHandler.onOrderClick(mOrder.getEntryId());
//        mContext.startActivity(ReceiptsDetailActivity.newIntent(mContext, mOrder.getEntryId().toString()));

    }
}
