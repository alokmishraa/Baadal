
package com.alokmishra.baadal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Query {

    @SerializedName("results")
    @Expose
    private Results results;
    public Results getResults() {
        return results;
    }

}
