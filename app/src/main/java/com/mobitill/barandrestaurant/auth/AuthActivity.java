package com.mobitill.barandrestaurant.auth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mobitill.barandrestaurant.MainApplication;
import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.utils.ActivityUtils;

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

        DaggerAuthComponent.builder()
                .authPresenterModule(new AuthPresenterModule(authFragment, this))
                .waitersRepositoryComponent(((MainApplication)getApplication()).getWaitersRepositoryComponent())
                .build()
                .inject(this);
    }


}
