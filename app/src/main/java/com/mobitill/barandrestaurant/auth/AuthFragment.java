package com.mobitill.barandrestaurant.auth;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.data.waiter.waitermodels.response.Waiter;
import com.mobitill.barandrestaurant.register.RegisterActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuthFragment extends Fragment implements AuthContract.View {

    private static final String TAG = AuthFragment.class.getSimpleName();

    private AuthContract.Presenter mPresenter;
    private Unbinder mUnbinder;

    @BindView(R.id.et_password) EditText mPasswordEditText;
    @BindView(R.id.phoneNumber)EditText mPhoneNumber;
    @BindView(R.id.btnOk) Button mButtonOk;

    private List<Waiter> mWaiters = new ArrayList<>();


    public static AuthFragment newInstance() {
        Bundle args = new Bundle();
        AuthFragment fragment = new AuthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AuthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.auth_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
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

    @OnClick(R.id.btnOk)
    public void onClickSubmit(Button button){
        if (mPasswordEditText==null) {
            mPasswordEditText.setError("Password field is Empty");
        }
        if (mPhoneNumber == null){
            mPhoneNumber.setError("Phone Number field is Empty");
        }if (mPhoneNumber!=null && mPasswordEditText!=null){
        mPresenter.performLogin(mPhoneNumber.getText().toString(),mPasswordEditText.getText().toString(), mWaiters);
        mPasswordEditText.setText("");
        mPhoneNumber.setText("");
        }
    }

    @Override
    public void setPresenter(AuthContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void showLoadingWaitersError() {
        Toast.makeText(getActivity(), "waiters loading error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingIndicator(boolean b) {
        Toast.makeText(getActivity(), "waiters loaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWaitersLoaded(List<Waiter> waiters) {
        mWaiters = waiters;
    }

    @Override
    public void showPlaceOrderActivity() {
        startActivity(RegisterActivity.newIntent(getActivity()));
    }

    @Override
    public void showWaiterLoginError() {
        Toast.makeText(getActivity(), R.string.login_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginFailed() {
//        Snackbar.make(mButtonOk, R.string.login_failed, Snackbar.LENGTH_LONG).show();
        Toast.makeText(getActivity(), R.string.login_failed, Toast.LENGTH_SHORT).show();
    }
}
