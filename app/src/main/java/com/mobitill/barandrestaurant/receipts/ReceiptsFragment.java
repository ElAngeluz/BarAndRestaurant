package com.mobitill.barandrestaurant.receipts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobitill.barandrestaurant.R;
import com.mobitill.barandrestaurant.data.product.models.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReceiptsFragment extends Fragment implements ReceiptsContract.View{
    private static final String TAG = ReceiptsFragment.class.getSimpleName();

    private ReceiptsContract.Presenter presenter;
    private Unbinder unbinder;

    private ReceiptsProductAdapter receiptsProductAdapter;
    private RecyclerView.LayoutManager manager;

    @BindView(R.id.recView_receipts_products)
    private RecyclerView receiptsRecyclerView;

    public ReceiptsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.receipts_fragment, container, false);

        unbinder = ButterKnife.bind(getActivity(),view);
        manager = new GridLayoutManager(getActivity(),3);
        receiptsRecyclerView.setLayoutManager(manager);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void setPresenter(ReceiptsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProducts(List<Product> products) {
        /*
         *May still need revisiting
         */
        receiptsProductAdapter = new ReceiptsProductAdapter(products);
        receiptsRecyclerView.setAdapter(receiptsProductAdapter);

    }

    @Override
    public void checkedOut() {

    }

    @Override
    public void notCheckedOut() {

    }
}
