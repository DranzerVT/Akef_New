package com.android.akef.UI.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.android.akef.Database.Repository;
import com.android.akef.Interfaces.DatabaseFetchListener;
import com.android.akef.Tables.Tournament;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    Repository repository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
    }

    public List<Tournament> fetchTournamentsFromDB(int count){
        return repository.loadTournamentsFromDB(count);
    }

    public void loadTournamentList(DatabaseFetchListener listener){

        repository.loadTournamentList(listener);
    }
}