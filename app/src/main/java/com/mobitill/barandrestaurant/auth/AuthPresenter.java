package com.mobitill.barandrestaurant.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.f2prateek.rx.preferences2.Preference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.data.waiter.WaitersRepository;
import com.mobitill.barandrestaurant.data.waiter.waitermodels.response.Waiter;
import com.mobitill.barandrestaurant.utils.schedulers.BaseScheduleProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by james on 4/27/2017.
 */

public class AuthPresenter implements AuthContract.Presenter {

    private static final String TAG = AuthPresenter.class.getSimpleName();

    @NonNull
    private final AuthContract.View mView;

    @NonNull
    private final Context mContext;

    @NonNull
    private final WaitersRepository mWaitersRepository;

    @NonNull
    private final BaseScheduleProvider mScheduleProvider;

    @NonNull
    private final RxSharedPreferences mRxSharedPreferences;

    @NonNull
    private CompositeDisposable mSubscriptions;

    @Inject
    AuthPresenter(@NonNull AuthContract.View view,
                  @NonNull Context context,
                  @NonNull WaitersRepository waitersRepository,
                  @NonNull BaseScheduleProvider scheduleProvider,
                  @NonNull RxSharedPreferences rxSharedPreferences,
                  @NonNull SharedPreferences sharedPreferences){
        mContext = checkNotNull(context);
        mView = checkNotNull(view);
        mWaitersRepository = checkNotNull(waitersRepository);
        mScheduleProvider = checkNotNull(scheduleProvider, "schedule provider should not be null");
        mSubscriptions = new CompositeDisposable();
        mRxSharedPreferences = rxSharedPreferences;
    }

    /**
     * Method injection is used here to safely reference {@code this} after the object is created.
     */
    @Inject
    void setupListeners() {
        mView.setPresenter(this);
    }



    @Override
    public void login() {
        mSubscriptions.clear();
        Log.i(TAG, "login: " + "fetch waiters");
        Disposable disposable = mWaitersRepository
                .getAll()
                .subscribe(
                        // onNext
                        waiters -> mView.onWaitersLoaded(waiters),

                        // onError
                        throwable -> {
                            Log.e(TAG, "login: ", throwable);
                            mView.showLoadingWaitersError();
                            },

                        //onCompleted
                        () -> mView.showLoadingIndicator(false));

        mSubscriptions.add(disposable);
        Log.i(TAG, "login: waiters fetched");
    }

    @Override
    public void performLogin(String s, List<Waiter> waiters) {
       mWaitersRepository.getWaiterFromPin(s)
        .observeOn(mScheduleProvider.ui())
        .subscribe(
                waiter -> {
                    if(waiter.getPin() != null && waiter.getPin().equals(s)){
                        Preference<String> waiterId = mRxSharedPreferences.getString(mContext.getString(R.string.key_waiter_id));
                        waiterId.set(waiter.getId());
                        mView.showPlaceOrderActivity();
                    } else {
                        mView.showLoginFailed();
                    }
                },
                throwable -> {
                    Log.e(TAG, "performLogin: ", throwable);
                    mView.showLoginFailed();
                    //mView.showWaiterLoginError();
                },
                () -> {}
        );
    }


    @Override
    public void subscribe() {
       login();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }
}
