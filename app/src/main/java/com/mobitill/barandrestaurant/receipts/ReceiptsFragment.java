package com.mobitill.barandrestaurant.receipts;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    public static final String WAITER_NAME = "waiter_name";

    private String mWaiterName;

    @Inject ReceiptsPresenter mReceiptsPresenter;

    private ReceiptsContract.Presenter presenter;
    private Unbinder unbinder;
    private CommunicationHandler mHandler;
    private List<Order> mSortedList = new ArrayList<>();

//    private ReceiptsOrdersAdapter receiptsOrdersAdapter;

    private List<ReceiptOrders> receiptOrders;
    private ReceiptOrdersAdapter mReceiptOrdersAdapter;
    private RecyclerView.LayoutManager manager;

    @BindView(R.id.recView_receipts_orders)
    public RecyclerView receiptsRecyclerView;

    public static ReceiptsFragment newInstance(String waiterName) {

        Bundle args = new Bundle();
        args.putString(WAITER_NAME,waiterName);

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

        Bundle bundle = getArguments();
        mWaiterName = bundle.getString(WAITER_NAME);
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
    public void showOrdersPerDate(List<Order> sortedList) {
        mSortedList = sortedList;
    }
    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
        receiptOrders  = new ArrayList<>();
        List<String> orderDates = mReceiptsPresenter.getDate();
        for (String date:orderDates) {
            receiptOrders.add(new ReceiptOrders(date,mReceiptsPresenter.getOrdersForEachDate(date)));
        }
        if(isAdded()){
            if (mReceiptOrdersAdapter == null){

                mReceiptOrdersAdapter = new ReceiptOrdersAdapter(receiptOrders,getActivity(),mHandler,mWaiterName);
                receiptsRecyclerView.setAdapter(mReceiptOrdersAdapter);
            }
        }
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

//        List<String> orderDates = mReceiptsPresenter.getDate();
//        List<ReceiptOrders> receiptOrders = new ArrayList<>();
//        for (String date:orderDates) {
//            mReceiptsPresenter.getOrdersPerDate(date);
//                for (int i = 0; i < orderDates.size(); i++) {
//                receiptOrders.add(new ReceiptOrders(date,mSortedList));
//                    Log.d(TAG, "showOrdersPerDate: " + mSortedList.size());
//            }
//        }

         /*
        *
        * displaying normal recyclerview
        * */
//        if(isAdded()){
//            if (receiptsOrdersAdapter == null){
//
//                receiptsOrdersAdapter = new ReceiptsOrdersAdapter(orders, getActivity());
//                receiptsRecyclerView.setAdapter(receiptsOrdersAdapter);
//            }else {
//            }
//        }

        /*
        *
        * displaying expandable recyclerview
        * */

//        if(isAdded()){
//            if (mReceiptOrdersAdapter == null){
//
//                mReceiptOrdersAdapter = new ReceiptOrdersAdapter(receiptOrders,getActivity(),mHandler);
//                receiptsRecyclerView.setAdapter(mReceiptOrdersAdapter);
//            }
//        }


    }

    @Override
    public void showNoOrders() {

        Toast.makeText(getActivity(), "No orders!", Toast.LENGTH_SHORT).show();
    }
    /*
    * Interface for communication with host activity
    * */

    interface CommunicationHandler{
        void onOrderClick(String orderId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mHandler = (CommunicationHandler) context;
        } catch (ClassCastException e) {
            Log.i(TAG, "onAttach: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
