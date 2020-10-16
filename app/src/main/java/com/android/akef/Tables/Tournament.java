package com.android.akef.Tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Tournament {

    @PrimaryKey
    @ColumnInfo(name = "TournamentID")
    private long tournamentID;
    @ColumnInfo(name = "Title")
    private String title;
    @ColumnInfo(name = "Content")
    private String content;
    @ColumnInfo(name = "Date")
    private String date;
    @ColumnInfo(name = "Server")
    private String server;
    @ColumnInfo(name = "Image")
    private String image;
    @ColumnInfo(name = "Author")
    private int author;
    @ColumnInfo(name = "GameMode")
    private String gameMode;
    @ColumnInfo(name = "TimeZone")
    private String timeZone;
    @ColumnInfo(name = "StartDate")
    private String startDate;
    @ColumnInfo(name = "Contestants")
    private String contestants;
    @ColumnInfo(name = "TournamentGamesFormat")
    private String tournamentGamesFormat;
    @ColumnInfo(name = "TournamentGamesFrequency")
    private String tournamentGamesFrequency;
    @ColumnInfo(name = "TournamentPrizes")
    private String tournamentPrizes;
    @ColumnInfo(name = "TournamentFrequency")
    private String tournamentFrequency;
    @ColumnInfo(name = "TournamentGame")
    private String tournamentGame;
    @ColumnInfo(name = "TournamentMaxParticipants")
    private String tournamentMaxParticipants;
    @ColumnInfo(name = "TournamentState")
    private String tournamentState;
    @ColumnInfo(name = "Premium")
    private String premium;
    @ColumnInfo(name = "TournamentPlatform")
    private String tournamentPlatform;
    @ColumnInfo(name = "Link")
    private String link;


    public Tournament(long tournamentID, String title, String content, String date, String server, String image,
                      int author, String gameMode, String timeZone, String startDate, String contestants, String tournamentGamesFormat,
                      String tournamentGamesFrequency, String tournamentPrizes, String tournamentFrequency, String tournamentGame,
                      String tournamentMaxParticipants, String tournamentState, String premium, String tournamentPlatform,String link) {
        this.tournamentID = tournamentID;
        this.title = title;
        this.content = content;
        this.date = date;
        this.server = server;
        this.image = image;
        this.author = author;
        this.gameMode = gameMode;
        this.timeZone = timeZone;
        this.startDate = startDate;
        this.contestants = contestants;
        this.tournamentGamesFormat = tournamentGamesFormat;
        this.tournamentGamesFrequency = tournamentGamesFrequency;
        this.tournamentPrizes = tournamentPrizes;
        this.tournamentFrequency = tournamentFrequency;
        this.tournamentGame = tournamentGame;
        this.tournamentMaxParticipants = tournamentMaxParticipants;
        this.tournamentState = tournamentState;
        this.premium = premium;
        this.tournamentPlatform = tournamentPlatform;
        this.link = link;
    }

    public long getTournamentID() {
        return tournamentID;
    }

    public void setTournamentID(long tournamentID) {
        this.tournamentID = tournamentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getContestants() {
        return contestants;
    }

    public void setContestants(String contestants) {
        this.contestants = contestants;
    }

    public String getTournamentGamesFormat() {
        return tournamentGamesFormat;
    }

    public void setTournamentGamesFormat(String tournamentGamesFormat) {
        this.tournamentGamesFormat = tournamentGamesFormat;
    }

    public String getTournamentGamesFrequency() {
        return tournamentGamesFrequency;
    }

    public void setTournamentGamesFrequency(String tournamentGamesFrequency) {
        this.tournamentGamesFrequency = tournamentGamesFrequency;
    }

    public String getTournamentPrizes() {
        return tournamentPrizes;
    }

    public void setTournamentPrizes(String tournamentPrizes) {
        this.tournamentPrizes = tournamentPrizes;
    }

    public String getTournamentFrequency() {
        return tournamentFrequency;
    }

    public void setTournamentFrequency(String tournamentFrequency) {
        this.tournamentFrequency = tournamentFrequency;
    }

    public String getTournamentGame() {
        return tournamentGame;
    }

    public void setTournamentGame(String tournamentGame) {
        this.tournamentGame = tournamentGame;
    }

    public String getTournamentMaxParticipants() {
        return tournamentMaxParticipants;
    }

    public void setTournamentMaxParticipants(String tournamentMaxParticipants) {
        this.tournamentMaxParticipants = tournamentMaxParticipants;
    }

    public String getTournamentState() {
        return tournamentState;
    }

    public void setTournamentState(String tournamentState) {
        this.tournamentState = tournamentState;
    }

    public String getPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }

    public String getTournamentPlatform() {
        return tournamentPlatform;
    }

    public void setTournamentPlatform(String tournamentPlatform) {
        this.tournamentPlatform = tournamentPlatform;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
