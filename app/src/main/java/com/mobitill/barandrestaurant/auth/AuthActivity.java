package com.mobitill.barandrestaurant.auth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mobitill.barandrestaurant.MainApplication;
import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.utils.ActivityUtils;
import com.mobitill.barandrestaurant.utils.interceptors.AESEncryptor;

import javax.inject.Inject;

public class AuthActivity extends AppCompatActivity {

    private static final String TAG = AuthActivity.class.getSimpleName();

    @Inject AuthPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        AuthFragment authFragment =
                (AuthFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if(authFragment == null){
            authFragment = AuthFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), authFragment, R.id.contentFrame);
        }

        try {
            Log.i(TAG, "onCreate: test" + AESEncryptor.decrypt("F1071D03CF6A69538871D34C6DF003325C9CF48C2B2A88EABDBB34BBB59E3659B1826085BBC5076E8C493D4C46361DDB"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        DaggerAuthComponent.builder()
                .authPresenterModule(new AuthPresenterModule(authFragment, this))
                .build()
                .inject(this);
    }


}
