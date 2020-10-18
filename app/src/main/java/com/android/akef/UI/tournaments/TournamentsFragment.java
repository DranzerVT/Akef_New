package com.android.akef.UI.tournaments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.akef.Adapters.TournamentGamesAdapter;
import com.android.akef.Interfaces.DatabaseFetchListener;
import com.android.akef.R;
import com.android.akef.Tables.Tournament;

import java.util.List;

public class TournamentsFragment extends Fragment {

    private TournamentsViewModel mViewModel;
    RecyclerView tournamentListView;

    public static TournamentsFragment newInstance() {
        return new TournamentsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tournaments_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TournamentsViewModel.class);
        tournamentListView = view.findViewById(R.id.recyclerView);
        mViewModel.loadTournamentList(new DatabaseFetchListener() {
            @Override
            public <T> void onLoadingFinished(T o) {

                if(o!=null){
                   List<Tournament> tournamentList = (List<Tournament>)o;
                    Log.e("TournamentsFragment", "onLoadingFinished: " + tournamentList);

                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            TournamentGamesAdapter tournamentGamesAdapter = new TournamentGamesAdapter(tournamentList);
                            tournamentListView.setAdapter(tournamentGamesAdapter);
                        }
                    });

                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}