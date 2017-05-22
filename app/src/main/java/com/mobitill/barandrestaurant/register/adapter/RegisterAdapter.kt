package com.mobitill.barandrestaurant.register.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager
import com.mobitill.barandrestaurant.data.product.models.Product

/**
 * Created by james on 5/22/2017.
 */

class RegisterAdapter(activity: Activity, private var mItems: List<Product>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mDelegatesManager: AdapterDelegatesManager<List<Product>> = AdapterDelegatesManager<List<Product>>()

    companion object {
        val TAG = RegisterAdapter::class.java.simpleName
    }

    init {
        // AdapterDelegatesManager internally assigns view type integers
        mDelegatesManager.addDelegate(ProductAdapterDelegate(activity))
    }

    override fun getItemViewType(position: Int): Int {
        return mDelegatesManager.getItemViewType(mItems!!, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return mDelegatesManager.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        mDelegatesManager.onBindViewHolder(mItems!!, position, holder as RecyclerView.ViewHolder)
    }

    override fun getItemCount(): Int {
        return mItems!!.size
    }

    fun setItems(items: List<Product>) {
        mItems = items
    }

}
