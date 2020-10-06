package com.android.akef.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.akef.R;

import java.util.ArrayList;
import java.util.List;

public class TrendingGamesAdapter extends RecyclerView.Adapter<TrendingGamesAdapter.ViewHolder> {

    private List<Drawable> mGamesList;


    public TrendingGamesAdapter(Context context) {

        mGamesList = new ArrayList<>();
        mGamesList.add(context.getDrawable(R.drawable.pubg_image));
        mGamesList.add(context.getDrawable(R.drawable.gta_img));
        mGamesList.add(context.getDrawable(R.drawable.tarkov_img));
        mGamesList.add(context.getDrawable(R.drawable.fifa_img));
        mGamesList.add(context.getDrawable(R.drawable.eurotruck_img));
        mGamesList.add(context.getDrawable(R.drawable.pes_img));
        mGamesList.add(context.getDrawable(R.drawable.warzone_img));
        mGamesList.add(context.getDrawable(R.drawable.watchdogs_img));
        mGamesList.add(context.getDrawable(R.drawable.pubg2_img));


    }

    @NonNull
    @Override
    public TrendingGamesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //This is what adds the code we've written in here to our target view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.games_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingGamesAdapter.ViewHolder holder, int position) {


        //Here we use the information in the list we created to define the views
        Drawable gameIcon = mGamesList.get(position);
        ImageView imageView = holder.img;
        imageView.setImageDrawable(gameIcon);

    }

    @Override
    public int getItemCount() {
        return mGamesList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;


        //This is the subclass ViewHolder which simply
        //'holds the views' for us to show on each row
        public ViewHolder(View itemView) {
            super(itemView);

            //Finds the views from our row.xml
            img = (ImageView) itemView.findViewById(R.id.game_img);
        }

    }



}