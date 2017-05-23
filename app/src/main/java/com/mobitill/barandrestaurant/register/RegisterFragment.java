package com.mobitill.barandrestaurant.register;


import android.os.Bundle;
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

import com.mobitill.barandrestaurant.MainApplication;
import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.data.product.models.Product;
import com.mobitill.barandrestaurant.register.adapter.RegisterAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements RegisterContract.View{

    private static final String TAG = RegisterFragment.class.getSimpleName();

    private static final Comparator<Product> PRODUCT_COMPARATOR = (o1, o2) -> o1.getName().compareTo(o2.getName());

    @Inject
    RegisterPresenter mRegisterPresenter;

    private RegisterContract.Presenter mPresenter;
    private Unbinder mUnbinder;

    @BindView(R.id.productsRecyclerView) public RecyclerView recyclerView;

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

        DaggerRegisterComponent.builder()
                .registerPresenterModule(new RegisterPresenterModule(this, getActivity()))
                .productRepositoryComponent(((MainApplication)getActivity().getApplication()).getProductRepositoryComponent())
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
                mRegisterAdapter = new RegisterAdapter(getActivity(), PRODUCT_COMPARATOR);
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
        Snackbar.make(recyclerView, "Products", Snackbar.LENGTH_SHORT).show();
    }
}
