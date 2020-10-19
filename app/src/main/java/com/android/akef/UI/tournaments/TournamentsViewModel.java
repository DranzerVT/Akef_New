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

    ApiInterface apiInterface;
    Repository repository;

    public TournamentsViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
    }

    public List<Tournament> fetchTournamentsFromDB(){
       return repository.loadTournamentsFromDB();
    }

    public void loadTournamentList(DatabaseFetchListener listener){

        apiInterface = RetrofitClient.getClient(Variables.BASE_URL).create(ApiInterface.class);
        apiInterface.getTournamentList().enqueue(new Callback<List<TournamentResponse>>() {
            @Override
            public void onResponse(Call<List<TournamentResponse>> call, Response<List<TournamentResponse>> response) {

                List<TournamentResponse> responseList = response.body();
                List<Tournament> tournamentList = new ArrayList<>();
                Variables.showResponseData(new Gson().toJson(responseList),"GetTournamentList");
                for (int i = 0; i < responseList.size(); i++) {
                    long tournamentID = responseList.get(i).getId();
                    String title = responseList.get(i).getTitle().getRendered();
                    String content = responseList.get(i).getContent().getRendered();
                    String date = responseList.get(i).getDateGmt();
                    String server = responseList.get(i).getTournamentServer();
                    String image = "";
                    int author = responseList.get(i).getAuthor();
                    String gameMode = responseList.get(i).getGameModes();
                    String timezone = responseList.get(i).getTournamentTimezone();
                    String startDate = responseList.get(i).getTournamentStarts();
                    String contestants = responseList.get(i).getTournamentContestants();
                    String prizes = "";
                    String tournamentGamesFormat = responseList.get(i).getTournamentGamesFormat();
                    String tournamentGameFrequency = responseList.get(i).getTournamentGameFrequency();
                    String tournamentFrequency = responseList.get(i).getTournamentFrequency();
                    String tournamentGame = responseList.get(i).getTournamentGame();
                    String tournamentMaxParticipants = responseList.get(i).getTournamentMaxParticipants();
                    String tournamentState = responseList.get(i).getTournamentState();
                    String premium = responseList.get(i).getPremium();
                    String tournamentPlatform = responseList.get(i).getTournamentPlatform();
                    String link = responseList.get(i).getLink();

                    Tournament tournament = new Tournament(tournamentID,title,content,date,server,image,
                            author,gameMode,timezone,startDate,contestants,tournamentGamesFormat,tournamentGameFrequency,
                            prizes,tournamentFrequency,tournamentGame,tournamentMaxParticipants,tournamentState,premium,
                            tournamentPlatform,link);
                    tournamentList.add(tournament);

                }

                repository.insertTournamentList(tournamentList,listener);
            }

            @Override
            public void onFailure(Call<List<TournamentResponse>> call, Throwable t) {
                t.printStackTrace();
                Variables.showErrorData(t.getMessage(), "GetTournamentList");
            }
        });
    }
}