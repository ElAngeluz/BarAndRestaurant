package com.mobitill.barandrestaurant.receipts_detail;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mobitill.barandrestaurant.MainApplication;
import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.checkout.CheckOutActivity;
import com.mobitill.barandrestaurant.data.order.model.Order;
import com.mobitill.barandrestaurant.data.orderItem.model.OrderItem;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReceiptsDetailFragment extends Fragment implements ReceiptsDetailContract.View {

    private static final String TAG = ReceiptsDetailFragment.class.getSimpleName();
    private static final String ARG_ORDER_ID = "arg_order_id";

    String mOrderId = "";


    @BindView(R.id.et_receipts_detail_orderNumber)
    EditText mEditTextReceiptDetailOrderNumber;

    @BindView(R.id.et_receipts_detail_receiptNumber)
    EditText mEditTextReceiptDetailReceiptNumber;


    @Inject
    ReceiptsDetailPresenter receiptsDetailPresenter;

    private ReceiptsDetailContract.Presenter mPresenter;

    public ReceiptsDetailFragment() {
        // Required empty public constructor
    }

    public static ReceiptsDetailFragment newInstance(String orderId) {

        Bundle args = new Bundle();
        args.putString(ARG_ORDER_ID, orderId);
        ReceiptsDetailFragment fragment = new ReceiptsDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerReceiptsDetailComponent.builder()
                .receiptsDetailPresenterModule(new ReceiptsDetailPresenterModule(this, getActivity()))
                .baseComponent(((MainApplication)getActivity().getApplication()).mBaseComponent())
                .build()
                .inject(this);

        mOrderId = getArguments().getString(ARG_ORDER_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.receipts_detail_fragment, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick(R.id.btn_receipts_detail_checkOut)
    public void receiptDetailCheckOutClick(){
       startActivity(CheckOutActivity.newIntent(getActivity(),mOrderId));
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
        mPresenter.getOrder(mOrderId);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void setPresenter(ReceiptsDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);

    }

    @Override
    public void showOrder(Order order) {
        mEditTextReceiptDetailOrderNumber.setText(order.getDisplayId());
        mEditTextReceiptDetailReceiptNumber.setText(order.getEntryId());
    }

    @Override
    public void showOrderItems(List<OrderItem> orderItems) {

    }
}
