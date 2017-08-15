package com.mobitill.barandrestaurant.auth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mobitill.barandrestaurant.MainApplication;
import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.utils.ActivityUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthActivity extends AppCompatActivity {

    private static final String TAG = AuthActivity.class.getSimpleName();

    @Inject AuthPresenter mPresenter;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        AuthFragment authFragment =
                (AuthFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if(authFragment == null){
            authFragment = AuthFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), authFragment, R.id.contentFrame);
        }
        DaggerAuthComponent.builder()
                .authPresenterModule(new AuthPresenterModule(authFragment))
                .baseComponent(((MainApplication) getApplication()).mBaseComponent())
                .build()
                .inject(this);
    }
}
