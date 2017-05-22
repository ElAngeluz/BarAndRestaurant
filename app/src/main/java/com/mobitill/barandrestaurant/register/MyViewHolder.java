package com.mobitill.barandrestaurant.register;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.data.product.models.Product;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andronicus on 5/22/2017.
 */

public class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.btn_product_item)
        Button buttonProductItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setClickable(true);
        }
        public void bindView(Product product){
            buttonProductItem.setText("Product names go here");
        }
    }
