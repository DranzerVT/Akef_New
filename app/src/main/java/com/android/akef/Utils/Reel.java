package com.android.akef.Utils;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Reel {

    long userID;
    String downloadUrl;
    @ServerTimestamp
    Date date;
    String title;
    String description;
    List<Likes> likes;
    List<Reports> reports;
    List<Comments> comments;

    public Reel(long userID, String downloadUrl, Date date,String title,String description) {
        this.userID = userID;
        this.downloadUrl = downloadUrl;
        this.date = date;
        this.likes = new ArrayList<>();
        this.reports = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.title = title;
        this.description = description;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Likes> getLikes() {
        return likes;
    }

    public void setLikes(List<Likes> likes) {
        this.likes = likes;
    }

    public List<Reports> getReports() {
        return reports;
    }

    public void setReports(List<Reports> reports) {
        this.reports = reports;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
