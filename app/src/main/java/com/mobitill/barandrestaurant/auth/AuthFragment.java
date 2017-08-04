package com.mobitill.barandrestaurant.auth;


import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.data.waiter.waitermodels.response.Waiter;
import com.mobitill.barandrestaurant.register.RegisterActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuthFragment extends Fragment implements AuthContract.View,ClicksHandler{

    private static final String TAG = AuthFragment.class.getSimpleName();
    public static final String WAITER_NAME = "waiter_name";

    private AuthContract.Presenter mPresenter;
    private Unbinder mUnbinder;

    private TextInputEditText mInputEditText;
    public RecyclerView mRecyclerView;
    public WaitersListAdapter mListAdapter;
    public RecyclerView.LayoutManager mLayoutManager;
//    @BindView(R.id.et_password) EditText mPasswordEditText;
//    @BindView(R.id.phoneNumber)EditText mPhoneNumber;
//    @BindView(R.id.btnOk) Button mButtonOk;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_auth_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.waiters_list_rv);
        mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
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

//    @OnClick(R.id.btnOk)
//    public void onClickSubmit(Button button){
//        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
//        dialog.setTitle(mWaiters.get(8).getName());
//        dialog.show();

//        if (mPhoneNumber.getText().toString().isEmpty() | mPasswordEditText.getText().toString().isEmpty()){
//            Toast.makeText(getActivity(), "Some fields are empty", Toast.LENGTH_SHORT).show();
//        }
//        else if (mPhoneNumber!=null && mPasswordEditText!=null){
//        mPresenter.performLogin(mPhoneNumber.getText().toString(),mPasswordEditText.getText().toString(), mWaiters);
//        mPasswordEditText.setText("");
//        mPhoneNumber.setText("");
//        }else{
//            Toast.makeText(getActivity(), R.string.login_error, Toast.LENGTH_SHORT).show();
//
//        }
//    }

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
//        Toast.makeText(getActivity(), "waiters loaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWaitersLoaded(List<Waiter> waiters) {
        mWaiters = waiters;
        mListAdapter = new WaitersListAdapter(mWaiters,getActivity(),this);
        mRecyclerView.setAdapter(mListAdapter);
    }

    @Override
    public void showPlaceOrderActivity(String waiterName) {
        startActivity(RegisterActivity.newIntent(getActivity(),waiterName));
        sendWaiterName(waiterName);
    }
    /*
    * SharedPreferences is used here to send waiter name to Checkout Activity
    * */
    public void sendWaiterName(String waiterName){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(WAITER_NAME,waiterName);
        editor.commit();
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

    @Override
    public void invalidCredentials() {
        Toast.makeText(getActivity(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClicked(View view, int position) {

        mInputEditText = new TextInputEditText(getActivity());
        mInputEditText.setMaxLines(1);
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(mWaiters.get(position).getName());
        dialog.setView(mInputEditText);
        dialog.setPositiveButton("OK", (dialog1, which) -> {
            Toast.makeText(getActivity(), "Logging in...", Toast.LENGTH_SHORT).show();
        });
        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
