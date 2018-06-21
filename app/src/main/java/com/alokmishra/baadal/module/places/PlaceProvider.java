package com.alokmishra.baadal.module.places;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.alokmishra.baadal.R;
import com.alokmishra.baadal.module.util.Constants;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PlaceProvider {

    private static volatile PlaceProvider sInstance;

    private PlaceProvider() {
    }

    public static PlaceProvider getInstance() {
        if (sInstance == null) {
            synchronized (PlaceProvider.class) {
                if (sInstance == null) {
                    sInstance = new PlaceProvider();
                }
            }
        }
        return sInstance;
    }

    public Intent getSearchIntent(Activity activity) {
        try {
            AutocompleteFilter filter = getFilterBbyType(AutocompleteFilter.TYPE_FILTER_CITIES);
            return new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).setFilter(filter).build(activity);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
        return null;
    }

    private AutocompleteFilter getFilterBbyType(int filterType) {
        return new AutocompleteFilter.Builder().setTypeFilter(filterType).build();
    }

    public void getCurrentCity(Fragment fragment, final PlaceFetchedListener listener, final OnFailureListener failureListener) {

        PlaceDetectionClient placeDetectionClient = Places.getPlaceDetectionClient(fragment.getActivity());
        if (ActivityCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            fragment.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.RequestCodes.PLACE_REQUEST_PERMISSION);
            return;
        }

        final Task<PlaceLikelihoodBufferResponse> placeResult = placeDetectionClient.getCurrentPlace(null);
        placeResult.addOnFailureListener(failureListener);
        placeResult.addOnCompleteListener(new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                try {
                    final List<Place> placesList = new ArrayList<Place>();
                    PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();
                    for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                        placesList.add(placeLikelihood.getPlace().freeze());
                    }
                    likelyPlaces.release();
                    String[] arr = placesList.get(0).getAddress().toString().split(",");
                    String city = arr[arr.length - 3];
                    listener.onPlaceFetched(city);
                } catch (Exception e) {
                    listener.onFailure();
                }

            }
        });
    }


    public boolean checkLocation(final Context context) {
        if(!isLocationEnabled(context)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.gps_not_found_title);  // GPS not found
            builder.setMessage(R.string.gps_not_found_message); // Want to enable?
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            });
            builder.create().show();
            return false;
        } else {
            return true;
        }
    }
    private boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

}
