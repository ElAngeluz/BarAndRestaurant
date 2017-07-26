package com.mobitill.barandrestaurant.checkout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.SingleFragmentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckOutActivity extends SingleFragmentActivity {

    private static final String TAG = CheckOutActivity.class.getSimpleName();
    public static final String WAITER_NAME = "waiter_name";

    private static final String ID = "orderId";
    private static final String TOTAL = "sumTotal";

    @BindView(R.id.toolbar_tv)
    TextView mTextViewToolbar;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String waiterName = sharedPreferences.getString(WAITER_NAME,"CheckOutActivity");
        setContentView(R.layout.check_out_activity);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mTextViewToolbar.setEnabled(true);
        mTextViewToolbar.setText(waiterName);

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
