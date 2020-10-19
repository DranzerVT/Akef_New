package com.android.akef.Tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    @ColumnInfo(name = "UserID")
    private long userID;
    @ColumnInfo(name = "Username")
    private String userName;
    @ColumnInfo(name = "Profile")
    private String profile;
    @ColumnInfo(name = "Level")
    private String level;
    @ColumnInfo(name = "Link")
    private String link;

    public User(long userID, String userName, String profile, String level, String link) {
        this.userID = userID;
        this.userName = userName;
        this.profile = profile;
        this.level = level;
        this.link = link;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
