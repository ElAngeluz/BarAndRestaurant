package com.mobitill.barandrestaurant;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.mobitill.barandrestaurant.receipts.ReceiptsFragment;
import com.mobitill.barandrestaurant.register.RegisterFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by james on 3/13/2017.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.toolbar) Toolbar mToolbar;

    @Nullable
    @BindView(R.id.nav_view) BottomNavigationView mNavigationView;

    @Nullable
    @BindView(R.id.toolbar_tv)
    TextView mToolbarTextView;

    protected abstract Fragment createFragment();

    @LayoutRes
    protected int getLayoutResId(){
        return R.layout.activity_fragment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);

        mToolbarTextView.setEnabled(false);

        setSupportActionBar(mToolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
//        actionBar.setDisplayHomeAsUpEnabled(true);

        if(mNavigationView != null){
            setUpDrawerContent(mNavigationView);
        }


        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.contentFrame);
        if(fragment == null){
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.contentFrame, fragment)
                    .commit();
        }


    }

    private void setUpDrawerContent(BottomNavigationView navigationView) {
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                switch (item.getItemId()){
                    case R.id.action_order:
                        ft.replace(R.id.contentFrame, RegisterFragment.newInstance())
                            .commit();
                        break;
                    case R.id.action_orders:
                        ft.replace(R.id.contentFrame, ReceiptsFragment.newInstance())
                                .commit();
                        break;

                    case R.id.action_stats:
                        Toast.makeText(SingleFragmentActivity.this, "Account not yet set up", Toast.LENGTH_SHORT).show();
                    default:
                        break;
                }

                item.setChecked(true);
                return true;
            }
        });
    }


}
