package com.android.akef.Database;

import android.app.Application;

import com.android.akef.Interfaces.DatabaseFetchListener;
import com.android.akef.Tables.Tournament;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Repository {

    AppDatabase appDatabase;
    public static Repository sInstance;

    private Repository(Application application) {
        appDatabase = AppDatabase.getAppDatabase(application);
    }

    public static Repository getInstance(Application application) {

        if (sInstance == null) {
            sInstance = new Repository(application);
        }
        return sInstance;
    }

    public void insertTournamentList(List<Tournament> tournamentList, DatabaseFetchListener listener){

        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            appDatabase.tournamentDao().insertAll(tournamentList);
            listener.onLoadingFinished(tournamentList);
        });
    }
}