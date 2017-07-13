package com.mobitill.barandrestaurant.receipts;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobitill.barandrestaurant.MainApplication;
import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.data.order.model.Order;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReceiptsFragment extends Fragment implements ReceiptsContract.View{
    private static final String TAG = ReceiptsFragment.class.getSimpleName();

    @Inject ReceiptsPresenter mReceiptsPresenter;

    private ReceiptsContract.Presenter presenter;
    private Unbinder unbinder;

    private ReceiptsOrdersAdapter receiptsOrdersAdapter;
    private RecyclerView.LayoutManager manager;
    private List<Order> orders = new ArrayList<>();


    @BindView(R.id.recView_receipts_orders)
    public RecyclerView receiptsRecyclerView;

    public static ReceiptsFragment newInstance() {

        Bundle args = new Bundle();

        ReceiptsFragment fragment = new ReceiptsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ReceiptsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        DaggerReceiptsComponent.builder()
                .receiptsPresenterModule(new ReceiptsPresenterModule(this))
                .baseComponent(((MainApplication) getActivity().getApplication()).mBaseComponent())
                .build()
                .inject(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.receipts_fragment, container, false);
        unbinder = ButterKnife.bind(this,view);
        manager = new LinearLayoutManager(getActivity(), 1, false);
        receiptsRecyclerView.setLayoutManager(manager);
        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void setPresenter(ReceiptsContract.Presenter presenter) {
        this.presenter =checkNotNull(presenter);
    }

    @Override
    public void checkedOut() {

    }

    @Override
    public void notCheckedOut() {

    }

    @Override
    public void showOrders(List<Order> orders) {

        if(isAdded()){
            if (receiptsOrdersAdapter == null){

                receiptsOrdersAdapter = new ReceiptsOrdersAdapter(orders, getActivity());
                receiptsOrdersAdapter.AdapterListener(new ReceiptsOrdersAdapter.AdapterCommunication() {
                    @Override
                    public void removeOrderFromList(int position) {
                        orders.remove(position);
                        receiptsOrdersAdapter.notifyItemRemoved(position);
                        receiptsOrdersAdapter.notifyItemRangeChanged(position, orders.size()-1);
                        receiptsOrdersAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void removeFromDB(String orderId) {
                        Log.d(TAG, "position and Id: " + orderId);
                        mReceiptsPresenter.deleteOrderFromDB(orderId);
                        Log.d(TAG, "before deleting: " + orderId);
                    }
                });
                receiptsRecyclerView.setAdapter(receiptsOrdersAdapter);
            }else {
            }
        }

    }

    @Override
    public void showNoOrders() {

    }

}
