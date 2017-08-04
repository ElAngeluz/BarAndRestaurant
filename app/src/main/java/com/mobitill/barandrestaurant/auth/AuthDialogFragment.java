package com.mobitill.barandrestaurant.auth;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.data.waiter.waitermodels.response.Waiter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuthDialogFragment extends DialogFragment {

    private static final String TAG = "AuthDialogFragment";
    private static final String WAITER_NAME = "waiter_name";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String WAITERS = "waiters";

    private List<Waiter> mWaiters;
    private String mWaiterName;
    private String phoneNumber;
    private String password;
    private EditText mEditTextPassword;
    private AuthContract.Presenter mPresenter;

    public static AuthDialogFragment newInstance(String waiterName,String phoneNumber,ArrayList<Waiter> waiters) {

        Bundle args = new Bundle();
        args.putString(WAITER_NAME,waiterName);
        args.putString(PHONE_NUMBER,phoneNumber);
        args.putParcelableArrayList(WAITERS,waiters);

        AuthDialogFragment fragment = new AuthDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.auth_dialog_fragment, null);
        mEditTextPassword = (EditText) view.findViewById(R.id.dialog_et);
        mWaiters = getArguments().getParcelableArrayList(WAITERS);
        mWaiterName = getArguments().getString(WAITER_NAME);
        phoneNumber = getArguments().getString(PHONE_NUMBER);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Hello, " + mWaiterName)
                .setPositiveButton(R.string.string_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       password = mEditTextPassword.getText().toString();
                        if (phoneNumber.isEmpty() | password.isEmpty()) {
                            Toast.makeText(getActivity(), "Some fields are empty", Toast.LENGTH_SHORT).show();
                        } else if (phoneNumber != null && password != null) {
                            mPresenter.performLogin(phoneNumber, password, mWaiters);
                        } else {
                            Toast.makeText(getActivity(), R.string.login_error, Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.string_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .create();
    }

    public void setPresenter(AuthContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
