package com.android.akef.UI.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.akef.Adapters.TournamentGamesAdapter;
import com.android.akef.Adapters.TrendingGamesAdapter;
import com.android.akef.Interfaces.DatabaseFetchListener;
import com.android.akef.Interfaces.TournamentListType;
import com.android.akef.R;
import com.android.akef.Tables.Tournament;
import com.android.akef.UI.WebViewActivity;
import com.android.akef.UI.tournaments.TournamentsViewModel;
import com.android.akef.Utils.Variables;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    NestedScrollView mScrollView;
    RecyclerView gameListView,tournamentListView;
    TrendingGamesAdapter trendingGamesAdapter;
    Button btnAdminPanel;
    private Context mContext;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        mScrollView = view.findViewById(R.id.scrollView);
        gameListView = view.findViewById(R.id.trend_games_list);
        gameListView.setLayoutManager(new GridLayoutManager(mContext,
                1, GridLayoutManager.HORIZONTAL,false));
        tournamentListView = view.findViewById(R.id.recent_events_list);
        tournamentListView.setLayoutManager(new GridLayoutManager(mContext,
                1, GridLayoutManager.HORIZONTAL,false));
        btnAdminPanel = view.findViewById(R.id.btn_admin_panel);

        List<Tournament> tournamentList = mViewModel.fetchTournamentsFromDB(4);
        if(tournamentList!= null && tournamentList.size() > 0){
            TournamentGamesAdapter tournamentGamesAdapter = new TournamentGamesAdapter(tournamentList,getActivity(), TournamentListType.HOME);
            tournamentListView.setAdapter(tournamentGamesAdapter);
        }
        mViewModel.loadTournamentList(new DatabaseFetchListener() {
            @Override
            public <T> void onLoadingFinished(T o) {
                if(o!=null){
                    List<Tournament> tournamentList = mViewModel.fetchTournamentsFromDB(4);

                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            TournamentGamesAdapter tournamentGamesAdapter = new TournamentGamesAdapter(tournamentList,getActivity(),TournamentListType.HOME);
                            tournamentListView.setAdapter(tournamentGamesAdapter);
                        }
                    });

                }
            }
        });

        trendingGamesAdapter = new TrendingGamesAdapter(mContext);
        gameListView.setAdapter(trendingGamesAdapter);

        btnAdminPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://docs.google.com/document/d/1hMJhNwv0wKR4DHTV3SmY_LJFDQlZvJQMFnuEDdJLj7I/edit");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
}