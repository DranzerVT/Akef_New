package com.android.akef.Utils;

// Need the READ_EXTERNAL_STORAGE permission if accessing video files that your
// app didn't create.

import android.net.Uri;

// Container for information about each video.
public class VideoModel {
    private final Uri uri;
    private final String name;
    private final int duration;
    private final int size;

    public VideoModel(Uri uri, String name, int duration, int size) {
        this.uri = uri;
        this.name = name;
        this.duration = duration;
        this.size = size;
    }


    public Uri getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getSize() {
        return size;
    }
}