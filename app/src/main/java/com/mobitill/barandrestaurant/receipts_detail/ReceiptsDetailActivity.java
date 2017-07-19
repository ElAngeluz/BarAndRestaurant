package com.mobitill.barandrestaurant.receipts_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.SingleFragmentActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReceiptsDetailActivity extends SingleFragmentActivity{

    private static final String TAG = ReceiptsDetailActivity.class.getSimpleName();
    private static final String EXTRA_ORDER_ID = "extra_order_id";


    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.toolbar_tv)
    TextView mToolbarTextView;

    @Inject
    ReceiptsDetailPresenter receiptsDetailPresenter;

    public static Intent newIntent(Context context, String orderId){
        Intent intent = new Intent(context, ReceiptsDetailActivity.class);
        intent.putExtra(EXTRA_ORDER_ID, orderId);
        return intent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        mToolbarTextView.setEnabled(true);
        mToolbarTextView.setText(R.string.app_name);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.receipts_detail_activity;
    }

    @Override
    protected Fragment createFragment() {
        return ReceiptsDetailFragment.newInstance(getIntent().getStringExtra(EXTRA_ORDER_ID));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
