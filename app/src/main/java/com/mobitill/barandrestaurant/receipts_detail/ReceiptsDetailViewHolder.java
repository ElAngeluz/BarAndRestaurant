package com.mobitill.barandrestaurant.receipts_detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mobitill.barandrestaurant.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andronicus on 5/26/2017.
 */

public class ReceiptsDetailViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_receipts_detail_product)
    TextView receiptsDetailProduct;

    @BindView(R.id.tv_receipts_detail_product_number)
    TextView receiptsDetailProductNumber;

    @BindView(R.id.tv_receipts_detail_product_amount)
    TextView receiptsDetailProductAmount;

    public ReceiptsDetailViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);

    }

    public void bindView() {
    }
}
