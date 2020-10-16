package com.android.akef.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.android.akef.Tables.Tournament;

import java.util.List;

@Dao
public interface TournamentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Tournament> tournamentList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Tournament tournament);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Tournament> tournamentList);

    @Query("DELETE FROM Tournament")
    void deleteAll();

    @Query("SELECT * from Tournament")
    List<Tournament> getAllTournaments();

}
