package com.android.akef.UI.latestupdates;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.akef.R;

public class LatestUpdatesFragment extends Fragment {

    private LatestUpdatesViewModel mViewModel;

    public static LatestUpdatesFragment newInstance() {
        return new LatestUpdatesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.latest_updates_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LatestUpdatesViewModel.class);
        // TODO: Use the ViewModel
    }

}