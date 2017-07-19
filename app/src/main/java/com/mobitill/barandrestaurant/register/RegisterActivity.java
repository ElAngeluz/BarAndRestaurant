package com.mobitill.barandrestaurant.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.SingleFragmentActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends SingleFragmentActivity {


    private static final String WAITER_NAME = "waiterName";
    private static final String TAG = RegisterActivity.class.getSimpleName();

    @BindView(R.id.toolbar_tv)
    TextView mToolbarTextView;

    private String waiterName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        waiterName = getIntent().getStringExtra(WAITER_NAME);
        mToolbarTextView.setEnabled(true);
        mToolbarTextView.setText(waiterName);
    }

    @Inject
    RegisterPresenter mRegisterPresenter;

    public static Intent newIntent(Context context,String waiterName){
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.putExtra(WAITER_NAME,waiterName);
        return intent;
    }


    @Override
    protected Fragment createFragment() {
        return RegisterFragment.newInstance();
    }


}
