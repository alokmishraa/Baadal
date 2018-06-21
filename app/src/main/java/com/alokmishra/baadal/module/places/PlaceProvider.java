package com.alokmishra.baadal.module.places;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.alokmishra.baadal.module.util.Constants;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
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

    public void getCurrentCity(Activity context, final PlaceFetchedListener listener) {
        PlaceDetectionClient placeDetectionClient = Places.getPlaceDetectionClient(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.RequestCodes.PLACE_REQUEST_PERMISSION);

            return;
        }
        Task<PlaceLikelihoodBufferResponse> placeResult = placeDetectionClient.getCurrentPlace(null);
        placeResult.addOnCompleteListener(new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                final List<Place> placesList = new ArrayList<Place>();
                PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();
                for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                    placesList.add(placeLikelihood.getPlace().freeze());
                }
                likelyPlaces.release();
                String[] arr = placesList.get(0).getAddress().toString().split(",");
                String city = arr[arr.length - 3];
                listener.onPlaceFetched(city);
            }
        });

    }

}
