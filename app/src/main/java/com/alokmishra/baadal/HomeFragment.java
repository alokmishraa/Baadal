package com.alokmishra.baadal;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alokmishra.baadal.module.places.PlaceFetchedListener;
import com.alokmishra.baadal.module.places.PlaceProvider;
import com.alokmishra.baadal.module.util.CommonUtils;
import com.alokmishra.baadal.module.util.Constants;
import com.alokmishra.baadal.module.util.OnNetworkFailureListener;
import com.alokmishra.baadal.view.model.CurrentWeatherItemData;
import com.alokmishra.baadal.view.model.ForecastItemData;
import com.alokmishra.baadal.view.model.SingleDayForecastItemData;
import com.alokmishra.baadal.view.widget.CurrentDayWeatherWidget;
import com.alokmishra.baadal.view.widget.SingleDayForecastWidget;
import com.alokmishra.baadal.viewmodel.ForecastViewModel;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.tasks.OnFailureListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment {

    private ForecastViewModel mViewModel;
    private LinearLayout mForecastContainer;
    private LayoutInflater mInflator;
    private CurrentDayWeatherWidget mCurrentDayWeatherWidget;
    private static final int NEXT_FORECAST_DAYS = 7; // Do not exceed more then 9

    public static final String TAG = HomeFragment.class.getSimpleName();
    private ProgressBar progreeBar;

    public static HomeFragment newInstance() {
        return new HomeFragment();
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
        progreeBar = view.findViewById(R.id.progressBar);
        mForecastContainer = view.findViewById(R.id.forecast_container);
        mCurrentDayWeatherWidget = view.findViewById(R.id.current_day_widget);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initObservers();
        String city = CommonUtils.getSavedCity();
        if (!TextUtils.isEmpty(city)) {
            startWeatherFetch(city);
        } else {
            askForInitialLocation();
        }
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
        progreeBar.setVisibility(View.GONE);
        mCurrentDayWeatherWidget.setData(currentWeatherItemData);
    }

    private void updateForecastUi(ForecastItemData forecastItemData) {
        progreeBar.setVisibility(View.GONE);
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
                startWeatherFetch(place.getName().toString());
            } else {
                Toast.makeText(getActivity(), R.string.search_error, Toast.LENGTH_SHORT).show();
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
            case R.id.action_locate:
                if (PlaceProvider.getInstance().checkLocation(getActivity())) {
                    getCurrentCity();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void startSearch() {
        Intent intent = PlaceProvider.getInstance().getSearchIntent(getActivity());
        startActivityForResult(intent, Constants.RequestCodes.PLACE_AUTOCOMPLETE_REQUEST_CODE);
    }

    private void showErrorDialog(final String city) {
        progreeBar.setVisibility(View.GONE);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.network_error)
                .setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startWeatherFetch(city);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity().finish();
                    }
                });
        builder.setCancelable(false);
        builder.create();
        builder.show();
    }

    private void showLocationErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.permission_error)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.create();
        builder.show();
    }

    private void askForInitialLocation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.no_cached_city)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.create();
        builder.show();
    }

    private OnNetworkFailureListener mNetworkErrorListener = new OnNetworkFailureListener() {
        @Override
        public void onFailure(String city) {
            showErrorDialog(city);
        }
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constants.RequestCodes.PLACE_REQUEST_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentCity();
                } else {
                    progreeBar.setVisibility(View.GONE);
                    showLocationErrorDialog();
                }
        }
    }

    private PlaceFetchedListener mPlaceFetchedListener = new PlaceFetchedListener() {
        @Override
        public void onPlaceFetched(String city) {
            startWeatherFetch(city);
        }
    };

    private OnFailureListener mOnFailureListener = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            progreeBar.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "Error while fetching current city", Toast.LENGTH_SHORT).show();
        }
    };

    private void startWeatherFetch(String city) {
        progreeBar.setVisibility(View.VISIBLE);
        mViewModel.start(city, mNetworkErrorListener);
    }

    private void getCurrentCity() {
        progreeBar.setVisibility(View.VISIBLE);
        PlaceProvider.getInstance().getCurrentCity(this, mPlaceFetchedListener, mOnFailureListener);
    }
}
