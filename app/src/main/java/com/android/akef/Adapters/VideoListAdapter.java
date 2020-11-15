package com.android.akef.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.akef.Interfaces.VideoClickedListener;
import com.android.akef.R;
import com.android.akef.UI.ReelUploadActivity;
import com.android.akef.Utils.VideoModel;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder>{

    List<VideoModel> videoList;
    Context context;
    VideoClickedListener videoClickedListener;

    public VideoListAdapter(List<VideoModel> videoList, Context context, VideoClickedListener videoClickedListener) {
        this.videoList = videoList;
        this.context = context;
        this.videoClickedListener = videoClickedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //This is what adds the code we've written in here to our target view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.video_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Load thumbnail of a specific media item.
        Bitmap thumbnail = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                thumbnail =
                       context.getContentResolver().loadThumbnail(
                               videoList.get(position).getUri(), new Size(250, 250), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                thumbnail = ThumbnailUtils.createVideoThumbnail(ReelUploadActivity.
                        getFilePathFromUri(context,videoList.get(position).getUri()).getPath(),
                        MediaStore.Video.Thumbnails.MINI_KIND);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if(thumbnail != null) {
            Glide.with(context)
                    .load(thumbnail)
                    .override(250, 250) // resizes the image to these dimensions (in pixel)
                    .centerCrop() // this cropping technique scales the image so that it fills the requested bounds and then crops the extra.
                    .into(holder.img);
        }

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoClickedListener.onVideoClicked(videoList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;


        //This is the subclass ViewHolder which simply
        //'holds the views' for us to show on each row
        public ViewHolder(View itemView) {
            super(itemView);

            //Finds the views from our row.xml
            img = (ImageView) itemView.findViewById(R.id.video_img);
        }

    }
}
