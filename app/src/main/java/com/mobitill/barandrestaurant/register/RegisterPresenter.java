package com.mobitill.barandrestaurant.register;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.f2prateek.rx.preferences2.Preference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.data.order.OrderRepository;
import com.mobitill.barandrestaurant.data.order.model.Order;
import com.mobitill.barandrestaurant.data.orderItem.OrderItemRepository;
import com.mobitill.barandrestaurant.data.orderItem.model.OrderItem;
import com.mobitill.barandrestaurant.data.product.ProductRepository;
import com.mobitill.barandrestaurant.data.product.models.Product;
import com.mobitill.barandrestaurant.data.waiter.WaitersRepository;
import com.mobitill.barandrestaurant.jobs.OrderRequestJob;
import com.mobitill.barandrestaurant.utils.schedulers.BaseScheduleProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by james on 5/10/2017.
 */

public class RegisterPresenter implements RegisterContract.Presenter {

    private static final String TAG = RegisterPresenter.class.getSimpleName();

    @NonNull
    private final RegisterContract.View mView;

    @NonNull
    private final ProductRepository mProductRepository;

    @NonNull
    private final OrderRepository mOrderRepository;

    @NonNull
    private final Context mContext;

    @NonNull
    private final WaitersRepository mWaitersRepository;

    @NonNull
    private final BaseScheduleProvider mScheduleProvider;

    @NonNull
    private final OrderItemRepository mOrderItemRepository;

    @NonNull
    private final RxSharedPreferences mRxSharedPreferences;

    @NonNull
    private CompositeDisposable mDisposable;

    private Order mOrder;

    @Inject
    public RegisterPresenter(@NonNull RegisterContract.View view,
                             @NonNull ProductRepository productRepository,
                             @NonNull OrderRepository orderRepository,
                             @NonNull WaitersRepository waitersRepository,
                             @NonNull OrderItemRepository orderItemRepository,
                             @NonNull BaseScheduleProvider scheduleProvider,
                             @NonNull RxSharedPreferences rxSharedPreferences,
                             @NonNull Context context) {
        mView = checkNotNull(view);
        mProductRepository = productRepository;
        mOrderRepository = orderRepository;
        mWaitersRepository = waitersRepository;
        mOrderItemRepository = orderItemRepository;
        mScheduleProvider = checkNotNull(scheduleProvider, "schedule provider should not be null");
        mRxSharedPreferences = rxSharedPreferences;
        mContext = context;
        mDisposable = new CompositeDisposable();
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
        getProducts();
    }

    @Override
    public void unsubscribe() {
        mOrder = null;
        mDisposable.clear();
    }

    @Override
    public void getProducts() {
        mDisposable.clear();
        Disposable disposable = mProductRepository
                .getAll()
                .subscribe(
                        //onNext
                        products -> {
                            if (products != null && !products.isEmpty()) {
                                mView.showProducts(products);
                            } else {
                                mView.showNoProducts();
                            }

                        },
                        throwable -> {
                            Log.e(TAG, "getProducts: ", throwable);
                            mView.showNoProducts();
                        },
                        () -> {
                        }

                );
        mDisposable.add(disposable);
    }

    @Override
    public void createOrder(Product product) {
        OrderItem orderItem;
        if (mOrder == null) {
            Preference<String> waiterIdPreference = mRxSharedPreferences.getString(mContext.getString(R.string.key_waiter_id));
            String waiterId = waiterIdPreference.get();
            mOrder = new Order();
            mOrder.setWaiterId(waiterId);
            mOrder.setCheckedOut(0);
            mOrder.setSynced(0);
            mOrderRepository.save(mOrder);
            orderItem = new OrderItem(product.getId(), mOrder.getEntryId(), "counter", 0, 0, product.getName());
        } else {
            orderItem = new OrderItem(product.getId(), mOrder.getEntryId(), "counter", 0, 0, product.getName());
        }

        if (mOrderItemRepository.save(orderItem) != null) {
            mView.showOrderItemCreated(mOrder);
        }
    }

    @Override
    public void getOrderItems(Order order) {
        Disposable disposable = mOrderItemRepository
                .getOrderItemWithOrderId(order.getEntryId())
                .subscribe(
                        orderItems -> {
                            mView.showOrderItemsOnTicket(orderItems);
                        },
                        throwable -> Log.e(TAG, "getOrderItems: ", throwable),
                        () -> {
                        });
        mDisposable.add(disposable);
    }

    @Override
    public Map<String, Stack<OrderItem>> aggregateOrderItems(List<OrderItem> orderItems) {

        Map<String, Stack<OrderItem>> orderItemMap = new HashMap<>();
        // aggregate items into a stack
        for(OrderItem orderItem: orderItems){

            if(orderItemMap.containsKey(orderItem.getProductId())){
                orderItemMap.get(orderItem.getProductId()).push(orderItem);
            } else {
                Stack<OrderItem> orderItemStack = new Stack<>();
                orderItemStack.push(orderItem);
                orderItemMap.put(orderItem.getProductId(), orderItemStack);
            }

        }

        return orderItemMap;
    }

    @Override
    public void addOrderItem(OrderItem orderItem) {
        OrderItem orderItemToSave = new OrderItem(orderItem.getProductId(), orderItem.getOrderId(), orderItem.getCounter(), 0, 0, orderItem.getProductName());
        if (mOrderItemRepository.save(orderItemToSave) != null) {
            mView.showOrderItemCreated(mOrder);
        }
    }

    @Override
    public void removeOrderItem(OrderItem orderItemToDelete) {
        if(mOrderItemRepository.delete(orderItemToDelete.getId()) > 0){
            mView.showOrderItemCreated(mOrder);
        }
    }

    @Override
    public void sendOrderRequest() {
        if(mOrder != null){
            //mOrderItemRepository.orderRequest(mOrder, );
        }
    }

    @Override
    public void completeOrderRequest() {
        if(mOrder!=null){
            mOrder = null;
            mView.showOrderRequestComplete();
            OrderRequestJob.scheduleJob();
        }
    }


}
