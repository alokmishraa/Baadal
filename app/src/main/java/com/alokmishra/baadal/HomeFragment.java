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
import android.widget.LinearLayout;

import com.alokmishra.baadal.view.model.CurrentWeatherItemData;
import com.alokmishra.baadal.view.model.ForecastItemData;
import com.alokmishra.baadal.view.model.SingleDayForecastItemData;
import com.alokmishra.baadal.view.widget.SingleDayForecastWidget;
import com.alokmishra.baadal.viewmodel.ForecastViewModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment {

    private ForecastViewModel mViewModel;
    private String mCity;
    private LinearLayout mForecastContainer;
    private LayoutInflater mInflator;

    public static final String TAG = HomeFragment.class.getSimpleName();
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = ViewModelProviders.of(this).get(ForecastViewModel.class);
        mViewModel.init();
        mInflator = LayoutInflater.from(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mForecastContainer = view.findViewById(R.id.forecast_container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initObservers();
        mViewModel.start("Noida");
    }

    private void initObservers() {
        mViewModel.getCurrentLiveData().observe(this, new Observer<CurrentWeatherItemData>() {
            @Override
            public void onChanged(@Nullable CurrentWeatherItemData currentWeatherItemData) {
                 updateCurrentUi(currentWeatherItemData);
            }
        });

        mViewModel.getForecastLiveData().observe(this, new Observer<ForecastItemData>() {
            @Override
            public void onChanged(@Nullable ForecastItemData forecastItemData) {
                updateForecastUi(forecastItemData);
            }
        });
    }

    private void updateCurrentUi(CurrentWeatherItemData currentWeatherItemData) {
        //TODO init current weather item view;
    }

    private void updateForecastUi(ForecastItemData forecastItemData) {
        for(SingleDayForecastItemData item : forecastItemData.getForecastList()) {
            SingleDayForecastWidget widget = (SingleDayForecastWidget) mInflator.inflate(R.layout.single_day_forecast, null);
            widget.setData(item);
            mForecastContainer.addView(widget);
        }

        //TODO init forecast weather item view;
    }

}
