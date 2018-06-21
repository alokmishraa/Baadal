package com.alokmishra.baadal.module.places;

import android.app.Activity;
import android.content.Intent;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

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

}
