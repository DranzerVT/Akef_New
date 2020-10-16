
package com.android.akef.RetrofitModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TournamentMaps {

    @SerializedName("22")
    @Expose
    private String _22;
    @SerializedName("19")
    @Expose
    private String _19;
    @SerializedName("20")
    @Expose
    private String _20;
    @SerializedName("21")
    @Expose
    private String _21;

    public String get22() {
        return _22;
    }

    public void set22(String _22) {
        this._22 = _22;
    }

    public String get19() {
        return _19;
    }

    public void set19(String _19) {
        this._19 = _19;
    }

    public String get20() {
        return _20;
    }

    public void set20(String _20) {
        this._20 = _20;
    }

    public String get21() {
        return _21;
    }

    public void set21(String _21) {
        this._21 = _21;
    }

}
