
package com.android.akef.RetrofitModels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TournamentResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("date_gmt")
    @Expose
    private String dateGmt;
    @SerializedName("guid")
    @Expose
    private Guid guid;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("modified_gmt")
    @Expose
    private String modifiedGmt;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("title")
    @Expose
    private Title title;
    @SerializedName("content")
    @Expose
    private Content content;
    @SerializedName("author")
    @Expose
    private Integer author;
    @SerializedName("featured_media")
    @Expose
    private Integer featuredMedia;
    @SerializedName("template")
    @Expose
    private String template;
    @SerializedName("format")
    @Expose
    private String format;
/*    @SerializedName("tournament_maps")
    @Expose
    private TournamentMaps tournamentMaps;*/
/*    @SerializedName("participant_cache")
    @Expose
    private String participantCache;*/
   /* @SerializedName("game_cache")
    @Expose
    private String gameCache;*/
   /* @SerializedName("tournament_competitors")
    @Expose
    private TournamentCompetitors tournamentCompetitors;*/
    @SerializedName("tournament_timezone")
    @Expose
    private String tournamentTimezone;
    @SerializedName("tournament_starts")
    @Expose
    private String tournamentStarts;
  /*  @SerializedName("games_created")
    @Expose
    private String gamesCreated;*/
   /* @SerializedName("tournament_starts_unix")
    @Expose
    private String tournamentStartsUnix;*/
    @SerializedName("tournament_contestants")
    @Expose
    private String tournamentContestants;
    @SerializedName("tournament_games_format")
    @Expose
    private String tournamentGamesFormat;
    @SerializedName("tournament_game_frequency")
    @Expose
    private String tournamentGameFrequency;
    /*@SerializedName("tournament_prizes")
    @Expose
    private List<String> tournamentPrizes = null;*/
    @SerializedName("tournament_frequency")
    @Expose
    private String tournamentFrequency;
/*    @SerializedName("tournament_regulations")
    @Expose
    private List<TournamentRegulation> tournamentRegulations = null;*/
    @SerializedName("tournament_game")
    @Expose
    private String tournamentGame;
    @SerializedName("tournament_max_participants")
    @Expose
    private String tournamentMaxParticipants;
    @SerializedName("tournament_server")
    @Expose
    private String tournamentServer;
/*    @SerializedName("_vc_post_settings")
    @Expose
    private String vcPostSettings;*/
   /* @SerializedName("tournament_candidate")
    @Expose
    private List<Object> tournamentCandidate = null;*/
    @SerializedName("tournament_state")
    @Expose
    private String tournamentState;
    @SerializedName("premium")
    @Expose
    private String premium;
    @SerializedName("playoffs_started")
    @Expose
    private String playoffsStarted;
/*    @SerializedName("initial_lineup")
    @Expose
    private String initialLineup;*/
