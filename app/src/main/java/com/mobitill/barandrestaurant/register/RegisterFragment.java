package com.mobitill.barandrestaurant.register;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobitill.barandrestaurant.MainApplication;
import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.data.order.model.Order;
import com.mobitill.barandrestaurant.data.orderItem.model.OrderItem;
import com.mobitill.barandrestaurant.data.product.models.Product;
import com.mobitill.barandrestaurant.register.adapter.AdapterCallback;
import com.mobitill.barandrestaurant.register.adapter.RegisterAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements RegisterContract.View, AdapterCallback{

    private static final String TAG = RegisterFragment.class.getSimpleName();
    private static final String ARG_PRODUCT_LIST = "product_list";

    private static final Comparator<Product> PRODUCT_COMPARATOR = (o1, o2) -> o1.getName().compareTo(o2.getName());

    @Inject
    RegisterPresenter mRegisterPresenter;

    private RegisterContract.Presenter mPresenter;
    private Unbinder mUnbinder;

    @BindView(R.id.productsRecyclerView) public RecyclerView recyclerView;
    @BindView(R.id.ticketLinearLayout) public LinearLayout mTicketLinearLayout;

    private RegisterAdapter mRegisterAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Product> mProducts = new ArrayList<>();


    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance() {

        Bundle args = new Bundle();

        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if(savedInstanceState!=null){
            mProducts = savedInstanceState.getParcelableArrayList(ARG_PRODUCT_LIST);
        }

        DaggerRegisterComponent.builder()
                .registerPresenterModule(new RegisterPresenterModule(this, getActivity()))
                .baseComponent(((MainApplication) getActivity().getApplication()).mBaseComponent())
                .build()
                .inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        layoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(ARG_PRODUCT_LIST, (ArrayList<? extends Parcelable>) mProducts);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_register, menu);

        MenuItem searchItem =
                menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: " + query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i(TAG, "onQueryTextChange: " + newText);
                final List<Product> filteredProductList = filter(mProducts, newText);
                mRegisterAdapter.replaceAll(filteredProductList);
                recyclerView.scrollToPosition(0);
                return true;
            }
        });

        searchView.setOnSearchClickListener(v -> {

        });
    }

    private List<Product> filter(List<Product> products, String newText) {
        final String lowerCaseQuery = newText.toLowerCase();
        final List<Product> filteredProductList = new ArrayList<>();

        for(Product product: products){
            final String text = product.getName().toLowerCase();
            if(text.contains(lowerCaseQuery)){
                filteredProductList.add(product);
            }
        }
        return filteredProductList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProducts(List<Product> products) {
        mProducts = products;
        if(isAdded()){
            if(mRegisterAdapter == null){
                mRegisterAdapter = new RegisterAdapter(getActivity(), PRODUCT_COMPARATOR, this);
                mRegisterAdapter.add(products);
                recyclerView.setAdapter(mRegisterAdapter);
            } else {
                mRegisterAdapter.add(products);
               // mRegisterAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void showNoProducts() {
        if(isAdded()){
            Snackbar.make(recyclerView, "Products fetch failed", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showOrderItemCreated(Order order) {
        if(order != null){
            mPresenter.getOrderItems(order);
        }

        Toast.makeText(getActivity(), order.getEntryId() + " created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showOrderItemsOnTicket(List<OrderItem> orderItems) {

        mTicketLinearLayout.removeAllViews();
        Map<String, Stack<OrderItem>> orderItemMap = mPresenter.aggregateOrderItems(orderItems);

        // display the items
        for(HashMap.Entry<String, Stack<OrderItem>> entry : orderItemMap.entrySet()){

            String productName = entry.getValue().peek().getProductName();
            String quantity = String.valueOf(entry.getValue().size());

            OrderItem orderItem = entry.getValue().peek();

            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.order_item, mTicketLinearLayout, false);
            TextView mProductNameTextView = (TextView) view.findViewById(R.id.product_name);
            mProductNameTextView.setText(productName);
            TextView mQuantityTextView = (TextView) view.findViewById(R.id.quantity);
            mQuantityTextView.setText("x" + quantity);

            ImageButton addButton = (ImageButton) view.findViewById(R.id.button_add);
            addButton.setOnClickListener(v -> mPresenter.addOrderItem(orderItem));

            ImageButton removeButton = (ImageButton) view.findViewById(R.id.button_remove);
            removeButton.setOnClickListener(v -> mPresenter.removeOrderItem(entry.getValue().pop()));


            Button requestButton = (Button) view.findViewById(R.id.button_request);
            requestButton.setOnClickListener(v -> {
                mPresenter.sendOrderRequest();
            });

            mTicketLinearLayout.addView(view);

        }
    }

    @Override
    public void addToOrder(Product product) {
        mPresenter.createOrder(product);
    }
}
