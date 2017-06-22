package com.mobitill.barandrestaurant.checkout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.SingleFragmentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckOutActivity extends SingleFragmentActivity {

    private static final String TAG = CheckOutActivity.class.getSimpleName();
    private static final String ID = "orderId";
    private static final String TOTAL = "sumTotal";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_out_activity);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
    }

    public static Intent newIntent(Context context, String orderId,String sumTotal){
        Intent intent = new Intent(context, CheckOutActivity.class);
        intent.putExtra(ID, orderId);
        intent.putExtra(TOTAL,sumTotal);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return CheckOutFragment.newInstance(getIntent().getStringExtra(ID),getIntent().getStringExtra(TOTAL));
    }
}
