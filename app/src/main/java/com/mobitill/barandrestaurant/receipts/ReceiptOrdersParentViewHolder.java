package com.mobitill.barandrestaurant.receipts;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobitill.barandrestaurant.R;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

/**
 * Created by dataintegrated on 7/17/2017.
 */

public class ReceiptOrdersParentViewHolder extends GroupViewHolder {

    private TextView dateContainer;
    private ImageView dropDownIcon;

    public ReceiptOrdersParentViewHolder(View itemView) {
        super(itemView);
        dateContainer = (TextView) itemView.findViewById(R.id.date_container);
        dropDownIcon = (ImageView) itemView.findViewById(R.id.drop_down_icon);
    }
    public void setDate(String date){
       dateContainer.setText(date);
    }
}
