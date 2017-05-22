package com.mobitill.barandrestaurant.register.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import com.mobitill.barandrestaurant.R
import com.mobitill.barandrestaurant.data.product.models.Product

/**
 * Created by andronicus on 5/19/2017.
 */

class ProductAdapterDelegate(activity: Activity) : AdapterDelegate<List<Product>>() {

    private val mLayoutInflater: LayoutInflater

    init {
        mLayoutInflater = activity.layoutInflater
    }

    override fun isForViewType(products: List<Product>, i: Int): Boolean {
        return products[i] is Product
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup): RecyclerView.ViewHolder {
        return ProductViewHolder(mLayoutInflater.inflate(R.layout.product_item, viewGroup, false))
    }

    override fun onBindViewHolder(products: List<Product>, i: Int, viewHolder: RecyclerView.ViewHolder, list: List<Any>) {
        val vh = viewHolder as ProductViewHolder

        vh.mProductButton.text = products[i].name
    }


    internal class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var mProductButton: Button

        init {
            mProductButton = itemView.findViewById(R.id.btn_product_item) as Button
        }
    }

    companion object {

        val TAG = ProductAdapterDelegate::class.java.simpleName
    }
}
