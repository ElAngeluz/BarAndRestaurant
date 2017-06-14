package com.mobitill.barandrestaurant.checkout;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.mobitill.barandrestaurant.MainApplication;
import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.receipts.ReceiptsActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckOutFragment extends Fragment implements CheckOutContract.View {

    private static final String TAG = CheckOutFragment.class.getSimpleName();
    private static final String ID = "orderId";

    @Inject CheckOutPresenter mCheckOutPresenter;
    public CheckOutContract.Presenter mPresenter;


    @BindView(R.id.chkbx_checked_out_cash)
    CheckBox mCheckBoxCash;

    @BindView(R.id.chkbx_checked_out_mpesa)
    CheckBox mCheckBoxMpesa;

    @BindView(R.id.editText_checked_out_cash)
    EditText mEditTextCash;

    @BindView(R.id.editText_checked_out_mpesa)
    EditText mEditTextMpesa;


    private String orderId;
    private Unbinder mUnbinder;

    public CheckOutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderId = getArguments().getString(ID);
        DaggerCheckOutComponent.builder()
                .checkOutPresenterModule(new CheckOutPresenterModule(this))
                .baseComponent(((MainApplication) getActivity().getApplication()).mBaseComponent())
                .build()
                .inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.check_out_fragment, container, false);
       mUnbinder = ButterKnife.bind(this,view);
       mCheckBoxCash.setChecked(true);
       mEditTextMpesa.setEnabled(false);
        return view;
    }

    public void clearCheckbox(){
        mCheckBoxCash.setChecked(false);
        mCheckBoxMpesa.setChecked(false);
    }

    @OnClick(R.id.chkbx_checked_out_cash)
    public void cashCheckboxClicked(){
        clearCheckbox();
        mCheckBoxCash.setChecked(true);
        mEditTextCash.setEnabled(true);
        mEditTextMpesa.setEnabled(false);
    }

    @OnClick(R.id.chkbx_checked_out_mpesa)
    public void mpesaCheckboxClicked(){
        clearCheckbox();
        mCheckBoxMpesa.setChecked(true);
        mEditTextMpesa.setEnabled(true);
        mEditTextCash.setEnabled(false);
    }

    @OnClick(R.id.btn_checked_out_ok)
    public void OkButtonClick(){
        //startActivity(ReceiptsDetailActivity.newIntent(getActivity(),orderId));
        mPresenter.checkout(orderId);
        startActivity(ReceiptsActivity.newIntent(getActivity()));
        getActivity().finish();
    }

    @Override
    public String setPaymentMethod() {

        String paymentMethod;
        if(mCheckBoxCash.isChecked()){
            mEditTextMpesa.setClickable(false);
            mEditTextMpesa.setEnabled(false);
            mEditTextMpesa.setOnClickListener(null);
            mEditTextMpesa.setCursorVisible(false);
            mEditTextMpesa.setFocusableInTouchMode(false);
            mEditTextMpesa.setInputType(InputType.TYPE_NULL);
            mEditTextMpesa.setFocusableInTouchMode(false);
            mEditTextMpesa.setFocusable(false);
            paymentMethod = mCheckBoxCash.getText().toString();

        }else if (mCheckBoxMpesa.isChecked()){
            mEditTextCash.setClickable(false);
            mEditTextCash.setEnabled(false);
            mEditTextCash.setFocusable(false);
            mEditTextCash.setOnClickListener(null);
            mEditTextCash.setCursorVisible(false);
            mEditTextCash.setFocusableInTouchMode(false);
            mEditTextCash.setInputType(InputType.TYPE_NULL);
            mEditTextCash.setFocusableInTouchMode(false);
            paymentMethod = mCheckBoxMpesa.getText().toString();
        }else {
            paymentMethod = "";
        }
        return paymentMethod;
    }

    public String setAmount(){
        String amount = mEditTextCash.getText().toString();
        return amount;
    }

    @Override
    public String setTransactionsId() {
        String transactionsId = mEditTextMpesa.getText().toString();
        return transactionsId;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    public static CheckOutFragment newInstance(String orderId) {

        Bundle args = new Bundle();
        args.putString(ID,orderId);
        CheckOutFragment fragment = new CheckOutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setPresenter(@NonNull CheckOutContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
