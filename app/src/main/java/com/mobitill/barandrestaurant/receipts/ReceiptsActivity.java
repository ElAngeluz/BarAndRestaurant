package com.mobitill.barandrestaurant.receipts;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.mobitill.barandrestaurant.SingleFragmentActivity;

import javax.inject.Inject;

/**
 * Created by andronicus on 5/22/2017.
 */

public class ReceiptsActivity extends SingleFragmentActivity {

    private static final String TAG = ReceiptsActivity.class.getSimpleName();

    @Inject
    ReceiptsPresenter presenter;

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, ReceiptsActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return null;
    }
}
