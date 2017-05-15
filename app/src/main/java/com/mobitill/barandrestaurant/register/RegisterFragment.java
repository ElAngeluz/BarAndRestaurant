package com.mobitill.barandrestaurant.register;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobitill.barandrestaurant.R;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements RegisterContract.View{

    private static final String TAG = RegisterFragment.class.getSimpleName();

    private RegisterContract.Presenter mPresenter;

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
                .build()
                .inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);


        return view;
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

}
