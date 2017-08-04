package com.mobitill.barandrestaurant.receipts;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.data.order.model.Order;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dataintegrated on 7/17/2017.
 */

public class ReceiptOrdersChildViewHolder extends ChildViewHolder implements View.OnClickListener{

    @BindView(R.id.tv_order)
    TextView textViewOrder;

    @BindView(R.id.tv_set_checkout)
    TextView mTextViewSetCheckout;

    private Order mOrder;
    private Context mContext;
    private ReceiptsFragment.CommunicationHandler mHandler;

    public ReceiptOrdersChildViewHolder(View itemView, Context context,ReceiptsFragment.CommunicationHandler communicationHandler) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        itemView.setClickable(true);
        itemView.setOnClickListener(this);
        mContext = context;
        mHandler = communicationHandler;
    }

    public void bindView(Order order){
        mOrder = order;

        textViewOrder.setText("Order " + order.getDisplayId());

        if (order.getCheckedOut() == 1){
            mTextViewSetCheckout.setText("CheckedOut");
        }else {
            mTextViewSetCheckout.setText("NotCheckedOut");
        }
    }

    @Override
    public void onClick(View v) {
        mHandler.onOrderClick(mOrder.getEntryId());
//        mContext.startActivity(ReceiptsDetailActivity.newIntent(mContext, mOrder.getEntryId().toString()));

    }
    public Long getOrderTimeStamp(){
        Toast.makeText(mContext, mOrder.getTimeStamp().toString(), Toast.LENGTH_SHORT).show();
        return mOrder.getTimeStamp();
    }
}
