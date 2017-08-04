package com.mobitill.barandrestaurant.auth;

import android.content.Context;
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
import io.reactivex.functions.Consumer;

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
                  @NonNull RxSharedPreferences rxSharedPreferences) {
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
        Disposable disposable = mWaitersRepository
                .getAll()
                .subscribe(
                        // onNext
                        new Consumer<List<Waiter>>() {
                            @Override
                            public void accept(List<Waiter> waiters) throws Exception {
                                Log.d(TAG, "size: " + waiters.size());
                                for (Waiter waiter:waiters) {
                                    Log.d(TAG, "Names: " + waiter.getName());
                                }
                                mView.onWaitersLoaded(waiters);
                            }
                        },

                        // onError
                        throwable -> {
                            mView.showLoadingWaitersError();
                        },

                        //onCompleted
                        () -> mView.showLoadingIndicator(false));

        mSubscriptions.add(disposable);
    }

    @Override
    public void performLogin(String phone, String password, List<Waiter> waiters) {
        mWaitersRepository.getWaiterFromPhoneAndPassword(phone, password)
                .observeOn(mScheduleProvider.ui())
                .subscribe(
                        new Consumer<Waiter>() {
                            @Override
                            public void accept(Waiter waiter) throws Exception {
                                if (waiter.getPhone() != null && waiter.getPassword() != null
                                        && waiter.getPhone().equals(phone) && waiter.getPassword().equals(password)) {
                                    Preference<String> waiterId = mRxSharedPreferences.getString(mContext.getString(R.string.key_waiter_id));
                                    waiterId.set(waiter.getId());

                                    mView.showPlaceOrderActivity(waiter.getName());
                                }  else {
                                    mView.showLoginFailed();
                                }
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                                mView.invalidCredentials();
                                return;
//                                mView.showWaiterLoginError();
                            }
                        },
                        () -> {

                        }
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
