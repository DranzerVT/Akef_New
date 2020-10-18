package com.android.akef.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.akef.R;
import com.android.akef.Tables.Tournament;

import java.util.List;

public class TournamentGamesAdapter extends RecyclerView.Adapter<TournamentGamesAdapter.ViewHolder> {

    List<Tournament> tournamentList;

    public TournamentGamesAdapter(List<Tournament> tournamentList) {
        this.tournamentList = tournamentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //This is what adds the code we've written in here to our target view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.tournament_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.title.setText(tournamentList.get(position).getTitle());
        holder.date.setText(tournamentList.get(position).getStartDate());
        holder.gameName.setText(tournamentList.get(position).getTournamentGame());
        holder.platform.setText(tournamentList.get(position).getTournamentPlatform());
        holder.maxParticipants.setText(tournamentList.get(position).getTournamentMaxParticipants());
    }

    @Override
    public int getItemCount() {
        return tournamentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView logo;
        public TextView title,date,gameName,platform,maxParticipants;


        //This is the subclass ViewHolder which simply
        //'holds the views' for us to show on each row
        public ViewHolder(View itemView) {
            super(itemView);

            //Finds the views from our row.xml
            logo = (ImageView) itemView.findViewById(R.id.tournamentLogo);
            title = (TextView) itemView.findViewById(R.id.tournamentTitle);
            date = (TextView) itemView.findViewById(R.id.tournamentDate);
            gameName = (TextView) itemView.findViewById(R.id.tournamentGameName);
            platform = (TextView) itemView.findViewById(R.id.tournamentConsole);
            maxParticipants = (TextView) itemView.findViewById(R.id.tournamentTeams);
        }

    }
}
