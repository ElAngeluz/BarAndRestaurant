package com.mobitill.barandrestaurant.register;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by james on 5/10/2017.
 */

public class RegisterPresenter  implements RegisterContract.Presenter{

    @NonNull
    private final RegisterContract.View mView;

    @Inject
    public RegisterPresenter(@NonNull RegisterContract.View view){
        mView = checkNotNull(view);
    }

    /**
     * Method injection is used here to safely reference {@code this} after the object is created.
     */
    @Inject
    void setupListeners() {
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
