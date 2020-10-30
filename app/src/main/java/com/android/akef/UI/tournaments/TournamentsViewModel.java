package com.android.akef.UI.tournaments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.android.akef.APIs.ApiInterface;
import com.android.akef.APIs.RetrofitClient;
import com.android.akef.Database.Repository;
import com.android.akef.Interfaces.DatabaseFetchListener;
import com.android.akef.RetrofitModels.TournamentResponse;
import com.android.akef.Tables.Tournament;
import com.android.akef.Utils.Variables;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TournamentsViewModel extends AndroidViewModel {

    Repository repository;

    public TournamentsViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
    }

    public List<Tournament> fetchTournamentsFromDB(){
       return repository.loadTournamentsFromDB();
    }

    public void loadTournamentList(DatabaseFetchListener listener){

        repository.loadTournamentList(listener);
    }
}