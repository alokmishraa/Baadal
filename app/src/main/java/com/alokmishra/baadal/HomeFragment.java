package com.alokmishra.baadal;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alokmishra.baadal.module.places.PlaceProvider;
import com.alokmishra.baadal.module.util.Constants;
import com.alokmishra.baadal.view.model.CurrentWeatherItemData;
import com.alokmishra.baadal.view.model.ForecastItemData;
import com.alokmishra.baadal.view.model.SingleDayForecastItemData;
import com.alokmishra.baadal.view.widget.CurrentDayWeatherWidget;
import com.alokmishra.baadal.view.widget.SingleDayForecastWidget;
import com.alokmishra.baadal.viewmodel.ForecastViewModel;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment {

    private ForecastViewModel mViewModel;
    private String mCity;
    private LinearLayout mForecastContainer;
    private LayoutInflater mInflator;
    private CurrentDayWeatherWidget mCurrentDayWeatherWidget;
    private static final int NEXT_FORECAST_DAYS = 7; // Do not exceed more then 9

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
        setHasOptionsMenu(true);
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
        mCurrentDayWeatherWidget = view.findViewById(R.id.current_day_widget);
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
        mCurrentDayWeatherWidget.setData(currentWeatherItemData);
    }

    private void updateForecastUi(ForecastItemData forecastItemData) {
        mForecastContainer.removeAllViews();
        for (int i = 1; i <= NEXT_FORECAST_DAYS; i++) {
            SingleDayForecastItemData item = forecastItemData.getForecastList().get(i);
            SingleDayForecastWidget widget = (SingleDayForecastWidget) mInflator.inflate(R.layout.single_day_forecast, null);
            float alpha = ((float) i) / (NEXT_FORECAST_DAYS + 1);
            Pair<SingleDayForecastItemData, Float> pair = new Pair<>(item, alpha);
            widget.setData(pair);
            mForecastContainer.addView(widget);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.RequestCodes.PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                mViewModel.start(place.getName().toString());
            } else {
                Toast.makeText(getActivity(), "Error in search", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                startSearch();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void startSearch() {
        Intent intent = PlaceProvider.getInstance().getSearchIntent(getActivity());
        startActivityForResult(intent, Constants.RequestCodes.PLACE_AUTOCOMPLETE_REQUEST_CODE);
    }
}
