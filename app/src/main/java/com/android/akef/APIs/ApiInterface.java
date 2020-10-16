package com.android.akef.APIs;

import com.android.akef.RetrofitModels.TournamentResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    String tournamentList = "tournament";

    @GET(tournamentList)
    Call<List<TournamentResponse>> getTournamentList();
}
