package com.mobitill.barandrestaurant.receipts;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by dataintegrated on 7/17/2017.
 */

public class ReceiptOrders extends ExpandableGroup{

    public ReceiptOrders(String title, List items) {
        super(title, items);
    }

}