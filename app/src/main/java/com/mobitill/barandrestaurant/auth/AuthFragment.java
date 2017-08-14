package com.mobitill.barandrestaurant.auth;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.data.waiter.waitermodels.response.Waiter;
import com.mobitill.barandrestaurant.register.RegisterActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
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

    public RecyclerView mRecyclerView;
    public WaitersListAdapter mListAdapter;
    public RecyclerView.LayoutManager mLayoutManager;
    private LinearLayout mLinearLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_auth_fragment, container, false);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.new_auth_list_item_layout);
        mUnbinder = ButterKnife.bind(this, view);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.waiters_list_rv);
        mLayoutManager = new GridLayoutManager(getActivity(),3);
//        mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
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
        Collections.sort(mWaiters, (o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
        mListAdapter = new WaitersListAdapter(mWaiters, getActivity(), this);
        mRecyclerView.setAdapter(mListAdapter);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.auth_fragment,menu);
        super.onCreateOptionsMenu(menu, inflater);
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
        FragmentManager manager = getFragmentManager();

        AuthDialogFragment dialogFragment = AuthDialogFragment.newInstance(mWaiters.get(position).getName(),mWaiters.get(position).getPhone(), (ArrayList<Waiter>) mWaiters);
        dialogFragment.show(manager,null);
        dialogFragment.setCancelable(false);
        dialogFragment.setPresenter(mPresenter);
    }
}
