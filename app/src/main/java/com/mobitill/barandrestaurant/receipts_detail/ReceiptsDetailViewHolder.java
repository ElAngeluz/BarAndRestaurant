package com.mobitill.barandrestaurant.receipts_detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.data.product.models.Product;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andronicus on 5/26/2017.
 */

public class ReceiptsDetailViewHolder extends RecyclerView.ViewHolder {

    private Context mContext;

    @BindView(R.id.tv_receipts_detail_product_name)
    TextView receiptsDetailProduct;

    @BindView(R.id.tv_receipts_detail_product_amount)
    TextView tvreceiptsDetailProductAmount;

    @BindView(R.id.tv_receipts_detail_product_total)
    TextView tvreceiptsDetailProductTotal;

    private Product mProduct;

    public ReceiptsDetailViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);

    }

    public void bindView(Product product) {

        mProduct = product;
        receiptsDetailProduct.setText(product.getName());
//        receiptsDetailProductNumber.setText(product.toString().length());
//        receiptsDetailProductAmount.setText("e.g 200/= ");
    }
}
