package com.mobitill.barandrestaurant.register;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobitill.barandrestaurant.MainApplication;
import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.data.product.models.Product;
import com.mobitill.barandrestaurant.register.adapter.RegisterAdapter;

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

    @Inject
    RegisterPresenter mRegisterPresenter;

    private RegisterContract.Presenter mPresenter;
    private Unbinder mUnbinder;



    private MyCustomAdapter myCustomAdapter;

    private RegisterAdapter mRegisterAdapter;

    private RecyclerView.LayoutManager layoutManager;

    @BindView(R.id.productsRecyclerView)
    public RecyclerView recyclerView;

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
    public void showProducts(List<Product> products) {
        if(isAdded()){
            if(mRegisterAdapter == null){
                mRegisterAdapter = new RegisterAdapter(getActivity(), products);
                recyclerView.setAdapter(mRegisterAdapter);
            } else {
                mRegisterAdapter.setItems(products);
                mRegisterAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void showNoProducts() {
        Snackbar.make(recyclerView, "Products", Snackbar.LENGTH_SHORT).show();
    }
}
