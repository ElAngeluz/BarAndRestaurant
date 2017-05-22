package com.mobitill.barandrestaurant.receipts;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.data.product.models.Product;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andronicus on 5/22/2017.
 */

public class ReceiptsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_product)
    TextView textViewProduct;

    @BindView(R.id.chkbx_checked_out)
    CheckBox checkBoxCheckedOut;

    public ReceiptsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        itemView.setClickable(true);
    }
    /*Subject to Change*/

    public void bindView(Product product){
        textViewProduct.setText("Product names go here");
    }
}
