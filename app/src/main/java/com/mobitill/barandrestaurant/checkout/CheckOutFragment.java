package com.mobitill.barandrestaurant.checkout;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.mobitill.barandrestaurant.MainApplication;
import com.mobitill.barandrestaurant.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckOutFragment extends Fragment implements CheckOutContract.View, CheckOutPresenter.talkToFragment{

    private static final String TAG = CheckOutFragment.class.getSimpleName();
    private static final String ID = "orderId";
    private static final String ARG_TOAL = "sumTotal";


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

    @BindView(R.id.btn_checked_out_ok)
    Button mButtonOk;


    private String orderId;
    private String mTotal;
    private Unbinder mUnbinder;
    private AlertDialog.Builder mAlertDialog;
    private ProgressDialog mProgressDialog;

    public CheckOutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderId = getArguments().getString(ID);
        mTotal = getArguments().getString(ARG_TOAL);
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

        mAlertDialog = new AlertDialog.Builder(getActivity());
       mUnbinder = ButterKnife.bind(this,view);
       mCheckBoxCash.setChecked(true);
        mEditTextCash.setText(mTotal);
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
        mEditTextMpesa.setText("");
    }

    @OnClick(R.id.chkbx_checked_out_mpesa)
    public void mpesaCheckboxClicked(){
        clearCheckbox();
        mCheckBoxMpesa.setChecked(true);
        mEditTextMpesa.setEnabled(true);
        mEditTextCash.setEnabled(false);
        mEditTextCash.setText("");
    }

    @OnClick(R.id.btn_checked_out_ok)
    public void OkButtonClick() {
        if (mCheckBoxMpesa.isChecked()) {
            mPresenter.makeCall(orderId);
        } else {
            mPresenter.checkout(orderId);
            getActivity().finish();
        }
    }

    @Override
    public String setPaymentMethod() {

        String paymentMethod;
        if(mCheckBoxCash.isChecked()){
            paymentMethod = mCheckBoxCash.getText().toString();

        }else if (mCheckBoxMpesa.isChecked()){
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

    public static CheckOutFragment newInstance(String orderId,String sumTotal) {

        Bundle args = new Bundle();
        args.putString(ID,orderId);
        args.putString(ARG_TOAL,sumTotal);
        CheckOutFragment fragment = new CheckOutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setPresenter(@NonNull CheckOutContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showDialog1(String message) {
        mAlertDialog.setTitle("Mpesa Request");
        mAlertDialog.setMessage(message);
        mAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.checkout(orderId);
                getActivity().finish();
            }
        });
        mAlertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
       mAlertDialog.show();
    }

    @Override
    public void showDialog2(String message) {

        mAlertDialog.setTitle("Mpesa Request");
        mAlertDialog.setMessage(message);
        mAlertDialog.setPositiveButton("TRY AGAIN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
               mEditTextMpesa.setError("Enter Correct Code");
            }
        });
        mAlertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mAlertDialog.show();
    }

    @Override
    public void showDialog3(String message) {

        mAlertDialog.setTitle("Mpesa Request");
        mAlertDialog.setMessage(message);
        mAlertDialog.setPositiveButton("RETRY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.makeCall(orderId);
            }
        });
        mAlertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mAlertDialog.show();
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Loading. Please Wait...");
        mProgressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        mProgressDialog.dismiss();

    }
}
