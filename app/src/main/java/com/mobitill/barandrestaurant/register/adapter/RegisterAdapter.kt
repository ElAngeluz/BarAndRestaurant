package com.mobitill.barandrestaurant.register.adapter

import android.app.Activity
import android.support.v7.util.SortedList
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.mobitill.barandrestaurant.R
import com.mobitill.barandrestaurant.data.product.models.Product
import java.util.*

/**
 * Created by james on 5/22/2017.
 */

class RegisterAdapter(val activity: Activity,  val mComparator: Comparator<Product>, val callback: AdapterCallback) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var mSortedList = SortedList(Product::class.java, object : SortedList.Callback<Product>() {
        override fun compare(a: Product, b: Product): Int {
            return mComparator.compare(a, b)
        }

        override fun onInserted(position: Int, count: Int) {
            notifyItemRangeInserted(position, count)
        }

        override fun onRemoved(position: Int, count: Int) {
            notifyItemRangeRemoved(position, count)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            notifyItemMoved(fromPosition, toPosition)
        }

        override fun onChanged(position: Int, count: Int) {
            notifyItemRangeChanged(position, count)
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.equals(newItem)
        }

        override fun areItemsTheSame(item1: Product, item2: Product): Boolean {

            return item1 == item2
        }
    })


    companion object {
        val TAG = RegisterAdapter::class.java.simpleName
    }

    init {
        // AdapterDelegatesManager internally assigns view type integers
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(activity)
        return ProductViewHolder(activity, inflater.inflate(R.layout.product_item, parent, false), callback)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val vh = holder as ProductViewHolder
        vh.bind(mSortedList[position])
    }

    override fun getItemCount(): Int {
        return mSortedList.size()
    }

    fun setItems(items: SortedList<Product>) {
        mSortedList = items
    }

    fun add(product: Product){
        mSortedList.add(product)
    }

    fun remove(product: Product){
        mSortedList.remove(product)
    }

    fun add(products: List<Product>){
        mSortedList.addAll(products)
    }

    fun remove(products: List<Product>){
        mSortedList.beginBatchedUpdates()
        products.forEach { product: Product -> mSortedList.remove(product) }
        mSortedList.endBatchedUpdates()
    }

    fun replaceAll(products: List<Product>){
        mSortedList.beginBatchedUpdates()
        (mSortedList.size() - 1 downTo 0)
                .map { mSortedList.get(it) }
                .filterNot { products.contains(it) }
                .forEach { mSortedList.remove(it) }

       // products.forEach { product:Product -> add(product) }
        products.forEach { product:Product -> Log.i(TAG, "replaceAll: " + product.name) }
        mSortedList.addAll(products)
        mSortedList.endBatchedUpdates()
    }


    internal class ProductViewHolder(val activity: Activity, itemView: View, val callback: AdapterCallback) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        var mProductButton: Button
        var mProduct: Product? = null

        init {
            mProductButton = itemView.findViewById(R.id.btn_product_item) as Button
            itemView.setOnClickListener(this)
        }

        fun bind(product: Product){
            mProduct = product
            mProductButton.text = product.name
        }

        override fun onClick(v: View?) {
            callback.addToOrder(mProduct)
        }

    }

}

