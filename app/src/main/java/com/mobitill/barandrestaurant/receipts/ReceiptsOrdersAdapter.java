package com.mobitill.barandrestaurant.receipts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.data.order.model.Order;

import java.util.List;

/**
 * Created by andronicus on 5/22/2017.
 */

public class ReceiptsOrdersAdapter extends RecyclerView.Adapter<ReceiptsOrdersViewHolder>{

    private LayoutInflater inflater;
    private List<Order> orders;
    private Context mContext;
    private AdapterCommunication mCommunication;

    public ReceiptsOrdersAdapter(List<Order> orders, Context context) {
        this.orders = orders;
        mContext = context;
    }

    @Override
    public ReceiptsOrdersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View view =  inflater.inflate(R.layout.receipts_orders, parent, false);
        ReceiptsOrdersViewHolder holder = new ReceiptsOrdersViewHolder(view, mContext);
        return holder;
    }

    @Override
    public void onBindViewHolder(ReceiptsOrdersViewHolder holder, int position) {

        Order order = orders.get(position);
        holder.bindView(order);
        holder.mImageViewDelete.setOnClickListener(v -> {

            Log.d("onBindViewHolder", "position " + position);
            String orderId = orders.get(position).getEntryId();
                removeItem(position);
                try {

                    mCommunication.removeFromDB(orderId);
                    Log.d("LOG ID", "onBindViewHolder: " +orderId);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "Error deleting from Database", Toast.LENGTH_SHORT).show();
                }

        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void removeItem(int position){

        try {
            mCommunication.removeOrderFromList(position);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Error deleting from List", Toast.LENGTH_LONG).show();
        }

    }

    public  void AdapterListener(AdapterCommunication adapterCommunication){
        mCommunication = adapterCommunication;
    }
    public interface AdapterCommunication{
        void removeOrderFromList(int position);
        void removeFromDB(String orderId);
    }
}
