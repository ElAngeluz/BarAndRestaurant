package com.mobitill.barandrestaurant.auth;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobitill.barandrestaurant.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuthDialogFragment extends Fragment {


    public AuthDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.auth_dialog_fragment, container, false);
    }

}