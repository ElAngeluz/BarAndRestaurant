package com.mobitill.barandrestaurant.receipts_detail;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobitill.barandrestaurant.MainApplication;
import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.checkout.CheckOutActivity;
import com.mobitill.barandrestaurant.data.order.model.Order;
import com.mobitill.barandrestaurant.data.orderItem.model.OrderItem;
import com.mobitill.barandrestaurant.data.product.models.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReceiptsDetailFragment extends Fragment implements ReceiptsDetailContract.View {

    private static final String TAG = ReceiptsDetailFragment.class.getSimpleName();
    private static final String ARG_ORDER_ID = "arg_order_id";

    String mOrderId = "";

    Product mProduct;

    private  Double total = 0.0;

    private String Total;

    @BindView(R.id.et_receipts_detail_orderNumber)
    EditText mEditTextReceiptDetailOrderNumber;

//    @BindView(R.id.et_receipts_detail_receiptNumber)
//    EditText mEditTextReceiptDetailReceiptNumber;

    @BindView(R.id.linearLayout_items)
    public LinearLayout itemsLinearLayout;

    @BindView(R.id.tv_sum_total)
    TextView sumTotalDisplay;


    @Inject
    ReceiptsDetailPresenter receiptsDetailPresenter;

    private ReceiptsDetailContract.Presenter mPresenter;
    private Unbinder mUnbinder;

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
                .baseComponent(((MainApplication) getActivity().getApplication()).mBaseComponent())
                .build()
                .inject(this);

        mOrderId = getArguments().getString(ARG_ORDER_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.receipts_detail_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mEditTextReceiptDetailOrderNumber.setEnabled(false);
//        mEditTextReceiptDetailReceiptNumber.setEnabled(false);
        return view;
    }

    @OnClick(R.id.btn_receipts_detail_checkOut)
    public void receiptDetailCheckOutClick() {
        startActivity(CheckOutActivity.newIntent(getActivity(), mOrderId));
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
        mPresenter.getOrder(mOrderId);
        mPresenter.getOrderItems(mOrderId);

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
//        mEditTextReceiptDetailReceiptNumber.setText(order.getEntryId());

    }


    @Override
    public void showOrderItems(List<OrderItem> orderItems) {
//        refactored

        itemsLinearLayout.removeAllViews();
        Map<String, Stack<OrderItem>> orderItemMap = mPresenter.aggregateOrderItems(orderItems);

        /* display the products */

        for(HashMap.Entry<String, Stack<OrderItem>> entry : orderItemMap.entrySet()){

            String productName = entry.getValue().peek().getProductName();
            String quantity = String.valueOf(entry.getValue().size());
            String productPrice = entry.getValue().peek().getProductPrice();
            String price = String.valueOf(Double.parseDouble(productPrice) * Double.parseDouble(quantity));

            total = 0.0;
            total = total + Double.parseDouble(price);
            Total = String.valueOf(total);

            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.receipts_detail_products,itemsLinearLayout, false);


            TextView tvProductName = (TextView) view.findViewById(R.id.tv_receipts_detail_product_name);
            tvProductName.setText(productName);

            TextView tvProductAmount = (TextView) view.findViewById(R.id.tv_receipts_detail_product_amount);
            tvProductAmount.setText(quantity);

            TextView tvProductPriceTotal = (TextView) view.findViewById(R.id.tv_receipts_detail_product_total);
            tvProductPriceTotal.setText(price);

            itemsLinearLayout.addView(view);
            sumTotalDisplay.setText(Total);

        }

    }

    @Override
    public void showProducts(List<Product> products) {

    }
}