/*    @SerializedName("tournament_delete_competitors")
    @Expose
    private String tournamentDeleteCompetitors;*/
    @SerializedName("tournament_platform")
    @Expose
    private String tournamentPlatform;
    @SerializedName("game_modes")
    @Expose
    private String gameModes;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateGmt() {
        return dateGmt;
    }

    public void setDateGmt(String dateGmt) {
        this.dateGmt = dateGmt;
    }

    public Guid getGuid() {
        return guid;
    }

    public void setGuid(Guid guid) {
        this.guid = guid;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getModifiedGmt() {
        return modifiedGmt;
    }

    public void setModifiedGmt(String modifiedGmt) {
        this.modifiedGmt = modifiedGmt;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public Integer getFeaturedMedia() {
        return featuredMedia;
    }

    public void setFeaturedMedia(Integer featuredMedia) {
        this.featuredMedia = featuredMedia;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

/*    public TournamentMaps getTournamentMaps() {
        return tournamentMaps;
    }

    public void setTournamentMaps(TournamentMaps tournamentMaps) {
        this.tournamentMaps = tournamentMaps;
    }*/

  /*  public String getParticipantCache() {
        return participantCache;
    }

    public void setParticipantCache(String participantCache) {
        this.participantCache = participantCache;
    }*/

  /*  public String getGameCache() {
        return gameCache;
    }

    public void setGameCache(String gameCache) {
        this.gameCache = gameCache;
    }*/

  /*  public TournamentCompetitors getTournamentCompetitors() {
        return tournamentCompetitors;
    }

    public void setTournamentCompetitors(TournamentCompetitors tournamentCompetitors) {
        this.tournamentCompetitors = tournamentCompetitors;
    }*/

    public String getTournamentTimezone() {
        return tournamentTimezone;
    }

    public void setTournamentTimezone(String tournamentTimezone) {
        this.tournamentTimezone = tournamentTimezone;
    }

    public String getTournamentStarts() {
        return tournamentStarts;
    }

    public void setTournamentStarts(String tournamentStarts) {
        this.tournamentStarts = tournamentStarts;
    }

   /* public String getGamesCreated() {
        return gamesCreated;
    }

    public void setGamesCreated(String gamesCreated) {
        this.gamesCreated = gamesCreated;
    }

    public String getTournamentStartsUnix() {
        return tournamentStartsUnix;
    }

    public void setTournamentStartsUnix(String tournamentStartsUnix) {
        this.tournamentStartsUnix = tournamentStartsUnix;
    }
*/
    public String getTournamentContestants() {
        return tournamentContestants;
    }

    public void setTournamentContestants(String tournamentContestants) {
        this.tournamentContestants = tournamentContestants;
    }

    public String getTournamentGamesFormat() {
        return tournamentGamesFormat;
    }

    public void setTournamentGamesFormat(String tournamentGamesFormat) {
        this.tournamentGamesFormat = tournamentGamesFormat;
    }

    public String getTournamentGameFrequency() {
        return tournamentGameFrequency;
    }

    public void setTournamentGameFrequency(String tournamentGameFrequency) {
        this.tournamentGameFrequency = tournamentGameFrequency;
    }

  /*  public List<String> getTournamentPrizes() {
        return tournamentPrizes;
    }

    public void setTournamentPrizes(List<String> tournamentPrizes) {
        this.tournamentPrizes = tournamentPrizes;
    }*/

    public String getTournamentFrequency() {
        return tournamentFrequency;
    }

    public void setTournamentFrequency(String tournamentFrequency) {
        this.tournamentFrequency = tournamentFrequency;
    }

/*    public List<TournamentRegulation> getTournamentRegulations() {
        return tournamentRegulations;
    }

    public void setTournamentRegulations(List<TournamentRegulation> tournamentRegulations) {
        this.tournamentRegulations = tournamentRegulations;
    }*/

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

    public String getTournamentServer() {
        return tournamentServer;
    }

    public void setTournamentServer(String tournamentServer) {
        this.tournamentServer = tournamentServer;
    }

/*    public String getVcPostSettings() {
        return vcPostSettings;
    }

    public void setVcPostSettings(String vcPostSettings) {
        this.vcPostSettings = vcPostSettings;
    }*/

  /*  public List<Object> getTournamentCandidate() {
        return tournamentCandidate;
    }

    public void setTournamentCandidate(List<Object> tournamentCandidate) {
        this.tournamentCandidate = tournamentCandidate;
    }*/

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

    public String getPlayoffsStarted() {
        return playoffsStarted;
    }

    public void setPlayoffsStarted(String playoffsStarted) {
        this.playoffsStarted = playoffsStarted;
    }
/*
    public String getInitialLineup() {
        return initialLineup;
    }

    public void setInitialLineup(String initialLineup) {
        this.initialLineup = initialLineup;
    }*/

/*    public String getTournamentDeleteCompetitors() {
        return tournamentDeleteCompetitors;
    }

    public void setTournamentDeleteCompetitors(String tournamentDeleteCompetitors) {
        this.tournamentDeleteCompetitors = tournamentDeleteCompetitors;
    }*/

    public String getTournamentPlatform() {
        return tournamentPlatform;
    }

    public void setTournamentPlatform(String tournamentPlatform) {
        this.tournamentPlatform = tournamentPlatform;
    }

    public String getGameModes() {
        return gameModes;
    }

    public void setGameModes(String gameModes) {
        this.gameModes = gameModes;
    }

/*    public String getYoastHead() {
        return yoastHead;
    }

    public void setYoastHead(String yoastHead) {
        this.yoastHead = yoastHead;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }*/

}
