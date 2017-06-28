package com.mobitill.barandrestaurant.auth;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

    @BindView(R.id.password) EditText mPasswordEditText;
    @BindView(R.id.backspace) ImageButton mBackSpaceButton;
    @BindView(R.id._0) Button _0Button;
    @BindView(R.id._1) Button _1Button;
    @BindView(R.id._2) Button _2Button;
    @BindView(R.id._3) Button _3Button;
    @BindView(R.id._4) Button _4Button;
    @BindView(R.id._5) Button _5Button;
    @BindView(R.id._6) Button _6Button;
    @BindView(R.id._7) Button _7Button;
    @BindView(R.id._8) Button _8Button;
    @BindView(R.id._9) Button _9Button;
    @BindView(R.id.submit) Button mSubmitButton;

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
        View view = inflater.inflate(R.layout.fragment_auth, container, false);
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

    @OnClick(R.id.backspace)
    public void onClickBackSpace(ImageButton imageButton){
        int length = mPasswordEditText.getText().toString().length();
        if(length > 0){
            mPasswordEditText.getText().delete(length-1, length);
        }
    }

    @OnClick(R.id._0)
    public void onClick_0(Button button){
//        if(mPasswordEditText.getText().toString().length()!=3){
            mPasswordEditText.setText(mPasswordEditText.getText().append(getString(R.string._0)));

//        }
    }

    @OnClick(R.id._1)
    public void onClick_1(Button button){
//        if(mPasswordEditText.getText().toString().length()!=3){
            mPasswordEditText.setText(mPasswordEditText.getText().append(getString(R.string._1)));
//        }
    }

    @OnClick(R.id._2)
    public void onClick_2(Button button){
//        if(mPasswordEditText.getText().toString().length()!=3){
            mPasswordEditText.setText(mPasswordEditText.getText().append(getString(R.string._2)));
//        }
    }

    @OnClick(R.id._3)
    public void onClick_3(Button button){
//        if(mPasswordEditText.getText().toString().length()!=3){
            mPasswordEditText.setText(mPasswordEditText.getText().append(getString(R.string._3)));
//        }
    }

    @OnClick(R.id._4)
    public void onClick_4(Button button){
//        if(mPasswordEditText.getText().toString().length()!=3){
            mPasswordEditText.setText(mPasswordEditText.getText().append(getString(R.string._4)));
//        }
    }

    @OnClick(R.id._5)
    public void onClick_5(Button button){
//        if(mPasswordEditText.getText().toString().length()!=3){
            mPasswordEditText.setText(mPasswordEditText.getText().append(getString(R.string._5)));
//        }
    }

    @OnClick(R.id._6)
    public void onClick_6(Button button){
//        if(mPasswordEditText.getText().toString().length()!=3){
            mPasswordEditText.setText(mPasswordEditText.getText().append(getString(R.string._6)));
//        }
    }

    @OnClick(R.id._7)
    public void onClick_7(Button button){
//        if(mPasswordEditText.getText().toString().length()!=3){
            mPasswordEditText.setText(mPasswordEditText.getText().append(getString(R.string._7)));
//        }
    }

    @OnClick(R.id._8)
    public void onClick_8(Button button){
//        if(mPasswordEditText.getText().toString().length()!=3){
            mPasswordEditText.setText(mPasswordEditText.getText().append(getString(R.string._8)));
//        }
    }

    @OnClick(R.id._9)
    public void onClick_9(Button button){
//        if(mPasswordEditText.getText().toString().length()!=3){
            mPasswordEditText.setText(mPasswordEditText.getText().append(getString(R.string._9)));
//        }
    }

    @OnClick(R.id.submit)
    public void onClickSubmit(Button button){
        mPresenter.performLogin(mPasswordEditText.getText().toString(), mWaiters);
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
        Snackbar.make(mSubmitButton, R.string.login_failed, Snackbar.LENGTH_LONG).show();
    }
}
