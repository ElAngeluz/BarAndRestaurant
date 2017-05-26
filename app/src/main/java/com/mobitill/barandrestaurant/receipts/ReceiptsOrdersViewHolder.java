package com.mobitill.barandrestaurant.receipts;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.data.order.model.Order;
import com.mobitill.barandrestaurant.data.product.models.Product;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andronicus on 5/22/2017.
 */

public class ReceiptsOrdersViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_order)
    TextView textViewOrder;

    @BindView(R.id.chkbx_checked_out)
    CheckBox checkBoxCheckedOut;

    public ReceiptsOrdersViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        itemView.setClickable(true);
    }
    /*Subject to Change*/

    public void bindView(Order order){
        textViewOrder.setText("Orders");
    }
}
