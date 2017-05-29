package com.mobitill.barandrestaurant.receipts_detail;

import android.content.Context;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

/**
 * Created by andronicus on 5/26/2017.
 */
@Module
public class ReceiptsDetailPresenterModule {

   private final ReceiptsDetailContract.View view;
   private final Context context;

    public ReceiptsDetailPresenterModule(ReceiptsDetailContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Provides
    @NonNull
    ReceiptsDetailContract.View providesReceiptsDetailContractView(){
        return view;
    }
}
