package com.mobitill.barandrestaurant.register;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v4.BuildConfig;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.SingleFragmentActivity;

import javax.inject.Inject;

public class RegisterActivity extends SingleFragmentActivity {


    private static final String TAG = RegisterActivity.class.getSimpleName();

    @Inject
    RegisterPresenter mRegisterPresenter;

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, RegisterActivity.class);
        return intent;
    }


    @Override
    protected Fragment createFragment() {
        return RegisterFragment.newInstance();
    }


}
