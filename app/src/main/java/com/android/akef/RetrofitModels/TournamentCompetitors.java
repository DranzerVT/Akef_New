
package com.android.akef.RetrofitModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TournamentCompetitors {

    @SerializedName("2032")
    @Expose
    private String _2032;

    public String get2032() {
        return _2032;
    }

    public void set2032(String _2032) {
        this._2032 = _2032;
    }

}
