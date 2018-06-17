package com.alokmishra.baadal;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alokmishra.baadal.view.model.CurrentWeatherItemData;
import com.alokmishra.baadal.viewmodel.CurrentCityWeatherViewModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment {

    private CurrentCityWeatherViewModel mViewModel;
    private String mCity;

    public static final String TAG = HomeFragment.class.getSimpleName();
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = ViewModelProviders.of(this).get(CurrentCityWeatherViewModel.class);
        mViewModel.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
    }

    private void updateView(CurrentWeatherItemData currentWeatherItemData) {
        //TODO init current weather item view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initObservers();

        //TODO move this to appropriate place
        mViewModel.start("Noida");

    }

    private void initObservers() {
        mViewModel.getCurrentLiveData().observe(this, new Observer<CurrentWeatherItemData>() {
            @Override
            public void onChanged(@Nullable CurrentWeatherItemData currentWeatherItemData) {
                 updateView(currentWeatherItemData);
            }
        });
    }

}
