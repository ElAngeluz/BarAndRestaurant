package com.mobitill.barandrestaurant.receipts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.receipts_detail.ReceiptsDetailActivity;
import com.mobitill.barandrestaurant.receipts_detail.ReceiptsDetailFragment;
import com.mobitill.barandrestaurant.register.RegisterActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andronicus on 5/22/2017.
 */

public class ReceiptsActivity extends AppCompatActivity implements ReceiptsFragment.CommunicationHandler{

    private boolean dualPane;
    public static final String WAITER_NAME = "waiter_name";
    private static final String ORDER_ID = "order_Id";

    private static final String TAG = ReceiptsActivity.class.getSimpleName();

    private String mWaiter;
    private String mOrderId;

    @Nullable
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Nullable
    @BindView(R.id.nav_view)
    BottomNavigationView mNavigationView;

    @Nullable
    @BindView(R.id.toolbar_tv)
    TextView mToolbarTextView;

    @Inject
    ReceiptsPresenter presenter;

    public static Intent newIntent(Context context){
        return new Intent(context, ReceiptsActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_activity);
        ButterKnife.bind(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String waiterName = sharedPreferences.getString(WAITER_NAME,"CheckOutActivity");
        mWaiter = waiterName;

        mToolbarTextView.setText(waiterName);

        setSupportActionBar(mToolbar);

        if(mNavigationView != null){
            setUpDrawerContent(mNavigationView);
        }

        FrameLayout detailsLayout = (FrameLayout) findViewById(R.id.detailFrame);
        dualPane = (detailsLayout != null && (detailsLayout.getVisibility()) == View.VISIBLE);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.listFrame);
        if (fragment == null){
            fragment = ReceiptsFragment.newInstance(mWaiter);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.listFrame,fragment)
                    .commit();
        }
    }
    @Override
    public void onOrderClick(String orderId) {
        mOrderId = orderId;

        if (dualPane){
            Fragment fragment = ReceiptsDetailFragment.newInstance(mOrderId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detailFrame,fragment)
                    .commit();
        }else
        {
            startActivity(ReceiptsDetailActivity.newIntent(ReceiptsActivity.this,mOrderId));
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
                        startActivity(RegisterActivity.newIntent(ReceiptsActivity.this,mWaiter));
//                        ft.replace(R.id.contentFrame, RegisterFragment.newInstance())
//                                .commit();
                        break;
                    case R.id.action_orders:
                        startActivity(ReceiptsActivity.newIntent(ReceiptsActivity.this));
//                        ft.replace(R.id.contentFrame, ReceiptsFragment.newInstance())
//                                .commit();
                        break;

                    case R.id.action_stats:
                        Toast.makeText(ReceiptsActivity.this, "Account not yet set up", Toast.LENGTH_SHORT).show();
                    default:
                        break;
                }

                item.setChecked(true);
                return true;
            }
        });
    }
}
