package com.mobitill.barandrestaurant.auth;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.data.waiter.waitermodels.response.Waiter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dataintegrated on 8/3/2017.
 */

public class WaitersListAdapter extends RecyclerView.Adapter<WaitersListAdapter.WaitersListViewHolder> {

    private List<Waiter> mWaiters;
    private Context mContext;
    private Waiter mWaiter;
    public ClicksHandler mClicksHandler;

    public WaitersListAdapter(List<Waiter> waiters, Context context,ClicksHandler clicksHandler) {
        this.mWaiters = waiters;
        this.mContext = context;
        this.mClicksHandler = clicksHandler;
    }

    @Override
    public WaitersListViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_auth_list_item, parent, false);
        return new WaitersListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WaitersListViewHolder holder, int position) {
        mWaiter = mWaiters.get(position);
        holder.mTextViewName.setText(mWaiters.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mWaiters.size();
    }

    public class WaitersListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTextViewName;
        private ImageView mImageViewAddPhoto;
        public WaitersListViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            mTextViewName = (TextView) itemView.findViewById(R.id.waiter_name_tv);
            mImageViewAddPhoto = (ImageView) itemView.findViewById(R.id.add_photo_iv);
            mImageViewAddPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Add Photo", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mClicksHandler.onItemClicked(v,position);
        }
    }
    public void setFilter(List<Waiter> waiters){
        mWaiters = new ArrayList<>();
        mWaiters.addAll(waiters);
        notifyDataSetChanged();

    }
}
