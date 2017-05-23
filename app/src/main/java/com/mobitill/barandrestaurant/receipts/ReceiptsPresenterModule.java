package com.mobitill.barandrestaurant.receipts;

import android.content.Context;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

/**
 * Created by andronicus on 5/22/2017.
 */
@Module
public class ReceiptsPresenterModule {
    private final ReceiptsContract.View view;
    private final Context context;

    public ReceiptsPresenterModule(ReceiptsContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Provides
    @NonNull
    ReceiptsContract.View provideReceiptsContractView(){
        return view;
    }
}
