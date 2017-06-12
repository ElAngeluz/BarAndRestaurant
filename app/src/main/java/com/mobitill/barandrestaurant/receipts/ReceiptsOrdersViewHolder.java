package com.mobitill.barandrestaurant.receipts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.data.order.model.Order;
import com.mobitill.barandrestaurant.receipts_detail.ReceiptsDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andronicus on 5/22/2017.
 */

public class ReceiptsOrdersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.tv_order)
    TextView textViewOrder;

    @BindView(R.id.tv_setcheckout)
    TextView textViewSetCheckout;

    private Order mOrder;
    private Context mContext;
    private View mView;

    public ReceiptsOrdersViewHolder(View itemView, Context context) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mView=itemView;
        itemView.setOnClickListener(this);

        mContext = context;
    }


    public void bindView(Order order) {
        mOrder = order;

        textViewOrder.setText("Order " + order.getDisplayId());

        if (order.getCheckedOut() == 1) {

            textViewSetCheckout.setText("CheckedOut");
            mView.setEnabled(false);
        } else {

            textViewSetCheckout.setText("Not CheckedOut");
            mView.setEnabled(true);

        }
    }

    @Override
    public void onClick(View v) {
        mContext.startActivity(ReceiptsDetailActivity.newIntent(mContext, mOrder.getEntryId().toString()));
    }
}
