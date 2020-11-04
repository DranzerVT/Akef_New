package com.android.akef.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.akef.Interfaces.TournamentListType;
import com.android.akef.R;
import com.android.akef.Tables.Tournament;
import com.android.akef.UI.WebViewActivity;
import com.android.akef.Utils.Variables;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class TournamentGamesAdapter extends RecyclerView.Adapter<TournamentGamesAdapter.ViewHolder> {

    List<Tournament> tournamentList;
    Context mContext;
    int listType;

    public TournamentGamesAdapter(List<Tournament> tournamentList,Context mContext,int listType) {
        this.tournamentList = tournamentList;
        this.mContext = mContext;
        this.listType = listType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //This is what adds the code we've written in here to our target view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;
        if(listType == TournamentListType.TOURNAMENT) {
            view = inflater.inflate(R.layout.tournament_item_layout, parent, false);
        }else if(listType == TournamentListType.HOME){
            view = inflater.inflate(R.layout.tournament_item_layout_home, parent, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.title.setText(tournamentList.get(position).getTitle());
        holder.date.setText(tournamentList.get(position).getStartDate());
        holder.gameName.setText(tournamentList.get(position).getTournamentGame());
        if(listType ==  TournamentListType.HOME){
            holder.title.setSelected(true);
        }
        holder.platform.setText(tournamentList.get(position).getTournamentPlatform());
        holder.maxParticipants.setText(tournamentList.get(position).getTournamentMaxParticipants());
        holder.tournamentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra(Variables.WEBVIEW_URL_KEY,tournamentList.get(position).getLink());
                intent.putExtra(Variables.WEBVIEW_JAVASCRIPT_KEY,Variables.JS_KEY);
                intent.putExtra(Variables.REQUIRES_REFRESH,true);
                intent.putExtra(Variables.WEBVIEW_TITLE,tournamentList.get(position).getTitle());
                intent.putExtra(Variables.PROMPT_LOGIN,true);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tournamentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView logo;
        public TextView title,date,gameName,platform,maxParticipants;
        public MaterialCardView tournamentCard;


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
            tournamentCard = (MaterialCardView) itemView.findViewById(R.id.tournament_card);
        }

    }
}
